package com.example.dbtest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ArrayAdapter;
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
		
		//addListenerOnButton();
		//addListenerOnSpinnerItemSelection();
	}
	
	public void addAmount(View v){
		//Toast.makeText(getApplicationContext(), "this button works", Toast.LENGTH_LONG).show();	
		
		spinner1 = (Spinner) findViewById(R.id.type_spinner);	
		String typeSelected = String.valueOf(spinner1.getSelectedItem());
		
			EditText valor1 = (EditText) findViewById (R.id.editText1);//get Amount
			EditText valor2 = (EditText) findViewById (R.id.editText2);//get Account
		    String myEditValue = valor1.getText().toString().trim();	
		    String myEditValue2 = " temp";
		    myEditValue2 = valor2.getText().toString().trim();	
		    double valor=0;
		    try {
		        valor = Double.parseDouble(myEditValue); 
		    }
		    catch(Exception e) {
		        Log.e("logtag", "Exception: " + e.toString());//displays exception message in the LogCat
		        Toast.makeText(getApplicationContext(), "No Numbers were Entered", Toast.LENGTH_LONG).show();			
		    }		    
		    Accounts accounts = new Accounts((double)valor,myEditValue2, typeSelected);			    
		    
		    mDbHelper = new Database(this);
	        mDbHelper.open();
	        mDbHelper.createNote(accounts);
	        mDbHelper.close();
	      
	        Toast.makeText(getApplicationContext(), typeSelected+" "+accounts.getText()+" account created with $"+ accounts.getAmount(), Toast.LENGTH_LONG).show();	
	       
	}

//	public void addListenerOnSpinnerItemSelection() {
//		spinner1 = (Spinner) findViewById(R.id.type_spinner);
//		spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
//		
//	  }

//	// get the selected dropdown list value
//	  public void addListenerOnButton() {
//	 
//		spinner1 = (Spinner) findViewById(R.id.type_spinner);		
//		Button btnSubmit = (Button) findViewById(R.id.button1);
//	 
//		btnSubmit.setOnClickListener(new OnClickListener() {
//	 
//		  @Override
//		  public void onClick(View v) {
//	 
//		    Toast.makeText(AddAccount.this,
//			"OnClickListener : " + "\nSpinner 1 : "+ String.valueOf(spinner1.getSelectedItem()),	Toast.LENGTH_SHORT).show();
//		  }
//	 
//		});
//	  }
}

 
