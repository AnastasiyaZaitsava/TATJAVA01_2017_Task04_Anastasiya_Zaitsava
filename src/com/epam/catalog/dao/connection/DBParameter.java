package com.epam.catalog.dao.connection;

public final class DBParameter {
	
	private DBParameter(){}

	public static final String DB_DRIVER = "com.mysql.jdbc.Driver"; 
	public static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/catalogdb"; 
	public static final String DB_USER = "root";
	public static final String DB_PASSWORD = "root";
	public static final String DB_POLL_SIZE = "5";
}