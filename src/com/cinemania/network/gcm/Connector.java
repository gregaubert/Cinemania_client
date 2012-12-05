package com.cinemania.network.gcm;

import android.content.Context;
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
        
        // Automatically registers application on startup.
        GCMRegistrar.register(mActivity, CommonUtilities.SENDER_ID);
        	
        final String regId=Secure.getString(mActivity.getBaseContext().getContentResolver(), Secure.ANDROID_ID);
        Log.d("DEBUG", "RegID: " + regId);
	}
    
    private static void checkNotNull(Object reference, String name) {
        if (reference == null) {
            throw new NullPointerException(
            		Base.getSharedInstance().getString(R.string.error_config, name));
        }
    }
}
