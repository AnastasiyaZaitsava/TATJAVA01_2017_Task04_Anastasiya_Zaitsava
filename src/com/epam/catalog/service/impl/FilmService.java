package com.epam.catalog.service.impl;

import java.util.ArrayList;

import com.epam.catalog.bean.Film;
import com.epam.catalog.bean.SearchCriteries;
import com.epam.catalog.dao.FilmDAO;
import com.epam.catalog.dao.exception.DAOException;
import com.epam.catalog.dao.factory.DAOFactory;
import com.epam.catalog.service.NewsService;
import com.epam.catalog.service.exception.ServiceException;
import com.epam.catalog.util.Util;

public class FilmService implements NewsService{
	
	public void addNews(String[] attributes) throws ServiceException{
		/* 0 - name
		   1 - genres
		   2 - actors
		   3 - news title
		   4 - news text
		   5 - news date */
		Util util = new Util();
		String name = attributes[0];
		ArrayList<String> genres = new ArrayList<String>();
		genres.addAll(util.parseAndConvert(attributes[1], ", "));
		ArrayList<String> actors = new ArrayList<String>();
		actors.addAll(util.parseAndConvert(attributes[2], ", "));
		
		Film film= new Film(name, genres, actors, attributes[3], attributes[4], attributes[5]);
		DAOFactory daoObjectFactory = DAOFactory.getInstance(); 
		FilmDAO filmDAO = daoObjectFactory.getFilmDAO(); 
		try {
			filmDAO.addFilm(film);
		} catch (DAOException e) {
			// TODO logging
			throw new ServiceException();
		}
	}	
	
	public ArrayList<Film> findNews(SearchCriteries criteries) throws ServiceException{
		DAOFactory daoObjectFactory = DAOFactory.getInstance(); 
		FilmDAO filmDAO = daoObjectFactory.getFilmDAO(); 
		ArrayList<Film> foundFilms = new ArrayList<Film>();
		try {
			foundFilms = filmDAO.findNews(criteries);
		} catch (DAOException e) {
			throw new ServiceException();
		}
		return foundFilms;
	}
	
}
