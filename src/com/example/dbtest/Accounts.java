package com.example.dbtest;

public class Accounts {
	private double amount;
	private String text;
	private String type;
	
	public Accounts(){
		amount =0;//default value	
		text = "temp";
		type = "Debit";
	}
	public Accounts(double x, String temp, String temp2){
		amount = x;
		text = temp;
		type = temp2;
	}
	
	public void setAmount(double x){
		amount =x;
	}
	
	public double getAmount(){
		return amount;
	}
	
	public void setText(String temp){
		text =temp;
	}
	
	public String getText(){
		return text;
	}
	
	public void setType(String temp){
		type =temp;
	}
	
	public String getType(){
		return type;
	}

}
