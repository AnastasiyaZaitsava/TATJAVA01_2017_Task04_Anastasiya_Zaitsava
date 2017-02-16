package com.epam.catalog.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.epam.catalog.bean.Disk;
import com.epam.catalog.bean.SearchCriteries;
import com.epam.catalog.dao.DiskDAO;
import com.epam.catalog.dao.connection.ConnectionPool;
import com.epam.catalog.dao.connection.ConnectionPoolException;
import com.epam.catalog.dao.exception.DAOException;
import com.epam.catalog.util.Util;


public class SQLDiskDAO implements DiskDAO{
	
	private final static String INSERT_DISK = "INSERT INTO catalogdb.disk (`idDisk`, `DiskName`,"
			+ " `Content`, `Producer`, `NewsTitle`, `NewsText`, `NewsDate`) VALUES(?,?,?,?,?,?,?)";
	@Override
	public void addDisk(Disk disk) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		Util util = new Util();
		Connection con = null;
		try {
			pool.initPoolData();
			con = pool.takeConnection();
			PreparedStatement ps = con.prepareStatement(INSERT_DISK);
			String diskID = util.getNewID(con, "disk");
			ps.setString(1, diskID);
			ps.setString(2, disk.getName());
			ps.setString(3, disk.getContent());
			ps.setString(4, disk.getProducer());
            ps.setString(5, disk.getNews().getTitle());
            ps.setString(6, disk.getNews().getText());
            ps.setString(7, disk.getNews().getDate());
			ps.executeUpdate();
			
		} catch (ConnectionPoolException | SQLException e) {
			// TODO logging
			throw new DAOException();
		} 
	}

	@Override
	public ArrayList<Disk> findNews(SearchCriteries criteries) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		ArrayList<Disk> foundDisks = new ArrayList<Disk>();
		String diskName;
		String content;
		String producer;
		String newsTitle;
		String newsText;
		String newsDate;
		String soughtName;
		if(criteries.getCriteries().containsKey("name")){
			soughtName = "='" + criteries.getCriteries().get("name")[0] + "'";
		} else {
			soughtName = " is not null";
		}
		String soughtContent;
		if(criteries.getCriteries().containsKey("content")){
				soughtContent = "='" + criteries.getCriteries().get("content")[0] + "'";
		} else {
			soughtContent = " is not null";
		}
		String soughtProducer;
		if(criteries.getCriteries().containsKey("producer")){
				soughtProducer = "='" + criteries.getCriteries().get("producer")[0] + "'";
		} else {
			soughtProducer = "is not null";
		}
		
		Connection con = null;
		Statement st = null; 
		ResultSet rs = null; 
		try {
			pool.initPoolData();
			con = pool.takeConnection();
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM Disk WHERE `DiskName` " + soughtName + " AND "
					+ "`Content` " + soughtContent + " AND `Producer` " + soughtProducer);
			while(rs.next()){
				diskName = rs.getString("DiskName");
				content = rs.getString("Content");
				producer = rs.getString("Producer");
				newsTitle = rs.getString("NewsTitle");
				newsText = rs.getString("NewsText");
				newsDate = rs.getString("NewsDate");
				foundDisks.add(new Disk(diskName, content, 
						producer, newsTitle, newsText, newsDate));
			}

		} catch (ConnectionPoolException | SQLException e) {
			// TODO logging
			throw new DAOException();
		}	
		pool.closeConnection(con, st, rs);		
		return foundDisks;
	}

}
