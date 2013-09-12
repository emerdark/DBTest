package com.example.dbtest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddAccount extends Activity {
	
	private Database mDbHelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_account);
	}
	
	public void addAmount(View v){
		//Toast.makeText(getApplicationContext(), "this button works", Toast.LENGTH_LONG).show();			
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
		    Accounts accounts = new Accounts((double)valor,myEditValue2);	
		    
		    
		    mDbHelper = new Database(this);
	        mDbHelper.open();
	        mDbHelper.createNote(accounts);
	        mDbHelper.close();
	        Toast.makeText(getApplicationContext(), accounts.getText()+" account created with $"+ accounts.getAmount(), Toast.LENGTH_LONG).show();	
	       
	}
	
	



}

 
