package com.cinemania.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import android.provider.Settings.Secure;
import android.util.Log;

import com.cinemania.activity.Base;

public final class Utilities {

	/**
	 * Base URL of the server
	 */
	public static final String SERVER_URL = "http://jorgealbaladejo.com/cinemania/app/index.php";

	/**
	 * Google API project id registered to use GCM.
	 */
	public static final String SENDER_ID = "253409584595";

	public static final int NUMBER_OF_TRY = 5;

	/**
	 * The device's hard ID
	 */
	public static final String DEVICE_ID = Secure.getString(Base
			.getSharedInstance().getBaseContext().getContentResolver(),
			Secure.ANDROID_ID);

	public static Response post(URL url, Map<String, String> params) {

		Response response = new Response();
		response.mSuccessful = true;

		// Constructs request's body by using given parameters
		String body = buildPostBody(params);

		// Post builded request to the specified server's URL
		Log.d("GAME", "Post(" + url + "): " + body);

		SendData task = new SendData(body);
		task.execute(url);
		try {
			return task.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.mSuccessful = false;
		return response;
		/*
		 * byte[] bytes = body.getBytes(); // Post response HttpURLConnection
		 * conn = null; for (int i = 0; i < NUMBER_OF_TRY; i++) { try { conn =
		 * (HttpURLConnection)url.openConnection(); conn.setDoOutput(true);
		 * conn.setUseCaches(false);
		 * conn.setFixedLengthStreamingMode(bytes.length);
		 * conn.setRequestMethod("POST");
		 * conn.setRequestProperty("Content-Type",
		 * "application/x-www-form-urlencoded;charset=UTF-8"); // Post the
		 * request OutputStream out = conn.getOutputStream(); out.write(bytes);
		 * out.close(); // Handle the response response.mCode =
		 * conn.getResponseCode(); if (response.mCode == 200) { response.mJson =
		 * buildPostResponse(conn.getInputStream()); // TODO: All responses from
		 * server have this field //response.mSuccessful &=
		 * response.mJson.getInt("success") == 1; return response; } else { //
		 * When an error occurs, we simply retry Log.e("GAME",
		 * "Wrong HTTP code response retry" + response.mCode); } } catch
		 * (Exception e) { // When an error occurs, we simply retry
		 * Log.e("GAME", "HTTP exception on POST", e); } finally { if (conn !=
		 * null) { conn.disconnect(); } } } response.mSuccessful = false;
		 * Log.e("GAME", "Max try"); return response;
		 */
	}

	private static String buildPostBody(Map<String, String> params) {
		// When there is no parameters, we doesn't need to build anything
		if (params == null || params.size() == 0) {
			return "";
		}
		StringBuilder bodyBuilder = new StringBuilder();
		Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
		// Iterate on each parameter and add it into the body
		while (iterator.hasNext()) {
			Entry<String, String> param = iterator.next();
			bodyBuilder.append(param.getKey()).append('=')
					.append(param.getValue());
			if (iterator.hasNext()) {
				bodyBuilder.append('&');
			}
		}
		return bodyBuilder.toString();
	}

	public static JSONObject buildPostResponse(InputStream in)
			throws IOException, JSONException {
		InputStreamReader reader = new InputStreamReader(in);
		StringBuilder builder = new StringBuilder();
		char[] buffer = new char[1024];
		int n = 0;
		// Read response contained into POST response
		while (-1 != (n = reader.read(buffer))) {
			builder.append(buffer, 0, n);
		}
		Log.d("GAME", "Result: " + builder.toString());
		return new JSONObject(builder.toString());
	}

	public static class Response {

		private JSONObject mJson;
		private int mCode;
		private boolean mSuccessful;

		Response() {
		}

		public JSONObject getJson() {
			return mJson;
		}

		public void setJSON(JSONObject aJson) {
			this.mJson = aJson;
		}

		public int getCode() {
			return mCode;
		}

		public void setCode(int aCode) {
			this.mCode = aCode;
		}

		public boolean successful() {
			return mSuccessful;
		}

		public void setSuccessful(boolean aSucces) {
			this.mSuccessful = aSucces;
		}
	}
}