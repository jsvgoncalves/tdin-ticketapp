package org.up.fe.tdin.tdintroubletickets.helper;


import java.lang.reflect.Method;

import org.apache.http.HttpResponse;
import org.up.fe.tdin.tdintroubletickets.R;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class ComService extends AsyncTask<String, String, String> {
	
	public static String serverURL = "http://192.168.102.240/tdin-webservice";
	ProgressDialog dialog;
	String methodName;
	Object object;
	boolean showProgress;
	
	@Override
	protected void onPreExecute(){}
	
	public ComService(String url, Object object, String methodName, boolean showProgress){
		this(url, object, methodName, showProgress, ((Context) object).getString(R.string.fetching_data));
	}
	
	/**
	 * Constructor with dialog message.
	 * @param url The url to access
	 * @param object The activity to callback
	 * @param methodName The callback method
	 * @param showProgress The boolean to indicate if a progress message should be displayed
	 * @param dialogMessage The dialog message string
	 */
	public ComService(String url, Object object, String methodName, boolean showProgress, String dialogMessage) {
		String full_url = serverURL + url;
		this.methodName = methodName;
		this.object = object;
		this.execute(full_url);
		this.showProgress = showProgress;

		//set message of the dialog
		if (showProgress) {
			dialog = new ProgressDialog((Context) object);
	        dialog.setMessage(dialogMessage);
	        dialog.setCancelable(false);
	        dialog.show();
		}
        super.onPreExecute();
	}

	@Override
	protected String doInBackground(String... params) {
	    // return ComHelper.httpGet(params);
	    return ComHelper.getHTTP(params);
	}
	
	@Override
	protected void onPostExecute (String response){
//		System.out.println(result);
//		Log.e("mylog", "result " + result);
//		JSONObject json = JSONHelper.string2JSON(result);
//		String status = JSONHelper.getValue(json, "status");

		String result = "----";
		Log.v("onPostExecute()", response);
		if (showProgress) {
			dialog.dismiss();
		}
		try {
			Method method = object.getClass().getMethod(methodName, String.class);
			method.invoke(object, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}