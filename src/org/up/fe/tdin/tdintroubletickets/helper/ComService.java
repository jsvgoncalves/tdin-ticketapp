package org.up.fe.tdin.tdintroubletickets.helper;


import java.lang.reflect.Method;

import org.apache.http.HttpResponse;
import org.up.fe.tdin.tdintroubletickets.R;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.app.Activity;
import android.app.Application;
import android.util.Log;

import org.json.JSONObject;

import org.up.fe.tdin.tdintroubletickets.model.TDINTroubleTickets;

public class ComService extends AsyncTask<String, String, String> {
	
	public static String serverURL = "http://ticket.algumavez.com/";
	//public static String serverURL = "http://192.168.102.240/tdin-webservice/";
	public static String extensionURL = ".json";
	ProgressDialog dialog;
	String methodName;
	Object object;
	boolean showProgress;
	TDINTroubleTickets tdin;
	
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
		tdin = (TDINTroubleTickets)((Context) object).getApplicationContext();
		String full_url = serverURL + url + extensionURL;
		this.methodName = methodName;
		this.object = object;
		this.execute(full_url, tdin.getEmail(), tdin.getPw());
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
		// JSONObject json = JSONHelper.string2JSON(response);
		// String status = JSONHelper.getValue(json, "status");

		// Log.v("onPostExecute()", response);
		if (showProgress) {
			dialog.dismiss();
		}
		try {
			Method method = object.getClass().getMethod(methodName, String.class);
			method.invoke(object, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}