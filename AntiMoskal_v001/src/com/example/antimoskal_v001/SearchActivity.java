package com.example.antimoskal_v001;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


/* for post data method
import  org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.methods.HttpPost;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.client.ClientProtocolException;
*/


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class SearchActivity extends Activity {

	EditText searchField;
	TextView results;
	Button sendBtn;
	String BarCode, res="";
	private ProgressBar spinner;
	
/*	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

	StrictMode.setThreadPolicy(policy);*/
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		
        spinner = (ProgressBar) findViewById(R.id.prgrBar);
		searchField = (EditText) findViewById(R.id.SearchField);
		results = (TextView) findViewById(R.id.Results);
		sendBtn = (Button) findViewById(R.id.srchBtn);
		
		spinner.setVisibility(View.GONE);
		sendBtn.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v)
            {
				spinner.setVisibility(View.VISIBLE);    
				new Thread(new GetResponce()).start();
            }
			
		});
	}
	
	class GetResponce implements Runnable {
		public void run(){
			
			try{
				
				results.setText(GetText());
	            runOnUiThread(new Runnable() {  
	            @Override
	            	public void run() {
	                    // TODO Auto-generated method stub

	                	spinner.setVisibility(View.GONE);
	                }
	            });
				
			} catch (Exception ex){
				ex.printStackTrace();
			}

			
			
		} 
	
	

	public  String  GetText()  throws  UnsupportedEncodingException
	      {
			  
		      BarCode = searchField.getText().toString();
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


