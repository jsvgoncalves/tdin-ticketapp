package org.up.fe.tdin.tdintroubletickets;

import org.up.fe.tdin.tdintroubletickets.helper.ComService;

import android.os.Bundle;
import android.os.Debug;
import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {

	ProgressDialog dialog;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        

        // Try to login
        new ComService(
			"/", 
			MainActivity.this, 
			"loginDone",
			false
        ); // Hide progress bar cuz we will set it manually
	
        
    }
    
    public void loginDone(String result) {
		Log.d("loginDone()", "I've logged in");
	}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
