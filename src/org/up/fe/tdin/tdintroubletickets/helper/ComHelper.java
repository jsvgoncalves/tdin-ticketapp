package org.up.fe.tdin.tdintroubletickets.helper;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.util.Log;
/**
 * Contains facilities to connect to the REST server
 * @author joao
 *
 */
public class ComHelper{
	
//	public static final String JSON_EXTENSION = ".json";
	//public static String serverURL = "http://busphone-service.herokuapp.com/";
	


	private static String readStream(InputStream is) {
		try {
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			int i = is.read();
			while(i != -1) {
				bo.write(i);
				i = is.read();
			}
			return bo.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * Makes a POST call to the server.
	 * @param params 0-> Call Method = POST; 1-> URL; ... -> Params
	 * @return
	 */
	public static String httpPost(String... params) {
		HttpClient httpclient = new DefaultHttpClient();
	    HttpPost request = new HttpPost(params[1]);

	    try {
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	        for (int i = 1; i+1 < params.length; i+=2) {
	        	nameValuePairs.add(new BasicNameValuePair(params[i], params[i+1]));
			}
	        if (!nameValuePairs.isEmpty()) {
	        	request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			}

	        // Execute HTTP Post Request
	        HttpResponse response = httpclient.execute(request);
	        System.out.println(response.toString());
	        return response.toString();
	    } catch (ClientProtocolException e) {
	    	e.printStackTrace();
			return "Client Protocol Exception";
	    } catch (IOException e) {
	    	e.printStackTrace();
			return "POST: Bad Con";
	    }
	}

	/**
	 * Makes a GET call to the server.
	 * @param params 0-> Call Method = GET; 1-> URL
	 * @return
	 */
	public static String httpGet(String... params) {
//		getApplicationContext()
		Log.v("mylog", "doing get");
		try {
			URL url = new URL(params[0]);
			Log.v("mylog", params[0]);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			InputStream in = new BufferedInputStream(urlConnection.getInputStream());
			String r = readStream(in);
			urlConnection.disconnect();
			return r;
		} catch (MalformedURLException e){
			e.printStackTrace();
			return "GET: Bad URL";
		} catch (IOException e) {
			e.printStackTrace();
			return "GET: Bad Con";
		}
	}
	
	protected void onPostExecute(String result) {
        System.err.println(result);
    }
	
	public static boolean isOnline(Context context) {
		ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
	    return (networkInfo != null && networkInfo.isConnected());
	}
	
}