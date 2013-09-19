package com.example.dbtest;

import java.io.IOException;
import java.text.NumberFormat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class EditAccount extends Activity {

	private TextView accountText;
	private TextView amountext;
	private TextView typeText;
	private Long mRowId;
	private Database mDbHelper;
	private Button btnDisplay;
	private Button update;
	private Button addButton, delButton;
	private EditText editText1;
	private EditText editText2;
	private EditText amountText;
	private double currentAmount = 0;
	private NumberFormat format = NumberFormat.getCurrencyInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account_edit);

		editText1 = (EditText) findViewById(R.id.newAmount);
		editText2 = (EditText) findViewById(R.id.updateAmount);
		update = (Button) findViewById(R.id.update);
		addButton = (Button) findViewById(R.id.addAmount);
		delButton = (Button) findViewById(R.id.delAmount);
		addButton.setEnabled(false);
		delButton.setEnabled(false);
		amountText = editText1;
		addListenerOnButton();

		mDbHelper = new Database(this);
		mDbHelper.open();

		accountText = (TextView) findViewById(R.id.currentAccount);
		amountext = (TextView) findViewById(R.id.currentAmount);

		mRowId = null;
		if (mRowId == null) {
			Bundle extras = getIntent().getExtras();
			mRowId = extras != null ? extras.getLong(Database.KEY_ROWID) : null;
		}
		populateFields();

	}

	private void populateFields() {
		if (mRowId != null) {
			Cursor note = mDbHelper.fetchNote(mRowId);
			startManagingCursor(note);
			accountText.setText(note.getString(note
					.getColumnIndexOrThrow(Database.KEY_ACC)));
			amountext.setText(note.getString(note
					.getColumnIndexOrThrow(Database.KEY_AMT)));
			editText1.setText(note.getString(note
					.getColumnIndexOrThrow(Database.KEY_AMT)));
			currentAmount = Double.parseDouble(amountext.getText().toString());
		}
	}

	public void addListenerOnButton() {
		btnDisplay = (Button) findViewById(R.id.update);
		btnDisplay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String title = accountText.getText().toString();
				double body = 0;
				try {
					body = Double.parseDouble(amountText.getText().toString());
					mDbHelper.updateNote(mRowId, title, body);
					Toast.makeText(getApplicationContext(),
							"Account Updated to " + String.valueOf(body),
							Toast.LENGTH_LONG).show();
					populateFields();

				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), e.getMessage(),
							Toast.LENGTH_SHORT).show();
				}

			}
		});
	}

	public void onRadioButtonClicked(View view) {
		// Is the button now checked?
		boolean checked = ((RadioButton) view).isChecked();
		// Check which radio button was clicked
		switch (view.getId()) {
		case R.id.radio1:
			if (checked) {
				editText1.setEnabled(true);
				editText2.setEnabled(false);
				amountText = editText1;
				update.setEnabled(true);
				addButton.setEnabled(false);
				delButton.setEnabled(false);
			}
			break;
		case R.id.radio2:
			if (checked) {
				editText1.setEnabled(false);
				editText2.setEnabled(true);
				amountText = editText2;
				update.setEnabled(false);
				addButton.setEnabled(true);
				delButton.setEnabled(true);
			}
			break;
		}
	}

	public void addAmount(View v) {
		String title = accountText.getText().toString();
		double body;
		try {
			body = Double.parseDouble(amountText.getText().toString());

			mDbHelper.updateNote(mRowId, title, currentAmount + body);
			populateFields();
			Toast.makeText(getApplicationContext(),
					format.format(body) + " has been added", Toast.LENGTH_LONG)
					.show();
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), e.getMessage(),
					Toast.LENGTH_SHORT).show();
		}
	}

	public void delAmount(View v) {
		String title = accountText.getText().toString();
		double body = Double.parseDouble(amountText.getText().toString());
		mDbHelper.updateNote(mRowId, title, currentAmount - body);
		populateFields();
		Toast.makeText(getApplicationContext(),
				format.format(body) + " has been deleted", Toast.LENGTH_LONG)
				.show();
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
	            	AlertDialog.Builder adb = new AlertDialog.Builder(EditAccount.this);
	    			adb.setTitle("Are You Sure You Want To Exit?");
	    			adb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	    				public void onClick(DialogInterface dialog, int id) {
	    					Intent intent = new Intent(EditAccount.this, MainActivity.class);
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