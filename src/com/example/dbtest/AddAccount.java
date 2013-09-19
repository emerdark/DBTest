package com.example.dbtest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddAccount extends Activity {

	private Database mDbHelper;
	private Spinner spinner1;
	private EditText valor1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_account);
		valor1 = (EditText) findViewById(R.id.editText1);// get Amount
		
		valor1.setOnKeyListener(new OnKeyListener() {
		    public boolean onKey(View v, int keyCode, KeyEvent event) {
		        // If the event is a key-down event on the "enter" button
		        if ((event.getAction() == KeyEvent.ACTION_DOWN) ||
		            (keyCode == KeyEvent.KEYCODE_ENTER)) {
		          // Perform action on key press
		        	InputMethodManager imm = 
		                    (InputMethodManager)getApplicationContext().getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
		                 imm.hideSoftInputFromWindow(valor1.getWindowToken(), 0);
		          //Toast.makeText(AddAccount.this, "Hello", Toast.LENGTH_SHORT).show();
		          return true;
		        }
		        return false;
		    }
		});
	}

	public void addAmount(View v) {
		mDbHelper = new Database(this);
		mDbHelper.open();
		
		//Context context = new Context(this);
		// populate Debit/Credit Using Spinner
		spinner1 = (Spinner) findViewById(R.id.type_spinner);
		String typeSelected = String.valueOf(spinner1.getSelectedItem());

		// create instance of Cursor to access database
		Cursor cursor = mDbHelper.getAllAccount();

		// get Amount and Account from user	
		 
		if(valor1 == null)
			Toast.makeText(getApplicationContext(),
					"Invalid Entry. Please Re-Enter!!!", Toast.LENGTH_LONG)
					.show();
		else{
		
		String myEditValue = valor1.getText().toString().trim();
		
		// convert Amount to double
		double valor = 0;
		while (true) {
			try {
				valor = Double.parseDouble(myEditValue);
				break;
			} catch (Exception e) {
				// display exception message in the LogCat good for debugging
				Log.e("logtag", "Exception: " + e.toString());
				Toast.makeText(getApplicationContext(),
						"Invalid Entry. Please Re-Enter!!!", Toast.LENGTH_LONG)
						.show();
			}
		}
		// Get Account Name
		EditText valor2 = (EditText) findViewById(R.id.editText2);
		String myEditValue2 = valor2.getText().toString().trim();
		String account = "temp";
		boolean accountExists = false;
		// Check if the account pre-exists, if yes then accountExists = true
		while (cursor.moveToNext()) {
			account = cursor.getString(cursor.getColumnIndex(Database.KEY_ACC));
			if (account.matches(myEditValue2)) {
				Toast.makeText(getApplicationContext(),
						"Account already exits", Toast.LENGTH_LONG).show();
				accountExists = true;
				break;
			} else if (account == "" || account.matches("temp")) {
				Toast.makeText(getApplicationContext(),
						"No Account Name Or Bad Name!!!", Toast.LENGTH_LONG)
						.show();
				accountExists = true;
				break;
			}
		}

		// create account if account doesn't exist
		if (!accountExists) {
			Accounts accounts = new Accounts((double) valor, myEditValue2,
					typeSelected);
			mDbHelper.createNote(accounts);

			AlertDialog.Builder adb = new AlertDialog.Builder(AddAccount.this);
			adb.setTitle("Account Created");
			adb.setMessage("Account Has Been Created");
			adb.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					Toast.makeText(getApplicationContext(), " Account Created",
							Toast.LENGTH_LONG).show();
				}
			});
			// adb.setNegativeButton("Cancel", null);
			adb.show();

		}
		cursor.close();
		mDbHelper.close();

	}
	}

}
