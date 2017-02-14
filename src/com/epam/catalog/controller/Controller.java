package com.epam.catalog.controller;

import java.util.ArrayList;

import com.epam.catalog.bean.Book;
import com.epam.catalog.bean.Disk;
import com.epam.catalog.bean.Film;
import com.epam.catalog.bean.SearchCriteries;
import com.epam.catalog.controller.exception.ControllerException;
import com.epam.catalog.service.exception.ServiceException;
import com.epam.catalog.service.factory.ServiceFactory;
import com.epam.catalog.service.impl.BookService;
import com.epam.catalog.service.impl.DiskService;
import com.epam.catalog.service.impl.FilmService;

public class Controller {
	
	public Controller(){}
	
	public void addBook(String[] news) throws ControllerException{
		ServiceFactory serviceObjectFactory = ServiceFactory.getInstance(); 
		BookService bookService = serviceObjectFactory.getBookService(); 
		try {
			bookService.addNews(news);
		} catch (ServiceException e) {
			// TODO logging
			throw new ControllerException();
		} 
	}
	
	public void addDisk(String[] news) throws ControllerException{
		ServiceFactory serviceObjectFactory = ServiceFactory.getInstance(); 
		DiskService diskService = serviceObjectFactory.getDiskService(); 
		try {
			diskService.addNews(news);
		} catch (ServiceException e) {
			// TODO logging
			throw new ControllerException();
		} 
		
	}
	
	public void addFilm(String[] news) throws ControllerException{
		ServiceFactory serviceObjectFactory = ServiceFactory.getInstance(); 
		FilmService filmService = serviceObjectFactory.getFilmService(); 
		try {
			filmService.addNews(news);
		} catch (ServiceException e) {
			// TODO logging
			throw new ControllerException();		
			} 
		
	}
	
	public ArrayList<Book> findBook(ArrayList<String> parameters) throws ControllerException{
		SearchCriteries criteries = formCriteries(parameters);
		ServiceFactory serviceObjectFactory = ServiceFactory.getInstance(); 
		BookService bookService = serviceObjectFactory.getBookService(); 
		ArrayList<Book> foundBooks = new ArrayList<Book>();
		try {
			foundBooks = bookService.findNews(criteries);
		} catch (ServiceException e) {
			throw new ControllerException();		
			} 
		return foundBooks;
	}
	
	public ArrayList<Disk> findDisk(ArrayList<String> parameters) throws ControllerException{
		SearchCriteries criteries = formCriteries(parameters);
		ServiceFactory serviceObjectFactory = ServiceFactory.getInstance(); 
		DiskService diskService = serviceObjectFactory.getDiskService(); 
		ArrayList<Disk> foundDisks = new ArrayList<Disk>();
		try {
			foundDisks = diskService.findNews(criteries);
		} catch (ServiceException e) {
			throw new ControllerException();
		} 
		return foundDisks;
	}
	
	public ArrayList<Film> findFilm(ArrayList<String> parameters) throws ControllerException{
		SearchCriteries criteries = formCriteries(parameters);
		ServiceFactory serviceObjectFactory = ServiceFactory.getInstance(); 
		FilmService filmService = serviceObjectFactory.getFilmService(); 
		ArrayList<Film> foundFilms = new ArrayList<Film>();
		try {
			foundFilms = filmService.findNews(criteries);
		} catch (ServiceException e) {
			throw new ControllerException();
		} 
		return foundFilms;
	}
	
	public static SearchCriteries formCriteries(ArrayList<String> parameters){
		String[] splitted;
		String[] categories = new String[parameters.size()];
		String[] items = new String[parameters.size()];
		for(int i = 0; i < parameters.size(); i++){
			splitted = parameters.get(i).split(" ", 2);
			categories[i] = splitted[0];
			items[i] = splitted[1];
		}
		SearchCriteries criteries = new SearchCriteries(categories, items);
		return criteries;
	}
}
