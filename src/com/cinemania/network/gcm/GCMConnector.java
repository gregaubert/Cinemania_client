package com.cinemania.network.gcm;
 
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.util.Log;
 
import com.cinemania.activity.Base;
import com.cinemania.camera.BoardHUD;
import com.google.android.gcm.GCMRegistrar;
import com.cinemania.network.Utilities;
import com.cinemania.network.api.API;
import com.cinemania.network.api.API.GameDataResult;
import com.cinemania.network.api.API.GameIdentifierResult;
import com.cinemania.network.gcm.GCMUtilities;
 
public class GCMConnector {
         
    private static AsyncTask mRegisterTask = null;
     
    private static Base mActivity = Base.getSharedInstance();
     
    private static String regId;
     
    public static void connect(){
         
        // Make sure the device has the proper dependencies.
        GCMRegistrar.checkDevice(mActivity);
         
        // Make sure the manifest was properly set - comment out this line
        // while developing the app, then uncomment it when it's ready.
        GCMRegistrar.checkManifest(mActivity);
         
        mActivity.registerReceiver(mHandleMessageReceiver, new IntentFilter(GCMUtilities.DISPLAY_MESSAGE_ACTION));
         
         
        regId = GCMRegistrar.getRegistrationId(mActivity);
           
        if (regId.equals("")) {
            Log.v(GCMUtilities.TAG, "Registering for a new ID");
             
            GCMRegistrar.register(mActivity, Utilities.SENDER_ID);
            regId = GCMRegistrar.getRegistrationId(mActivity);
        } 
         
        Log.d("DEBUG", "RegID: " + regId);
        
        API.registerDevice(regId);
         
        // needid if you want messages to be delivered to this app!
        GCMRegistrar.setRegisteredOnServer(mActivity, true);
 
    }
     
    // TODO: This one is never referenced
    public void destroy() {
        if (mRegisterTask != null) {
            mRegisterTask.cancel(true);
        }
         
        try{
            mActivity.unregisterReceiver(mHandleMessageReceiver);
        }
        catch (Exception e){};      
         
        GCMRegistrar.setRegisteredOnServer(mActivity, false);
         
        GCMRegistrar.onDestroy(mActivity);
    }
     
    private static BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getExtras().getString(GCMUtilities.MESSAGE);
            
	            
            if (action.equals("PASS_TURN") || action.equals("PLAYER_JOINED")){
            	Base.getSharedInstance().runOnUpdateThread(new Runnable(){
            		
					@Override
					public void run() {
						Base.getSharedInstance().getGame().getDataFromServer();
						Base.getSharedInstance().getGame().regenerateGameElements();
		            	((BoardHUD)Base.getSharedInstance().getCamera().getHUD()).update();
					}
	            	
	            });
	            
            }
            
        }
    };
     
}
