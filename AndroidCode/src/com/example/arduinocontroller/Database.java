// Database.java
package com.example.arduinocontroller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.support.v7.app.ActionBarActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Database extends LED {
	
	static String pin;
	static String intensity;
	static String length;
	static InputStream is=null;
	static String result=null;
	static String line=null;
	static int code;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.led);
        
        final EditText e_pin=(EditText) findViewById(R.id.input_LED1);
        final EditText e_intensity=(EditText) findViewById(R.id.input_LED2);
        final EditText e_length=(EditText) findViewById(R.id.input_LED3);
        Button Go=(Button) findViewById(R.id.button2);
        
        Go.setOnClickListener(new View.OnClickListener() {
			
    		@Override
    		public void onClick (View v) {
    			// TODO Auto-generated method stub
    				
    			pin = e_pin.getText().toString();
    			intensity = e_intensity.getText().toString();
    			length = e_length.getText().toString();
    			
    	    	Toast.makeText(getApplicationContext(), "Button pressed",
    	    			Toast.LENGTH_SHORT).show();
    			
    			insert();
    		}

    	});
        
	}
	
	public static void stuff(Context context){
		Toast.makeText(context, "Database class has been called",
				Toast.LENGTH_SHORT).show();
	}
	
	public static void insert() { //, String fileName) {
		
    	Thread thread = new Thread(new Runnable(){
    		
    	    @Override
    	    public void run() {
    	    	
    	    	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    	    	nameValuePairs.add(new BasicNameValuePair("pin",pin));
    	    	nameValuePairs.add(new BasicNameValuePair("intensity",intensity));
    	    	nameValuePairs.add(new BasicNameValuePair("length",length));

	    	    try
	    	    {	
	    	    	HttpClient httpclient = new DefaultHttpClient();
	    	    	HttpPost httppost = new HttpPost("http://hammadmirza.com/arduinodb/LED.php");//("http://localhost/insert.php");//("hammad3.db.8869720.hostedresource.com");//("http://10.0.2.2/insert.php");
	    	    	//HttpPost httppost = new HttpPost("http://hammadmirza.com/arduinodb/" + fileName + ".php");
	    	    	httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	    	    	HttpResponse response = httpclient.execute(httppost); 
	    	    	HttpEntity entity = response.getEntity();
	    	    	is = entity.getContent();
	    	    	Log.e("pass 1", "connection success ");
	    	    }
	    	    
	    	    catch(Exception e)
	    	    {
	    	    	Log.e("Fail 1", e.toString());
	    	    	//Toast.makeText(getApplicationContext(), "Invalid IP Address",
	    	    		//	Toast.LENGTH_LONG).show();
	    	    }     
    	    }
    	});
    	
    	thread.start();
    	
        try
        {
            BufferedReader reader = new BufferedReader (new InputStreamReader(is,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            
            is.close();
            result = sb.toString();
            
            //System.out.println("Result is " + result.toString());
	    	//Toast.makeText(getApplicationContext(), "Result is" + result,
	    		//	Toast.LENGTH_LONG).show();
            
	    	Log.e("pass 2", "connection success ");
        }
        
        catch(Exception e)
        {
            Log.e("Fail 2", e.toString());
        }     
	}
}
