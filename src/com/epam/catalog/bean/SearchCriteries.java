package com.epam.catalog.bean;

import java.util.HashMap;

public class SearchCriteries {
	private HashMap<String, String[]> criteries;
	
	public SearchCriteries(){}
	
	public SearchCriteries(String[] categories, String[] parameters){
		criteries = new HashMap<String, String[]>();
		for(int i = 0; i < categories.length; i++){
			criteries.put(categories[i], parameters[i].split(", "));
		}
	}
	
	public HashMap<String, String[]> getCriteries(){
		return criteries;
	}
}
