package com.cinemania.network.gcm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.provider.Settings.Secure;
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
    	
        // Make sure the device has the proper dependencies.
        GCMRegistrar.checkDevice(mActivity);
        
        // Make sure the manifest was properly set - comment out this line
        // while developing the app, then uncomment it when it's ready.
        GCMRegistrar.checkManifest(mActivity);
        
        BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver(){
            @Override
            public void onReceive(Context context, Intent intent) {
                String newMessage = intent.getExtras().getString(CommonUtilities.MESSAGE);
                Log.d("DEBUG", "Message received! " + newMessage);
            }
        };
        
        mActivity.registerReceiver(mHandleMessageReceiver,
                new IntentFilter(CommonUtilities.DISPLAY_MESSAGE_ACTION));
        
        
        final String regId = GCMRegistrar.getRegistrationId(mActivity);
        
        Log.d("DEBUG", "RegID: " + regId);
        
        if (regId.equals("")) {
        	GCMRegistrar.register(mActivity, CommonUtilities.SENDER_ID);
        	
        	Log.d("DEBUG", "RegID: " + GCMRegistrar.getRegistrationId(mActivity));
        } else {
          Log.v(CommonUtilities.TAG, "Already registered: " + regId);
        }
        
        // Automatically registers application on startup.
        GCMRegistrar.setRegisteredOnServer(mActivity, true);
	}
    
}
