package com.example.arduinocontroller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.support.v7.app.ActionBarActivity;

public class SelectionScreen extends Activity {

	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        // TODO Auto-generated method stub
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.selections2);
	    }
	 
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.main, menu);
			return true;
		}

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			// Handle action bar item clicks here. The action bar will
			// automatically handle clicks on the Home/Up button, so long
			// as you specify a parent activity in AndroidManifest.xml.
			int id = item.getItemId();
			if (id == R.id.action_settings) {
				return true;
			}
			return super.onOptionsItemSelected(item);
		}
		
		public void goBack(View view){
			startActivity(new Intent(this, myMainScreen.class));
			//finish();
		}
		
		public void LED(View view){
			startActivity(new Intent(this, LED.class));
			//finish();
		}
		
		public void StepperMotor(View view){
			startActivity(new Intent(this, StepperMotor.class));
			//finish();
		}
		
		public void Speaker(View view){
			startActivity(new Intent(this, Speaker.class));
			//finish();
		}
		
		
		
		/*
		public boolean selecting(View view){
			// Is the button now checked?
			boolean checked = ((RadioButton) view).isChecked();
			return checked;
		}*/
		
		/*
		public void selecting(View view){
			
			
			// Is the button now checked?
		    boolean checked = ((RadioButton) view).isChecked();
		    
		    // Check which radio button was clicked
		    switch(view.getId()) {
		        case R.id.radio_LED:
		            if (checked)
		            	startActivity(new Intent(this, LED.class));
		            break;
		        case R.id.radio_speaker:
		            if (checked)
		            	startActivity(new Intent(this, Speaker.class));
		            break;
		        case R.id.radio_SMotor:
		        	if (checked)
		        		startActivity(new Intent(this, StepperMotor.class));
		        	break;
		        case R.id.radio_other:
		        	if (checked)
		        		startActivity(new Intent(this, MainActivity.class));
		        	break;
		        case R.id.radio_sensor:
		        	if (checked)
		        		startActivity(new Intent(this, UseSensor.class));
		    }
		} */
}
