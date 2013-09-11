package com.example.dbtest;

public class Accounts {
	private double amount;
	private String text;
	
	public Accounts(){
		amount =0;//default value	
		text = "temp";
	}
	public Accounts(double x, String temp){
		amount = x;
		text = temp;
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

}
