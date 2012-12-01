package com.cinemania.network.gcm;

import android.content.Context;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.util.Log;

import com.cinemania.activity.Base;
import com.cinemania.client.R;
import com.google.android.gcm.GCMBroadcastReceiver;
import com.google.android.gcm.GCMRegistrar;
import com.cinemania.network.gcm.CommonUtilities;
import com.cinemania.network.gcm.ServerUtilities;

public class Connector {
	    
    private static AsyncTask mRegisterTask = null;
    
    private static Base mActivity = Base.getSharedInstance();
	
	public static void registerGCMReceiver(){
    	
    	checkNotNull(CommonUtilities.SERVER_URL, "SERVER_URL");
        checkNotNull(CommonUtilities.SENDER_ID, "SENDER_ID");
    	
        // Make sure the device has the proper dependencies.
        GCMRegistrar.checkDevice(mActivity);
        
        // Make sure the manifest was properly set - comment out this line
        // while developing the app, then uncomment it when it's ready.
        GCMRegistrar.checkManifest(mActivity);
        
        GCMBroadcastReceiver mHandleMessageReceiver = new GCMBroadcastReceiver();
        
        mActivity.registerReceiver(mHandleMessageReceiver,
                new IntentFilter(com.cinemania.network.gcm.CommonUtilities.DISPLAY_MESSAGE_ACTION));
        final String regId = GCMRegistrar.getRegistrationId(mActivity);
        
        if (regId.equals("")) {
            // Automatically registers application on startup.
            GCMRegistrar.register(mActivity, CommonUtilities.SENDER_ID);
            
            Log.d("DEBUG", "RegID: " + GCMRegistrar.getRegistrationId(mActivity));
            
        } else {
            // Device is already registered on GCM, check server.
            if (GCMRegistrar.isRegisteredOnServer(mActivity)) {
                // Skips registration.
                Log.d("DEBUG",mActivity.getString(R.string.already_registered));
            } else {
                // Try to register again, but not in the UI thread.
                // It's also necessary to cancel the thread onDestroy(),
                // hence the use of AsyncTask instead of a raw thread.
                final Context context = mActivity;
                mRegisterTask = new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {
                        boolean registered =
                                ServerUtilities.register(context, regId);
                        // At this point all attempts to register with the app
                        // server failed, so we need to unregister the device
                        // from GCM - the app will try to register again when
                        // it is restarted. Note that GCM will send an
                        // unregistered callback upon completion, but
                        // GCMIntentService.onUnregistered() will ignore it.
                        if (!registered) {
                            GCMRegistrar.unregister(context);
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        mRegisterTask = null;
                    }

                };
                mRegisterTask.execute(null, null, null);
            }
        }
    }
    
    private static void checkNotNull(Object reference, String name) {
        if (reference == null) {
            throw new NullPointerException(
            		Base.getSharedInstance().getString(R.string.error_config, name));
        }
    }
}
