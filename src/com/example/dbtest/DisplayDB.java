package com.example.dbtest;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
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
		totalText.setText("You got $"+Double.toString(total));
				
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

}
