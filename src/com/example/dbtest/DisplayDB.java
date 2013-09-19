package com.example.dbtest;

import java.text.NumberFormat;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class DisplayDB extends ListActivity {
	private Database mDbHelper;
	private static final int ACTIVITY_EDIT=1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.database_list);
		fillData();
	}

	//calls from database two rows and displays them
	private void fillData() {
		NumberFormat format = NumberFormat.getCurrencyInstance();
		mDbHelper = new Database(this);
		mDbHelper.open();
		Cursor accountsCursor = mDbHelper.fetchAllNotes();
		startManagingCursor(accountsCursor);
		SimpleCursorAdapter notes = new SimpleCursorAdapter(this,
				R.layout.accounts_row, accountsCursor, new String[]{Database.KEY_ACC ,Database.KEY_AMT, Database.KEY_TYPE}, new int[]{R.id.text1, R.id.text2, R.id.text3});
		setListAdapter(notes);		
		
		double total=0;
		String type="Debit";
		while(accountsCursor.moveToNext()){	
			type = accountsCursor.getString(accountsCursor.getColumnIndex(Database.KEY_TYPE));
			if(type.matches("Debit"))
			total+= accountsCursor.getDouble(accountsCursor.getColumnIndex(Database.KEY_AMT));		
			else
				total-= accountsCursor.getDouble(accountsCursor.getColumnIndex(Database.KEY_AMT));	
		}	
		
		TextView totalText = (TextView)(this.findViewById(R.id.text3));
		totalText.setText("You Got "+format.format(total));
				
		//close Cursor
		//accountsCursor.close();
		//close the database
		//mDbHelper.close();
		
	}
	
	@Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
//        Intent i = new Intent(this, EditAccount.class);
//        i.putExtra(Database.KEY_ROWID, id);
//        startActivityForResult(i, ACTIVITY_EDIT);
        
    	Intent intent2 = new Intent(DisplayDB.this, EditAccount.class);
    	intent2.putExtra(Database.KEY_ROWID, id);
		DisplayDB.this.startActivity(intent2);		
    }
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, Menu.FIRST, 0, R.string.menu_string);        
        return true;
    }
	
	 @Override
	    public boolean onMenuItemSelected(int featureId, MenuItem item) {
	        switch(item.getItemId()) {
	            case Menu.FIRST:
	            	AlertDialog.Builder adb = new AlertDialog.Builder(DisplayDB.this);
	    			adb.setTitle("Are You Sure You Want To Exit?");
	    			adb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	    				public void onClick(DialogInterface dialog, int id) {
	    					Intent intent = new Intent(DisplayDB.this, MainActivity.class);
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

}
