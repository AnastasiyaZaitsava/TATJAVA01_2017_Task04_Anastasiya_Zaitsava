package com.epam.catalog.service.factory;

import com.epam.catalog.service.impl.BookService;
import com.epam.catalog.service.impl.DiskService;
import com.epam.catalog.service.impl.FilmService;

public class ServiceFactory {
	private static final ServiceFactory instance = new ServiceFactory();
	
	private final BookService bookService = new BookService();
	private final DiskService diskService = new DiskService();
	private final FilmService filmService = new FilmService();
	
	private ServiceFactory(){}
	
	public static ServiceFactory getInstance(){
		return instance;
	}
	
	public BookService getBookService(){
		return bookService;
	}
	public DiskService getDiskService(){
		return diskService;
	}
	public FilmService getFilmService(){
		return filmService;
	}
}
