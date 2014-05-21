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
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.util.EntityUtils;
import org.apache.http.HttpStatus;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.util.Base64;
import android.util.Log;
/**
 * Contains facilities to connect to the REST server
 * @author joao
 *
 */
public class ComHelper{

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

	        // Add the login header each time.
	        String credentials = "jsvgoncalves@gmail.com" + ":" + "123456";  
	        String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);  
	        request.addHeader("Authorization", "Basic " + base64EncodedCredentials);
	        
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
	 * Performs an HTTP GET Request.
	 * @param params 0-> URL
	 * @return  401 - If the login fails.
	 * 			The content if all goes well
	 */
	public static String getHTTP(String... params) {
		HttpClient httpclient = new DefaultHttpClient();  
		HttpUriRequest request = new HttpGet(params[0]); // Or HttpPost(), depends on your needs  

		// The authorization part
		String credentials = "jsvgoncalves@gmail.com" + ":" + "123456";  
		String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);  
		request.addHeader("Authorization", "Basic " + base64EncodedCredentials);
		
		try {
			HttpResponse response = httpclient.execute(request);
			// Check the response status for login.
			if( !isLoggedIn( response.getStatusLine().getStatusCode() ) ) {
				return "" + HttpStatus.SC_UNAUTHORIZED;
			}

			// The status of the response (eg. 200, 404)
			// Log.v("http", response.getStatusLine().getStatusCode() + "");
			// The content of the response
			//Log.v("http-content",  EntityUtils.toString(response.getEntity()));
			return EntityUtils.toString(response.getEntity());
		} catch(Exception e) {

		}
		return null;
	}

	public static boolean isLoggedIn(int status) {
		if(status == HttpStatus.SC_UNAUTHORIZED) {
			return false;
		}

		return true;
	}
	
	public static boolean isOnline(Context context) {
		ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
	    return (networkInfo != null && networkInfo.isConnected());
	}
	
}