package com.epam.catalog.dao;

import java.util.ArrayList;

import com.epam.catalog.bean.Book;
import com.epam.catalog.bean.SearchCriteries;
import com.epam.catalog.dao.exception.DAOException;

public interface BookDAO {
	
	void addBook(Book book) throws DAOException;
	ArrayList<Book> findNews(SearchCriteries criteries) throws DAOException;
}
