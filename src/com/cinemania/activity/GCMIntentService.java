/*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cinemania.activity;
 
import static com.cinemania.network.gcm.CommonUtilities.SENDER_ID;
 
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gcm.GCMRegistrar;
import com.cinemania.network.gcm.CommonUtilities;
import com.cinemania.network.gcm.ServerUtilities;
 
/**
 * IntentService responsible for handling GCM messages.
 */
public class GCMIntentService extends GCMBaseIntentService {
 
    @SuppressWarnings("hiding")
    private static final String TAG = "GCMIntentService";
 
    public GCMIntentService() {
        super(SENDER_ID);
    }
 
    @Override
    protected void onRegistered(Context context, String registrationId) {
        Log.d(TAG, "Device registered: regId = " + registrationId);
        ServerUtilities.register(context, registrationId);
    }
 
    @Override
    protected void onUnregistered(Context context, String registrationId) {
        Log.d(TAG, "Device unregistered");
     
        if (GCMRegistrar.isRegisteredOnServer(context)) {
            ServerUtilities.unregister(context, registrationId);
        } else {
            // This callback results from the call to unregister made on
            // ServerUtilities when the registration to the server failed.
            Log.d(TAG, "Ignoring unregister callback");
        }
    }
 
    @Override
    protected void onMessage(Context context, Intent intent) {
        Log.d(TAG, "Received message:" + getDataFromInputIntent(intent));
        passIntent(context,intent);
    }
 
    @Override
    protected void onDeletedMessages(Context context, int total) {
        Log.d(TAG, "Received deleted messages notification");
    }
 
    @Override
    public void onError(Context context, String errorId) {
        Log.d(TAG, "Received error: " + errorId);
    }
 
    @Override
    protected boolean onRecoverableError(Context context, String errorId) {
        // log message
        Log.d(TAG, "Received recoverable error: " + errorId);
        return super.onRecoverableError(context, errorId);
    }
     
    private String getDataFromInputIntent(Intent intent){
        return intent.getExtras().getString(CommonUtilities.MESSAGE);
    }
     
    private void passIntent(Context context, Intent intent) {
        Intent passIntent = new Intent(CommonUtilities.DISPLAY_MESSAGE_ACTION);
        passIntent.putExtra(CommonUtilities.MESSAGE, getDataFromInputIntent(intent));
        context.sendBroadcast(passIntent);
    }
}