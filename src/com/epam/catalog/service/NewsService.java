package com.epam.catalog.service;

import java.util.ArrayList;

import com.epam.catalog.bean.SearchCriteries;
import com.epam.catalog.service.exception.ServiceException;

public interface NewsService {

	void addNews(String[] attributes) throws ServiceException;
	ArrayList<? extends Object> findNews(SearchCriteries criteries) throws ServiceException;
}
