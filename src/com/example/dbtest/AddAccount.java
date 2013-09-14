package com.example.dbtest;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddAccount extends Activity {

	private Database mDbHelper;
	private Spinner spinner1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_account);

	}

	public void addAmount(View v) {
		mDbHelper = new Database(this);
		mDbHelper.open();

		// populate Debit/Credit Using Spinner
		spinner1 = (Spinner) findViewById(R.id.type_spinner);
		String typeSelected = String.valueOf(spinner1.getSelectedItem());

		// create instance of Cursor to access database
		Cursor cursor = mDbHelper.getAllAccount();

		// get Amount and Account from user
		EditText valor1 = (EditText) findViewById(R.id.editText1);// get Amount
		String myEditValue = valor1.getText().toString().trim();

		// convert Amount to double
		double valor = 0;
		while (true) {
			try {
				valor = Double.parseDouble(myEditValue);
				break;
			} catch (Exception e) {
				// display exception message in the LogCat
				Log.e("logtag", "Exception: " + e.toString());
				Toast.makeText(getApplicationContext(),
						"No Numbers were Entered", Toast.LENGTH_LONG).show();
			}
		}
		//Get Account Name
		EditText valor2 = (EditText) findViewById(R.id.editText2);
		String myEditValue2 = valor2.getText().toString().trim();
		String account = "temp";
		boolean accountExists = false;
		//Check if the account pre-exists, if yes then accountExists = true
		while (cursor.moveToNext()) {
			account = cursor.getString(cursor.getColumnIndex(Database.KEY_ACC));
			if (account.matches(myEditValue2)) {
				Toast.makeText(getApplicationContext(),
						"Account already exits", Toast.LENGTH_LONG).show();
				accountExists = true;
				break;
			}
		}
		
		// create account if account doesn't exist
		if (!accountExists) {
			Accounts accounts = new Accounts((double) valor, myEditValue2,
					typeSelected);
			mDbHelper.createNote(accounts);
			Toast.makeText(getApplicationContext(), typeSelected + " " + accounts.getText()
							+ " account created with $" + accounts.getAmount(),
					Toast.LENGTH_LONG).show();
		}
		cursor.close();
		mDbHelper.close();

	}

}
