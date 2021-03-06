package com.epam.catalog.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

import com.epam.catalog.bean.Book;
import com.epam.catalog.bean.Disk;
import com.epam.catalog.bean.Film;

public class Util {

	public Util(){}
	
	public void showNews(Book book){
		System.out.println(book.getName());
		ArrayList<String> authors = book.getAuthors();
		for(String author: authors){
			System.out.print(author);
			if (!author.equals(authors.get(authors.size()-1))){
				System.out.print(", ");
			}
		}
		System.out.println("");
		ArrayList<String> genres = book.getGenres();
		for(String genre: genres){
			System.out.print(genre);
			if (!genre.equals(genres.get(genres.size()-1))){
				System.out.print(", ");
			}
		}
		System.out.println("");
		System.out.println(book.getNews().getTitle());
		System.out.println(book.getNews().getText());
		System.out.println(book.getNews().getDate());
		
	}
	
	public void showNews(Disk disk){
		System.out.println(disk.getName());
		System.out.println(disk.getContent());
		System.out.println(disk.getProducer());
		System.out.println(disk.getNews().getTitle());
		System.out.println(disk.getNews().getText());
		System.out.println(disk.getNews().getDate());
	}
	
	public void showNews(Film film){
		System.out.println(film.getName());
		ArrayList<String> genres = film.getGenres();
		for(String genre: genres){
			System.out.print(genre);
			if (!genre.equals(genres.get(genres.size()-1))){
				System.out.print(", ");
			}
			else{
				System.out.println(".");
			}
		}
		ArrayList<String> actors = film.getActors();
		for(String actor: actors){
			System.out.print(actor);
			if (!actor.equals(actors.get(actors.size()-1))){
				System.out.print(", ");
			}
			else{
				System.out.println(".");
			}
		}
		System.out.println(film.getNews().getTitle());
		System.out.println(film.getNews().getText());
		System.out.println(film.getNews().getDate());
		
	}
	
	public ArrayList<String> parseAndConvert(String exp, String regex){ 	//parse string and convert it to ArrayList
		String[] parsedExp = exp.split(regex);
		ArrayList<String> list = new ArrayList<String>();
		Collections.addAll(list, parsedExp);
		return list;
		
	}
	
	public String getIDifExists(Statement st, String tableName, String column, String parameter){
		try {
			ResultSet rs = st.executeQuery("select * from `"+ tableName +"` where " 
											+ column + "= '" + parameter +"'");
			if (rs.next()){
				String id = rs.getString(1);
				return id;
			}
			else{
				rs.close();
				return "0";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "0";
		}
		
	}
	
	public String getNewID(Connection con, String tableName){
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM " + tableName);
			rs.last();
			int nextRow = rs.getInt(1) + 1;
			rs.close();
			st.close();
			return String.valueOf(nextRow);
		} catch (SQLException e) {
			e.printStackTrace();
			return "0";
		}
	}
	
}
