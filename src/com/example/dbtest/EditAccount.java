package com.example.dbtest;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class EditAccount extends Activity {

	private TextView accountText;
	private TextView amountext;
	private TextView typeText;
	private Long mRowId;
	private Database mDbHelper;
	private Button btnDisplay;	
	private EditText editText1;
	private EditText editText2;
	private EditText amountText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account_edit);

		editText1 = (EditText) findViewById(R.id.newAmount);
		editText2 = (EditText) findViewById(R.id.updateAmount);
		amountText = editText1;
		addListenerOnButton();

		mDbHelper = new Database(this);
		mDbHelper.open();

		accountText = (TextView) findViewById(R.id.currentAccount);
		amountext = (TextView) findViewById(R.id.currentAmount);
		
		//
		// //Button confirmButton = (Button) findViewById(R.id.confirm);
		mRowId = null;
		// mRowId = (savedInstanceState == null) ? null :
		// (Long) savedInstanceState.getSerializable(Database.KEY_ROWID);
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
		}
	}

	public void addListenerOnButton() {		
		btnDisplay = (Button) findViewById(R.id.update);

		btnDisplay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//Toast.makeText(getApplicationContext(),
					//	amountText.getText().toString(), Toast.LENGTH_LONG)
					//	.show();
				
				String title = accountText.getText().toString();
		        double body = Double.parseDouble(amountText.getText().toString());

//		        if (mRowId == null) {
//		            long id = mDbHelper.createNote(title, Double.parseDouble(body));
//		            if (id > 0) {
//		                mRowId = id;
//		            }
//		        } else {
		    	Toast.makeText(getApplicationContext(),
					String.valueOf(body), Toast.LENGTH_LONG)
					.show();
		            mDbHelper.updateNote(mRowId, title, body);
		     //   }

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
			}
			break;
		case R.id.radio2:
			if (checked) {
				editText1.setEnabled(false);
				editText2.setEnabled(true);
				amountText = editText2;
			}
			break;

		}

	}

}