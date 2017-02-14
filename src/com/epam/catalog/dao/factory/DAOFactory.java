package com.epam.catalog.dao.factory;

import com.epam.catalog.dao.BookDAO;
import com.epam.catalog.dao.DiskDAO;
import com.epam.catalog.dao.FilmDAO;
import com.epam.catalog.dao.impl.SQLBookDAO;
import com.epam.catalog.dao.impl.SQLDiskDAO;
import com.epam.catalog.dao.impl.SQLFilmDAO;

public final class DAOFactory {

	private static final DAOFactory instance = new DAOFactory();
	
	private final BookDAO sqlBookImpl = new SQLBookDAO();
	private final DiskDAO sqlDiskImpl = new SQLDiskDAO();
	private final FilmDAO sqlFilmImpl = new SQLFilmDAO();
	
	private DAOFactory(){}
	
	public static DAOFactory getInstance(){
		return instance;
	}
	
	public BookDAO getBookDAO(){
		return sqlBookImpl;
	}
	
	public DiskDAO getDiskDAO(){
		return sqlDiskImpl;
	}
	
	public FilmDAO getFilmDAO(){
		return sqlFilmImpl;
	}
}
