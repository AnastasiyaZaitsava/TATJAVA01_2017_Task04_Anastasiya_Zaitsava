package com.epam.catalog.service.impl;

import java.util.ArrayList;

import com.epam.catalog.bean.Book;
import com.epam.catalog.bean.SearchCriteries;
import com.epam.catalog.dao.BookDAO;
import com.epam.catalog.dao.exception.DAOException;
import com.epam.catalog.dao.factory.DAOFactory;
import com.epam.catalog.service.exception.ServiceException;
import com.epam.catalog.util.Util;

public class BookService {
	public void addNews(String[] attributes) throws ServiceException{
		/* 0 - name
		   1 - authors
		   2 - genres
		   3 - news title
		   4 - news text
		   5 - news date */
		Util util = new Util();
		String name = attributes[0];
		ArrayList<String> authors = new ArrayList<String>();
		authors.addAll(util.parseAndConvert(attributes[1], ", "));
		ArrayList<String> genres = new ArrayList<String>();
		genres.addAll(util.parseAndConvert(attributes[2], ", "));
		Book book = new Book(name, authors, genres, attributes[3], attributes[4], attributes[5]);
		DAOFactory daoObjectFactory = DAOFactory.getInstance(); 
		BookDAO bookDAO = daoObjectFactory.getBookDAO(); 
		try {
			bookDAO.addBook(book);
		} catch (DAOException e) {
			// TODO logging
			throw new ServiceException();
		}
		
	}
	public ArrayList<Book> findNews(SearchCriteries criteries) throws ServiceException{
		ArrayList<Book> foundBooks = new ArrayList<Book>();
		try {
			DAOFactory daoObjectFactory = DAOFactory.getInstance(); 
			BookDAO bookDAO = daoObjectFactory.getBookDAO(); 
			foundBooks = bookDAO.findNews(criteries);
		} catch (DAOException e) {
			throw new ServiceException();
		}
		return foundBooks;
	}
	
	
}
