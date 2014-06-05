	package com.example.antimoskal_v001;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TextView;
import android.hardware.Camera;

public class MainActivity extends Activity {
	TextView tvResults;
	TextView scanResults;
	String results = "", BarCode;
	public static Camera cam = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tvResults = (TextView) findViewById(R.id.tvResults);
		scanResults = (TextView) findViewById(R.id.Results); 
		
		//onClick search button
        Button searchBtn = (Button) findViewById(R.id.searchBtn);
		searchBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(getApplicationContext(),SearchActivity.class);
                startActivity(i);
            }
        });
		
		
		//onClick scan button
		Button scanBtn = (Button) findViewById(R.id.scanBtn);
		scanBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				try {
					
					Intent intent = new Intent(
							"com.google.zxing.client.android.SCAN");
					//flashLightOn(v);
					intent.putExtra("SCAN_MODE", "QR_CODE_MODE,PRODUCT_MODE");
					startActivityForResult(intent, 0);
					
				
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Toast.makeText(getApplicationContext(), "ERROR:" + e, 1).show();

				}

			//	flashLightOff(v);
			}
		});
	}
		//saving results
		public void onActivityResult(int requestCode, int resultCode, Intent intent) {
			if (requestCode == 0) {

				if (resultCode == RESULT_OK) {
					results = intent.getStringExtra("SCAN_RESULT");
					
				 try{
	                     
                         // CALL GetText method to make post method call
					 new Thread(new GetResponce()).start();
                 }
                catch(Exception ex)
                 {
                    scanResults.setText(" url exeption! " );
                 }
				 
					tvResults.setText("Success!!!");
				} else if (resultCode == RESULT_CANCELED) {
					
					tvResults.setText("Scan cancelled.");
				}
			}
			
			
			
			
		}
		

		



	
		class GetResponce implements Runnable {
			public void run(){
				
				try{
					
					scanResults.setText(GetText());

					
				} catch (Exception ex){
					ex.printStackTrace();
				}

				
				
			} 
		
		

		public  String  GetText()  throws  UnsupportedEncodingException
		      {
				  
			      BarCode = results;
	              String data = URLEncoder.encode("barcode", "UTF-8") + "=" + URLEncoder.encode(BarCode, "UTF-8"); 
	  		  
		          data += "&" + URLEncoder.encode("okbutton", "UTF-8") + "=" + URLEncoder.encode("Search", "UTF-8"); 
		                 
		          String text = "";
		          BufferedReader reader=null;
				  
		          try
		          { 
		  		    
		            URL url = new URL("http://antimoskal.jelastic.regruhosting.ru/pages/viewproduct");
		            URLConnection conn = url.openConnection(); 
				  
		            conn.setDoOutput(true); 

				    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream()); 
		            		    
		            wr.write( data ); 
		            wr.flush(); 
				  reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		          StringBuilder sb = new StringBuilder();
		          String line = null;
				  
		          // Read Server Response
		          while((line = reader.readLine()) != null)
		              {
		  		  
		        	         // Append server response in string
		                     sb.append(line);
		              }
		              
		              
		              text = sb.toString();
		          }
		          catch(Exception ex)
		          {
		        	  return "Url Exeption1!";
		        	  //results.setText(" url exeption1! " );
		          }
		          finally
		          {
		              try
		              {
		   
		                  reader.close();
		              }
		 
		              catch(Exception ex) {return " url exeption2! ";}
		          }
		                
		          // Show response on activity
		          if (text.length() > 0)
		        	  {
		        	  return text;
		        	  //results.setText(text);
		        	  }
		          else {
		        	  return "No response";
		        	  //results.setText("No response");
		        	  }
		          
		      }
		}


}
