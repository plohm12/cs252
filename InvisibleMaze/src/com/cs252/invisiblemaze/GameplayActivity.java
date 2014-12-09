package com.cs252.invisiblemaze;

import com.cs252.invisiblemaze.util.SystemUiHider;
import com.shephertz.app42.gaming.multiplayer.client.events.LiveRoomInfoEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.MoveEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.RoomRequestListener;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class GameplayActivity extends Activity implements RoomRequestListener{
	GameboardView gbv;
	int tries;
	/**
	 * Whether or not the system UI should be auto-hidden after
	 * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
	 */
	private static final boolean AUTO_HIDE = false;

	/**
	 * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
	 * user interaction before hiding the system UI.
	 */
	private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

	/**
	 * If set, will toggle the system UI visibility upon interaction. Otherwise,
	 * will show the system UI visibility upon interaction.
	 */
	private static final boolean TOGGLE_ON_CLICK = false;

	/**
	 * The flags to pass to {@link SystemUiHider#getInstance}.
	 */
	private static final int HIDER_FLAGS = 0;

	/**
	 * The instance of the {@link SystemUiHider} for this activity.
	 */
	private SystemUiHider mSystemUiHider;

	private TextView turnText;
	private int GAME_SIZE = 6;
	private Gameboard gameboard;
	private LinearLayout gameboardView;
	private GameMessenger messenger;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_gameplay);
				
		gameboardView = (LinearLayout)findViewById(R.id.gameboardView);		
		gameboard = new Gameboard();
		
		
		setupActionBar();

		final View controlsView = findViewById(R.id.fullscreen_content_controls);
		final View contentView = findViewById(R.id.fullscreen_content);

		// Set up an instance of SystemUiHider to control the system UI for
		// this activity.

		mSystemUiHider = SystemUiHider.getInstance(this, contentView,
				HIDER_FLAGS);
		mSystemUiHider.setup();
		mSystemUiHider
				.setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
					// Cached values.
					int mControlsHeight;
					int mShortAnimTime;

					@Override
					@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
					public void onVisibilityChange(boolean visible) {
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
							// If the ViewPropertyAnimator API is available
							// (Honeycomb MR2 and later), use it to animate the
							// in-layout UI controls at the bottom of the
							// screen.
							if (mControlsHeight == 0) {
								mControlsHeight = controlsView.getHeight();
							}
							if (mShortAnimTime == 0) {
								mShortAnimTime = getResources().getInteger(
										android.R.integer.config_shortAnimTime);
							}
							controlsView
									.animate()
									.translationY(visible ? 0 : mControlsHeight)
									.setDuration(mShortAnimTime);
						} else {
							// If the ViewPropertyAnimator APIs aren't
							// available, simply show or hide the in-layout UI
							// controls.
							controlsView.setVisibility(visible ? View.VISIBLE
									: View.GONE);
						}
					}
				});

		// Set up the user interaction to manually show or hide the system UI.
		contentView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (TOGGLE_ON_CLICK) {
					mSystemUiHider.toggle();
				} else {
					mSystemUiHider.show();
				}
			}
		});
		messenger = new GameMessenger(this);
		turnText = (TextView)findViewById(R.id.turn_player_name);
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		// Trigger the initial hide() shortly after the activity has been
		// created, to briefly hint to the user that UI controls
		// are available.
		delayedHide(100);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		tries = 0;
		gbv = new GameboardView(this, GAME_SIZE, gameboard);
		gameboardView.addView(gbv);
		messenger.start();
		FullscreenActivity.theClient.addRoomRequestListener(this);
		FullscreenActivity.theClient.getLiveRoomInfo(Constants.room_id);

		if (Constants.isLocalPlayer) {
			isLocalTurn = true;
			turnText.setText("Next Turn "+Constants.localUsername);
		}
	}
	
	@Override
	public void onStop() {
		super.onStop();
		this.finish();
		messenger.stop();
		FullscreenActivity.theClient.removeRoomRequestListener(this);
	}
	
	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			// Show the Up button in the action bar.
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == android.R.id.home) {
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			// TODO: If Settings has multiple levels, Up should navigate up
			// that hierarchy.
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Touch listener to use for in-layout UI controls to delay hiding the
	 * system UI. This is to prevent the jarring behavior of controls going away
	 * while interacting with activity UI.
	 */
	View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View view, MotionEvent motionEvent) {
			if (AUTO_HIDE) {
				delayedHide(AUTO_HIDE_DELAY_MILLIS);
			}
			return false;
		}
	};

	Handler mHideHandler = new Handler();
	Runnable mHideRunnable = new Runnable() {
		@Override
		public void run() {
			mSystemUiHider.hide();
		}
	};

	private boolean isLocalTurn = false;
	
	private Handler UIThreadHandler = new Handler();

	/**
	 * Schedules a call to hide() in [delay] milliseconds, canceling any
	 * previously scheduled calls.
	 */
	private void delayedHide(int delayMillis) {
		mHideHandler.removeCallbacks(mHideRunnable);
		mHideHandler.postDelayed(mHideRunnable, delayMillis);
	}
	
	public void onGameStarted(final String nt) {
		if (nt.equals(Constants.localUsername)) {
			isLocalTurn  = true;
		} else {
			isLocalTurn = false;
		}
		
		UIThreadHandler.post(new Runnable() {
			@Override
			public void run() {
				turnText.setText("Next Turn "+nt);
			}
		});
	}
	
	void handleRemoteLeft() {
		FullscreenActivity.theClient.deleteRoom(Constants.room_id);
		UIThreadHandler.post(new Runnable() {
			@Override
			public void run() {
				GameplayActivity.this.finish();
			}
		});
	}

	public void onMoveCompleted(final MoveEvent evt) {
		if (evt.getNextTurn().equals(Constants.localUsername)) {
			isLocalTurn = true;	
		} else {
			isLocalTurn = false;	
		}
		
		UIThreadHandler.post(new Runnable() {
			@Override
			public void run() {
				if (evt.getMoveData().length() > 0) {
					System.out.println("moved completed");
					turnText.setText("Next Turn " + evt.getNextTurn());
					if(evt.getMoveData().equals("1")){
						System.out.println("You lost!");
						Constants.isLocalPlayer = isLocalTurn;
						//GameplayActivity.this.finish();
						Intent myIntent = new Intent(GameplayActivity.this, LoseActivity.class);
						startActivity(myIntent);
					}
				} else {
					turnText.setText("Next Turn " + evt.getNextTurn());
				}
			}
		});
	}
	
	@Override
	public void onGetLiveRoomInfoDone(LiveRoomInfoEvent arg0) {
		int users = arg0.getJoinedUsers().length;
		if (users > 1) {
			FullscreenActivity.theClient.startGame();
		}
	}

	@Override
	public void onJoinRoomDone(RoomEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLeaveRoomDone(RoomEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLockPropertiesDone(byte arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSetCustomRoomDataDone(LiveRoomInfoEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSubscribeRoomDone(RoomEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUnSubscribeRoomDone(RoomEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUnlockPropertiesDone(byte arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpdatePropertyDone(LiveRoomInfoEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void move(View view) {
		if (!isLocalTurn) {
			Toast.makeText(this,"Its not your turn!",Toast.LENGTH_SHORT).show();
			return;
		}
		
		boolean winner = gameboard.move(view);
		gbv.postInvalidate();
		
		if (winner) {
			//do winner stuff
			System.out.println("YOU HAVE WON!!!");
			messenger.sendMove("1");
		//	Constants.isLocalPlayer = isLocalTurn;
			Intent myIntent = new Intent(GameplayActivity.this, WinActivity.class);
			startActivity(myIntent);
		}
		
		tries++;
		if (tries >= 3) {
			//switch player turns
			tries = 0;
			messenger.sendMove("moved completed");
		}
	}
}
