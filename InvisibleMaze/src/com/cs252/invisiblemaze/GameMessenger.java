package com.cs252.invisiblemaze;

import java.util.HashMap;

import android.util.Log;

import com.shephertz.app42.gaming.multiplayer.client.events.ChatEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.ConnectEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.LobbyData;
import com.shephertz.app42.gaming.multiplayer.client.events.MoveEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomData;
import com.shephertz.app42.gaming.multiplayer.client.events.UpdateEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.ConnectionRequestListener;
import com.shephertz.app42.gaming.multiplayer.client.listener.NotifyListener;
import com.shephertz.app42.gaming.multiplayer.client.listener.TurnBasedRoomListener;

public class GameMessenger implements NotifyListener, TurnBasedRoomListener, ConnectionRequestListener {
	
	private GameplayActivity game; 
	
	public GameMessenger(GameplayActivity game) {
		this.game = game;
	}
	
	@Override
	public void onConnectDone(ConnectEvent arg0) {
        Log.d("AppWarpTrace", "onConnectDone "+arg0.getResult());        
	}

	@Override
	public void onDisconnectDone(ConnectEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onInitUDPDone(byte arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGetMoveHistoryDone(byte arg0, MoveEvent[] arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSendMoveDone(byte arg0) {
        Log.d("AppWarpTrace", "onSendMoveDone "+arg0);        
	}

	@Override
	public void onStartGameDone(byte arg0) {
        Log.d("AppWarpTrace", "onStartGameDone "+arg0);        
	}

	@Override
	public void onStopGameDone(byte arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onChatReceived(ChatEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGameStarted(String arg0, String arg1, String arg2) {
        Log.d("AppWarpTrace", "onGameStarted nextTurn "+arg2); 
        game.onGameStarted(arg2);
	}

	@Override
	public void onGameStopped(String arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMoveCompleted(MoveEvent arg0) {
		Log.d("AppWarpTrace", "onMoveCompleted turn is "+arg0.getNextTurn());   
        game.onMoveCompleted(arg0);      
	}

	@Override
	public void onPrivateChatReceived(String arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRoomCreated(RoomData arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRoomDestroyed(RoomData arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpdatePeersReceived(UpdateEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUserChangeRoomProperty(RoomData arg0, String arg1,
			HashMap<String, Object> arg2, HashMap<String, String> arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUserJoinedLobby(LobbyData arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUserJoinedRoom(RoomData arg0, String arg1) {
        Log.d("AppWarpTrace", "onUserJoinedRoom "+arg1);
	}

	@Override
	public void onUserLeftLobby(LobbyData arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUserLeftRoom(RoomData arg0, String arg1) {
		Log.d("AppWarpTrace", "onUserLeftRoom "+arg1);
	}

	@Override
	public void onUserPaused(String arg0, boolean arg1, String arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUserResumed(String arg0, boolean arg1, String arg2) {
		// TODO Auto-generated method stub
		
	}
	
	public void start() {
		FullscreenActivity.theClient.addTurnBasedRoomListener(this);
		FullscreenActivity.theClient.addNotificationListener(this);
		FullscreenActivity.theClient.addConnectionRequestListener(this);
	}
	
	public void stop() {
		FullscreenActivity.theClient.disconnect();
		FullscreenActivity.theClient.removeConnectionRequestListener(this);
		FullscreenActivity.theClient.removeNotificationListener(this);
		FullscreenActivity.theClient.removeTurnBasedRoomListener(this);
	}
	
	public void sendMove() {
		FullscreenActivity.theClient.sendMove("test");
	}
}
