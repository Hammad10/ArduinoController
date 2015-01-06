package com.example.arduinocontroller;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Speaker extends ActionBarActivity {

	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        // TODO Auto-generated method stub
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.speaker);
	        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	    }
	
	 	public void backToSelections(View view) {
			startActivity(new Intent(this, SelectionScreen.class));
			finish();
	 	}
}
