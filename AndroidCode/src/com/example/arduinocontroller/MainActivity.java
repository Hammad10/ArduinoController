package com.example.arduinocontroller;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;

// This activity is the splash screen
// Codebase for this activity taken from http://www.onlymobilepro.com/2013/01/16/android-beginner-creating-splash-screen/

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);	
		
	       Thread logoTimer = new Thread() {
	            public void run(){
	                try{
	                    int logoTimer = 0;
	                    while(logoTimer < 4000){
	                        sleep(100);
	                        logoTimer = logoTimer +100;
	                    };
	                    startActivity(new Intent("com.example.CLEARSCREEN"));
	                } 
	                 
	                catch (InterruptedException e) {
	                    e.printStackTrace();
	                } 
	                finally {
	                    finish();
	                }
	            }
	       };
	       logoTimer.start();
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
}
