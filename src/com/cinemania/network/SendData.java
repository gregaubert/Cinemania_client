package com.cinemania.network;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;
import android.util.Log;

import com.cinemania.network.Utilities.Response;

public class SendData extends AsyncTask<URL, Void, Response>{
	
	public final int NUMBER_OF_TRY = 5;
	
	private String param;
	
	public SendData(String aParam)
	{
		this.param = aParam;
	}
	
	@Override
	protected Response doInBackground(URL... params) {
		
		Log.i("GAME", "doInBackground");
		
		Response response = new Response();
		response.setSuccessful(true);
		
		byte[] bytes = param.getBytes();
		
		URL url = params[0];
		
		// Post response
		HttpURLConnection conn = null;
		for (int i = 0; i < NUMBER_OF_TRY; i++) {
			try {
				conn = (HttpURLConnection)url.openConnection();
				conn.setDoOutput(true);
				conn.setUseCaches(false);
				conn.setFixedLengthStreamingMode(bytes.length);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
				// Post the request
				OutputStream out = conn.getOutputStream();
				out.write(bytes);
				out.close();
				// Handle the response
				response.setCode(conn.getResponseCode());
				if (response.getCode() == 200) {
					response.setJSON(Utilities.buildPostResponse(conn.getInputStream()));
					// TODO: All responses from server have this field
					//response.mSuccessful &= response.mJson.getInt("success") == 1;
					return response;
				} else {
					// When an error occurs, we simply retry
					Log.e("GAME", "Wrong HTTP code response retry" + response.getCode());
				}
			} catch (Exception e) {
				// When an error occurs, we simply retry
				Log.e("GAME", "HTTP exception on POST", e);
			} finally {
				if (conn != null) {
					conn.disconnect();
				}
			}
		}
		response.setSuccessful(false);
		Log.e("GAME", "Max try");
		return response;
	}

	
}
