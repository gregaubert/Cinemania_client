package com.cinemania.network.gcm;
 
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.util.Log;
 
import com.cinemania.activity.Base;
import com.google.android.gcm.GCMRegistrar;
import com.cinemania.network.api.API;
import com.cinemania.network.api.API.GameDataResult;
import com.cinemania.network.api.API.GameIdentifierResult;
import com.cinemania.network.gcm.CommonUtilities;
 
public class GCMConnector {
         
    private static AsyncTask mRegisterTask = null;
     
    private static Base mActivity = Base.getSharedInstance();
     
     
    public static void connect(){
         
        // Make sure the device has the proper dependencies.
        GCMRegistrar.checkDevice(mActivity);
         
        // Make sure the manifest was properly set - comment out this line
        // while developing the app, then uncomment it when it's ready.
        GCMRegistrar.checkManifest(mActivity);
         
        mActivity.registerReceiver(mHandleMessageReceiver, new IntentFilter(CommonUtilities.DISPLAY_MESSAGE_ACTION));
         
         
        String regId = GCMRegistrar.getRegistrationId(mActivity);
           
        if (regId.equals("")) {
            Log.v(CommonUtilities.TAG, "Registering for a new ID");
             
            GCMRegistrar.register(mActivity, CommonUtilities.SENDER_ID);
            regId = GCMRegistrar.getRegistrationId(mActivity);
        } 
         
        Log.d("DEBUG", "RegID: " + regId);
        
        API.registerDevice(regId);
         
        // needid if you want messages to be delivered to this app!
        GCMRegistrar.setRegisteredOnServer(mActivity, true);
        
        /*boolean x = API.registerDevice(regId);
        Log.d("API", Boolean.toString(x));
        GameIdentifierResult r1 = API.newGame();
        GameDataResult r2 = API.gameData(r1.getGameIdentifier());
        API.gamePassTurn(r1.getGameIdentifier(), r2.getGameData());
        */
                
        // Try to register again, but not in the UI thread.
        // It's also necessary to cancel the thread onDestroy(),
        // hence the use of AsyncTask instead of a raw thread.
        /*final Context context = mActivity;
        mRegisterTask = new AsyncTask<Void, Void, Void>() {
 
            @Override
            protected Void doInBackground(Void... params) {
                ServerUtilities.register(context, regId);
                return null;
            }
 
            @Override
            protected void onPostExecute(Void result) {
                mRegisterTask = null;
            }
 
        };
        mRegisterTask.execute(null, null, null);*/   
 
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
            String newMessage = intent.getExtras().getString(CommonUtilities.MESSAGE);
            Log.d("DEBUG", "Message received! " + newMessage);
        }
    };
     
}