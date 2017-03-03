package com.epam.catalog.bean;

import java.util.ArrayList;

public class Film {

	private String name;
	private ArrayList<String> genres;
	private ArrayList<String> actors;
	private News news;
	
	public Film(){}
	public Film(String name, ArrayList<String> genres, ArrayList<String> actors, String newsTitle, String newsText, String newsDate){
		this.name = name;
		this.genres = new ArrayList<String>();
		this.genres.addAll(genres);
		this.actors = new ArrayList<String>();
		this.actors.addAll(actors);
		this.news = new News(newsTitle, newsText, newsDate);
	}
		
	public String getName(){
		return this.name;
	}
	
	public ArrayList<String> getActors(){
		return this.actors;
	}
	
	public ArrayList<String> getGenres(){
		return this.genres;
	}
	public News getNews(){
		return this.news;
	}
	
	public boolean equals(Object obj){
		if (this == obj) {
			return true;
		}
		
		if (null == obj) {
			return false;
		}

		if (getClass() != obj.getClass()) {
			return false;
		}
		
		Film film = (Film) obj;
		if (!name.equals(film.name)){
			return false;
		}
		if(!actors.containsAll(film.actors) || !film.actors.containsAll(actors)){
			return false;
		}
		
		if(!genres.containsAll(film.genres) || !film.genres.containsAll(genres)){
			return false;
		}
		
		if(!news.equals(film.news)){
			return false;
		}
		return true;
	}
	
}
