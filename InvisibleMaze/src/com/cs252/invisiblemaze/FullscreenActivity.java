package com.cs252.invisiblemaze;

import java.util.Random;

import com.cs252.invisiblemaze.util.SystemUiHider;
import com.shephertz.app42.gaming.multiplayer.client.WarpClient;
import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.events.AllRoomsEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.AllUsersEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.ConnectEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.LiveRoomInfoEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.LiveUserInfoEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.MatchedRoomsEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.ConnectionRequestListener;
import com.shephertz.app42.gaming.multiplayer.client.listener.RoomRequestListener;
import com.shephertz.app42.gaming.multiplayer.client.listener.ZoneRequestListener;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
@SuppressLint("NewApi") public class FullscreenActivity extends Activity implements
		ConnectionRequestListener {
	/**
	 * Whether or not the system UI should be auto-hidden after
	 * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
	 */
	public final static String EXTRA = "com.cs252.invisiblemaze.MESSAGE";
	private static final boolean AUTO_HIDE = false;
	public static String key = "caa5645bb9166b904e763ce781eeec1f80fb163ea02b504af45d3469d0550acf";
	public static String secertKey = "efd8256cda6edc66a30c34e5e740f61e3572c9d71ab1cb9d5cc206c84f47fba0";
	private FindRoom rf = new FindRoom();
    private ProgressDialog progressDialog;
    public int numPlayers = 0;

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

	public static WarpClient theClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_fullscreen);

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

							if (mShortAnimTime == 0) {
								mShortAnimTime = getResources().getInteger(
										android.R.integer.config_shortAnimTime);
							}

						}

						if (visible && AUTO_HIDE) {
							// Schedule a hide().
							delayedHide(AUTO_HIDE_DELAY_MILLIS);
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
		
		init();
		
		System.out.println("IN ONCREATE");
	}

	private void init() {
		// TODO Auto-generated method stub
		WarpClient.initialize(key, secertKey);
		WarpClient.enableTrace(true);
		try {
			theClient = WarpClient.getInstance();

		} catch (Exception e) {
			System.out.println("error key\n");
		}
		theClient.addConnectionRequestListener(this);
		
		System.out.println("IN INIT");
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		// Trigger the initial hide() shortly after the activity has been
		// created, to briefly hint to the user that UI controls
		// are available.
		delayedHide(100);
		
		System.out.println("IN ONPOSTCREATE");
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
			
			System.out.println("IN ONTOUCH");
			
			return false;
		}
	};

	Handler mHideHandler = new Handler();
	Runnable mHideRunnable = new Runnable() {
		@Override
		public void run() {
			mSystemUiHider.hide();
			
			System.out.println("IN RUN");
		}
	};
	
	private String userName;
	private Handler UIThreadHandler = new Handler();

	/**
	 * Schedules a call to hide() in [delay] milliseconds, canceling any
	 * previously scheduled calls.
	 */
	private void delayedHide(int delayMillis) {
		mHideHandler.removeCallbacks(mHideRunnable);
		mHideHandler.postDelayed(mHideRunnable, delayMillis);
		
		System.out.println("IN DELAYEDHIDE");
	}

	public void startMaze(View view) {
		EditText editText = (EditText) findViewById(R.id.user_name);
		userName = editText.getText().toString();
		if (userName.isEmpty()) {
			Random rd = new Random();
			int x = rd.nextInt(1000);
			
			StringBuilder sb = new StringBuilder();
			sb.append("user");
			sb.append(x);
			userName = sb.toString();
		}
		theClient.connectWithUserName(userName);
		Constants.localUsername = userName;
		
		System.out.println("IN STARTMAZE");
	}

	private void onRoomFound(final boolean success) {
    	UIThreadHandler.post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				//	progressDialog.dismiss();
				if (success && numPlayers >= 1) {
					Intent myIntent = new Intent(FullscreenActivity.this, SearchActivity.class);
					startActivity(myIntent);
				} else {
					Intent myIntent = new Intent(FullscreenActivity.this, SearchActivity.class);
					startActivity(myIntent);
				}
				
				System.out.println("IN SECOND RUN");
			}
    		
    	});
    	
    	System.out.println("IN ONROOMFOUND");
    }

	@Override
	public void onConnectDone(ConnectEvent arg0) {
		// TODO Auto-generated method stub
		Log.d("OnConnectDone", arg0.getResult() + "");

		if (arg0.getResult() == WarpResponseResultCode.SUCCESS) {
			// Intent intent = new Intent(this, GameplayActivity.class);
			// intent.putExtra(EXTRA, userName);
			// startActivity(intent);
			System.out.println("sucess\n");
			rf.findR();
		} else {
			onRoomFound(false);
			System.out.println("error creating\n");
		}
		
		System.out.println("IN ONCONNECTDONE");
	}

	@Override
	public void onDisconnectDone(ConnectEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onInitUDPDone(byte arg0) {
		// TODO Auto-generated method stub

	}

	private class FindRoom implements RoomRequestListener, ZoneRequestListener {


		public void findR() {
			theClient.addRoomRequestListener(this);
			theClient.addZoneRequestListener(this);
			theClient.joinRoomInRange(1, 1, true);
			Constants.isLocalPlayer = true;
			
			System.out.println("IN FINDR");
		}

		@Override
		public void onCreateRoomDone(RoomEvent arg0) {
			// TODO Auto-generated method stub
			if (arg0.getResult() == WarpResponseResultCode.SUCCESS) {
				theClient.joinRoom(arg0.getData().getId());
				Constants.isLocalPlayer = false;
			}
			
			System.out.println("IN ONCREATEROOMDONE");
		}

		@Override
		public void onDeleteRoomDone(RoomEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onGetAllRoomsDone(AllRoomsEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onGetLiveUserInfoDone(LiveUserInfoEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onGetMatchedRoomsDone(MatchedRoomsEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onGetOnlineUsersDone(AllUsersEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSetCustomUserDataDone(LiveUserInfoEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onGetLiveRoomInfoDone(LiveRoomInfoEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onJoinRoomDone(RoomEvent arg0) {
			// TODO Auto-generated method stub
			if (arg0.getResult() == WarpResponseResultCode.SUCCESS) {
				numPlayers++;
				theClient.subscribeRoom(arg0.getData().getId());
			} else {
				theClient.createTurnRoom("dynamic", "dev", 2, null, 30);
			}
			
			System.out.println("IN ONJOINROOMDONE");
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
			if (arg0.getResult() == WarpResponseResultCode.SUCCESS) {
				Constants.room_id = arg0.getData().getId();
				onRoomFound(true);
			}
			
			System.out.println("IN ONSUBSCRIBEROOMDONE");
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
	}
}
