package com.epam.catalog.bean;

import java.util.ArrayList;

public class Book {
	
	private String name;
	private ArrayList<String> authors;
	private ArrayList<String> genres;
	private News news;
	
	public Book(){}
	public Book(String name, ArrayList<String> authors, ArrayList<String> genres, String newsTitle, String newsText, String newsDate){
		this.name = name;
		this.authors = new ArrayList<String>();
		this.authors.addAll(authors);
		this.genres = new ArrayList<String>();
		this.genres.addAll(genres);
		this.news = new News(newsTitle, newsText, newsDate);
	}
	
		
	public String getName(){
		return this.name;
	}
	
	public ArrayList<String> getAuthors(){
		return this.authors;
	}
	
	public ArrayList<String> getGenres(){
		return this.genres;
	}
	public News getNews(){
		return this.news;
	}
	
	
}
