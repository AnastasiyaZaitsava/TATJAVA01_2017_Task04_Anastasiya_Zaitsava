package com.epam.catalog.dao;

import java.util.ArrayList;

import com.epam.catalog.bean.Film;
import com.epam.catalog.bean.SearchCriteries;
import com.epam.catalog.dao.exception.DAOException;

public interface FilmDAO {

	void addFilm(Film film) throws DAOException;
	ArrayList<Film> findNews(SearchCriteries criteries) throws DAOException;
}
