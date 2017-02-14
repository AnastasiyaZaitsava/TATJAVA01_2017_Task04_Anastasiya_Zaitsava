package com.epam.catalog.service.impl;

import java.util.ArrayList;

import com.epam.catalog.bean.Disk;
import com.epam.catalog.bean.SearchCriteries;
import com.epam.catalog.dao.DiskDAO;
import com.epam.catalog.dao.exception.DAOException;
import com.epam.catalog.dao.factory.DAOFactory;
import com.epam.catalog.service.exception.ServiceException;

public class DiskService {

	public void addNews(String[] attributes) throws ServiceException{
		/* 0 - name
		   1 - content
		   2 - producer
		   3 - news title
		   4 - news text
		   5 - news date */
		Disk disk = new Disk(attributes[0], attributes[1], attributes[2], attributes[3], attributes[4], attributes[5]);
		DAOFactory daoObjectFactory = DAOFactory.getInstance(); 
		DiskDAO diskDAO = daoObjectFactory.getDiskDAO(); 
		try {
			diskDAO.addDisk(disk);
		} catch (DAOException e) {
			// TODO logging
			throw new ServiceException();
		}
	}
	public ArrayList<Disk> findNews(SearchCriteries criteries) throws ServiceException{
		DAOFactory daoObjectFactory = DAOFactory.getInstance(); 
		DiskDAO diskDAO = daoObjectFactory.getDiskDAO(); 
		ArrayList<Disk> foundDisks= new ArrayList<Disk>();
		try {
			foundDisks = diskDAO.findNews(criteries);
		} catch (DAOException e) {
			throw new ServiceException();
		}
		return foundDisks;
	}
}
