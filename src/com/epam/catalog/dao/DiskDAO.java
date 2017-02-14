package com.epam.catalog.dao;

import java.util.ArrayList;

import com.epam.catalog.bean.Disk;
import com.epam.catalog.bean.SearchCriteries;
import com.epam.catalog.dao.exception.DAOException;

public interface DiskDAO {

	void addDisk(Disk disk) throws DAOException;
	ArrayList<Disk> findNews(SearchCriteries criteries) throws DAOException;
}
