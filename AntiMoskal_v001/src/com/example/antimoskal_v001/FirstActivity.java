package com.example.antimoskal_v001;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.ImageView;
import android.app.AlertDialog;
import android.view.View;

public class FirstActivity extends Activity {

		
		// flag for Internet connection status
	    Boolean isInternetPresent = false;
	     
	    // Connection detector class
	    ConnectionDetector cd;
	 
	    private ProgressBar spinner;
	    
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_first);
	        spinner = (ProgressBar) findViewById(R.id.prgrBar);
	        spinner.setVisibility(View.VISIBLE);

	        // creating connection detector class instance
	        cd = new ConnectionDetector(getApplicationContext());
	        // get Internet status
	        isInternetPresent = cd.isConnectingToInternet();
	        // check for Internet status

	        if (isInternetPresent) {

	        	Thread t = new Thread(){
					public void run(){
						try{
						Thread.sleep(2000);
						mHandler.post(mUpdateResults);
						}catch(Exception e) {}
					}
				}; t.start();
	        	// Internet Connection is Present
	            // make HTTP requests
	        //    Intent i = new Intent(getApplicationContext(),MainActivity.class);
	         //   startActivity(i);
	        } 
	        else {
	            // Internet connection is not present
	            // Ask user to connect to Internet
	            showAlertDialog(FirstActivity.this, "No Internet Connection",
	            "You don't have internet connection.", false);
	        }
	      }
	    final Handler mHandler = new Handler();

	    // Create runnable for posting
	    final Runnable mUpdateResults = new Runnable() {
	        public void run() {
	        		spinner.setVisibility(View.GONE);
	        		Intent i = new Intent(getApplicationContext(),MainActivity.class);
	        		i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
	   	            startActivity(i);
	        }
	    };
				

		
    

    /**
     * Function to display simple Alert Dialog
     * @param context - application context
     * @param title - alert dialog title
     * @param message - alert message
     * @param status - success/failure (used to set icon)
     * */
    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
 
        // Setting Dialog Title
        alertDialog.setTitle(title);
 
        // Setting Dialog Message
        alertDialog.setMessage(message);
         
        // Setting alert dialog icon
        alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);
 
        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
 
        // Showing Alert Message
        alertDialog.show();
    }
}


