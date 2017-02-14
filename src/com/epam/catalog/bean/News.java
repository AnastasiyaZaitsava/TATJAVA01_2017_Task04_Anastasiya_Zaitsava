package com.epam.catalog.bean;

public class News {
	private String title;
	private String text;
	private String date;
	
	public News(){}
	public News(String title, String text, String date){
		this.title = title;
		this.text = text;
		this.date = date;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public String getText(){
		return this.text;
	}
	
	public String getDate(){
		return this.date;
	}
}
