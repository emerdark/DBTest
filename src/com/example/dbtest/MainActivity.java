package com.example.dbtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void showDB(View v){
		//Toast.makeText(getApplicationContext(), "this button works", Toast.LENGTH_LONG).show();
		Intent intent = new Intent(MainActivity.this, DisplayDB.class);
		MainActivity.this.startActivity(intent);		
		
	}
	
	public void addAccount(View v){
		Intent intent2 = new Intent(MainActivity.this, AddAccount.class);
		MainActivity.this.startActivity(intent2);		
		
	}
	

}
