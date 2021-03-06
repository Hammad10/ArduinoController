package com.example.arduinocontroller;

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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.support.v7.app.ActionBarActivity;

// Codebase for the insert(Context context) method taken from: http://sampleprogramz.com/android/mysqldb.php

public class LED extends ActionBarActivity {
	
	static String pin;
	static String intensity;
	static String length;
	static InputStream is = null;
	static String result = null;
	static String line = null;
	static int code;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.led);
        
        // Spinner initialization code taken from: http://developer.android.com/guide/topics/ui/controls/spinner.html
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Spinner spinner = (Spinner) findViewById(R.id.input_LED2);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
             R.array.input_LED2, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        
        final EditText e_pin=(EditText) findViewById(R.id.input_LED1);
        final Spinner e_intensity=(Spinner) findViewById(R.id.input_LED2);
        final EditText e_length=(EditText) findViewById(R.id.input_LED3);
        Button Go=(Button) findViewById(R.id.button2);
        
        Go.setOnClickListener(new View.OnClickListener() {
			
	    	@Override
	    	public void onClick (View v) {
	
	    		pin = e_pin.getText().toString();
	    		intensity = e_intensity.getSelectedItem().toString();
	    		length = e_length.getText().toString();
				
	    		insert(getApplicationContext());
	    	}

    	});
    }
	 
 	public static void insert(Context context) {
		
    	Thread thread = new Thread(new Runnable(){
    		
    	    @Override
    	    public void run() {
    	    	
    	    	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    	    	nameValuePairs.add(new BasicNameValuePair("pin",pin));
    	    	nameValuePairs.add(new BasicNameValuePair("intensity",intensity));
    	    	nameValuePairs.add(new BasicNameValuePair("length",length));

	    	    try {	
	    	    	HttpClient httpclient = new DefaultHttpClient();
	    	    	HttpPost httppost = new HttpPost("http://xxxx/LED.php");
	    	    	httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	    	    	HttpResponse response = httpclient.execute(httppost); 
	    	    	HttpEntity entity = response.getEntity();
	    	    	is = entity.getContent();
	    	    	Log.e("pass 1", "connection success ");
	    	    }
	    	    
	    	    catch(Exception e) {
	    	    	Log.e("Fail 1", e.toString());
	    	    }     
    	    }
    	});
    	
    	thread.start();
    	
        try {
            BufferedReader reader = new BufferedReader (new InputStreamReader(is,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            
            is.close();
            result = sb.toString();
            
	    	Log.e("pass 2", "connection success ");
        }
        
        catch(Exception e) {
            Log.e("Fail 2", e.toString());
        }
        
        // Need to implement a check for successful insertion
    	Toast.makeText(context, "Inserted Successfully", Toast.LENGTH_LONG).show();

}

	public void goInteractive (View view) {
		
		startActivity(new Intent(this, LEDInteractive.class));
		
	}
}
	

