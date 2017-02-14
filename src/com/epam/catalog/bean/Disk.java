package com.epam.catalog.bean;


public class Disk {
	
	private String name;
	private String content;
	private String producer;
	private News news;
	
	public Disk(){}
	
	public Disk(String name, String content, String producer, String newsTitle, String newsText, String newsDate){
		this.name = name;
		this.content = content;
		this.producer = producer;
		this.news = new News(newsTitle, newsText, newsDate);
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getContent(){
		return this.content;
	}
	
	public String getProducer(){
		return this.producer;
	}
	
	public News getNews(){
		return this.news;
	}
	

}
