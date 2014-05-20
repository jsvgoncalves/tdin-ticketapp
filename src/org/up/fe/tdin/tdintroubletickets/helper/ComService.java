package org.up.fe.tdin.tdintroubletickets.helper;


import java.lang.reflect.Method;

import org.up.fe.tdin.tdintroubletickets.R;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class ComService extends AsyncTask<String, String, String> {
	
	public static String serverURL = "http://localhost/tdin-webservice";
	ProgressDialog dialog;
	String methodName;
	Object object;
	boolean showProgress;
	
	@Override
	protected void onPreExecute(){}
	
	public ComService(String url, Object object, String methodName, boolean showProgress){
		this(url, object, methodName, showProgress, ((Context) object).getString(R.string.fetching_data));
	}
	
	public ComService(String url, Object object, String methodName, boolean showProgress, String dialogMessage) {
		Log.e("mylog", url );
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
	    return ComHelper.httpGet(params);
	}
	
	@Override
	protected void onPostExecute (String result){
//		System.out.println(result);
//		Log.e("mylog", "result " + result);
//		JSONObject json = JSONHelper.string2JSON(result);
//		String status = JSONHelper.getValue(json, "status");
		if (showProgress) {
			dialog.dismiss();
		}
		try {
			Method method = object.getClass().getMethod(methodName, String.class);
			method.invoke(object, result);
//			callback.invoke(object, json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}