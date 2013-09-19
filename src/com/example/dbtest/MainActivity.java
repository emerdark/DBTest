package com.example.dbtest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	
	 private static final int INSERT_ID = Menu.FIRST;
	    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		  if( getIntent().getBooleanExtra("Exit me", false)){
		        finish();
		        return; // add this to prevent from doing unnecessary stuffs
		    }
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, INSERT_ID, 0, R.string.menu_string);        
        return true;
    }
	
	 @Override
	    public boolean onMenuItemSelected(int featureId, MenuItem item) {
	        switch(item.getItemId()) {
	            case INSERT_ID:
	            	AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
	    			adb.setTitle("Are You Sure You Want To Exit?");
	    			adb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	    				public void onClick(DialogInterface dialog, int id) {
	    					Intent intent = new Intent(MainActivity.this, MainActivity.class);
	    	                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    	                intent.putExtra("Exit me", true);
	    	                startActivity(intent);
	    	                finish();
	    				}
	    			});
	    			adb.setNegativeButton("Cancel", null);
	    			adb.show();
	            	
	                return true;
	        }

	        return super.onMenuItemSelected(featureId, item);
	    }

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}
	
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
