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
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.app.ActionBarActivity;


public class LEDInteractive extends ActionBarActivity implements SensorEventListener {
	
	static String pin;
	static String intensity;
	static String length;
	static InputStream is=null;
	static String result=null;
	static String line=null;
	static int code;
	
	private TextView lightLevel;
	private TextView capturedLevel;
    private SensorManager mSensorManager;
    private Sensor mLightSensor;
    public boolean buttonPressed = false;
    public float lightValue;
    public String capturedValue;
    public int lightValueInt;

	 @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ledinteractive);
        
        //SENSOR STUFF ----
        lightLevel= (TextView)findViewById(R.id.lightLevel);
        capturedLevel = (TextView)findViewById(R.id.capturedLevel);
        // Get an instance of the sensor service
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mLightSensor=mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
         
        PackageManager PM= this.getPackageManager();
        boolean light = PM.hasSystemFeature(PackageManager.FEATURE_SENSOR_LIGHT);
         
        if(light){
            Toast.makeText(getApplicationContext(),"Light sensor is detected. Ready to capture light for LED Intensity", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(getApplicationContext(),"No sensor is detected", Toast.LENGTH_SHORT).show();
        //---------------
        
        final EditText e_pin=(EditText) findViewById(R.id.input_LED1);
        final TextView e_intensity=(TextView) findViewById(R.id.capturedLevel);
        final EditText e_length=(EditText) findViewById(R.id.input_LED3);
        Button Go=(Button) findViewById(R.id.button2);
        
        Go.setOnClickListener(new View.OnClickListener() {
			
        	@Override
        	public void onClick (View v) {
        		// TODO Auto-generated method stub
    				
        		pin = e_pin.getText().toString();
        		if (lightValueInt >= 0 && lightValueInt <= 4)
        			intensity = "Low";
        		else if (lightValueInt >= 5 && lightValueInt <= 8)
        			intensity = "Medium";
        		else
        			intensity = "High";
        		length = e_length.getText().toString();

        		insert(getApplicationContext());
        	}

    	});
    }
	 
 	@Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something if sensor accuracy changes.
    }
    
    @Override
    public final void onSensorChanged(SensorEvent event) {
 
        /*float*/ lightValue = event.values[0];
        
        lightLevel.setText("Light level is: " + "" +lightValue); 

    }
    
    @Override
    protected void onResume() {
        // Register a listener for the sensor.
        super.onResume();
        mSensorManager.registerListener(this, mLightSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
 
    @Override
    protected void onPause() {
        // important to unregister the sensor when the activity pauses.
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
    
	public void lightCapture(View view) {
		capturedValue = Float.toString(lightValue);
		lightValueInt = Math.round(lightValue);
		capturedLevel.setText(capturedValue);
		Toast.makeText(getApplicationContext(),"Captured " + capturedValue, Toast.LENGTH_SHORT).show();
	}
	
 	public static void insert(Context context) { //, String fileName) {
		
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
        
        // MAKE A CHECK
    	Toast.makeText(context, "Inserted Successfully",
    			Toast.LENGTH_LONG).show();

	}

}
	

