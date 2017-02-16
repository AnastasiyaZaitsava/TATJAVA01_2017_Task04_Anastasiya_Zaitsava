package com.epam.catalog.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.epam.catalog.bean.Book;
import com.epam.catalog.bean.SearchCriteries;
import com.epam.catalog.dao.BookDAO;
import com.epam.catalog.dao.connection.ConnectionPool;
import com.epam.catalog.dao.connection.ConnectionPoolException;
import com.epam.catalog.dao.exception.DAOException;
import com.epam.catalog.util.Util;

public class SQLBookDAO implements BookDAO {

	private final static String INSERT_BOOK = "INSERT INTO catalogdb.book (`idBook`, `BookName`,"
			+ " `NewsTitle`, `NewsText`, `NewsDate`) VALUES(?,?,?,?,?)";
	private final static String INSERT_GENRE ="INSERT INTO genres(`idGenre`, `GenreName`) VALUES (?,?)";
	private final static String INSERT_AUTHOR ="INSERT INTO author(`idAuthor`, `AuthorsName`) VALUES (?,?)";
	private final static String INSERT_BOOK_GENRE ="INSERT INTO bookgenre(`idBookGenre`,"
				+ "`idBook`,`idGenre`) VALUES(?,?,?)";
	private final static String INSERT_BOOK_AUTHOR ="INSERT INTO authbook (`idAuthBook`,"
				+ "`idBook`,`idAuthor`) VALUES(?,?,?)";
	
	public void addBook(Book book) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		Util util = new Util();
		try {
			pool.initPoolData();
			Connection con = pool.takeConnection();
			Statement st = null; 
			st = con.createStatement();
			PreparedStatement ps = con.prepareStatement(INSERT_BOOK);
			String bookID = util.getNewID(con, "book");
			ps.setString(1, bookID);
			ps.setString(2, book.getName());
            ps.setString(3, book.getNews().getTitle());
            ps.setString(4, book.getNews().getText());
            ps.setString(5, book.getNews().getDate());
			ps.executeUpdate();
		
			String genreID;
			for(String genre: book.getGenres()){
				genreID = util.getIDifExists(st, "genres", "GenreName", genre);
				if (genreID.equals("0")){
					genreID = util.getNewID(con, "genres");
					ps = con.prepareStatement(INSERT_GENRE);
					ps.setString(1, genreID);
					ps.setString(2, genre);
					ps.executeUpdate();
				}
				
				ps = con.prepareStatement(INSERT_BOOK_GENRE);
				ps.setString(1, util.getNewID(con, "bookgenre"));
				ps.setString(2, bookID);
				ps.setString(3, genreID);
				ps.executeUpdate();
			}
			String authorID;
			for(String author: book.getAuthors()){
				authorID = util.getIDifExists(st, "author", "AuthorsName", author);
				if (authorID.equals("0")){
					authorID = util.getNewID(con, "author");
					ps = con.prepareStatement(INSERT_AUTHOR);
					ps.setString(1, authorID);
					ps.setString(2, author);
					ps.executeUpdate();
				}
				
				ps = con.prepareStatement(INSERT_BOOK_AUTHOR);
				ps.setString(1, util.getNewID(con, "authbook"));
				ps.setString(2, bookID);
				ps.setString(3, authorID);
				ps.executeUpdate();
			}
			
			
		} catch (ConnectionPoolException | SQLException e) {
			// TODO logging
			e.printStackTrace();
			throw new DAOException();
		}
		
	}

	public ArrayList<Book> findNews(SearchCriteries criteries) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		Util util = new Util();
		ArrayList<Book> foundBooks = new ArrayList<Book>();
		String bookName;
		ArrayList<String> genres = new ArrayList<String>();
		ArrayList<String> authors = new ArrayList<String>();
		String newsTitle;
		String newsText;
		String newsDate;
		String soughtName;
		if(criteries.getCriteries().containsKey("name")){
			soughtName = "='" + criteries.getCriteries().get("name")[0] + "' ";
		} else {
			soughtName = " is not null";
		}
		String soughtGenres = "";
		int criteriesLength;
		if(criteries.getCriteries().containsKey("genres")){
			criteriesLength = criteries.getCriteries().get("genres").length;
			String[] genresInCr = criteries.getCriteries().get("genres");
			for(int i=0; i<criteriesLength; i++){
				if (i==0){
					soughtGenres = "='" + genresInCr[i] + "'";
				}
				else
					soughtGenres += " OR genres.GenresName ='" + genresInCr[i] + "' ";
			}
				
		} else {
			soughtGenres = " is not null";
		}
		String soughtAuthors = "";
		if(criteries.getCriteries().containsKey("authors")){
			criteriesLength = criteries.getCriteries().get("authors").length;
			String[] authorsInCr = criteries.getCriteries().get("authors");
			for(int i=0; i<criteriesLength; i++){
				if (i==0){
					soughtAuthors = "='" + authorsInCr[i] + "'";
				}
				else
					soughtAuthors += " OR authors.AuthorsName ='" + authorsInCr[i] + "' ";
			}
		} else {
			soughtAuthors = "is not null";
		}
		
		Statement st = null; 
		ResultSet rs = null; 
		Connection con = null;
		try {
			pool.initPoolData();
			con = pool.takeConnection();
			Statement st1 = con.createStatement();
			ResultSet rs1 = null;
			st = con.createStatement();
			rs = st.executeQuery("select * from book where BookName " + soughtName + " AND idBook IN "
					+ "(select idBook from authbook join author on author.idAuthor = authbook.idAuthor "
					+ "where author.AuthorsName " + soughtAuthors + " and idBook IN (select idBook "
							+ "from bookgenre join genres on genres.idGenre = bookgenre.idGenre "
							+ "where genres.GenreName " + soughtGenres +"))");
			int bookID;
			while(rs.next()){
				bookName = rs.getString("BookName");
				bookID = rs.getInt("idBook");
				newsTitle = rs.getString("NewsTitle");
				newsText = rs.getString("NewsText");
				newsDate = rs.getString("NewsDate");
				rs1=st1.executeQuery("select AuthorsName from author "
						+ "join authbook on authbook.idAuthor = author.idAuthor "
						+ "where idBook = '" + bookID + "'");
				while(rs1.next()){
					authors.add(rs1.getString("AuthorsName"));
				}
				rs1 = st1.executeQuery("select GenreName from genres "
						+ "join bookgenre on bookgenre.idGenre = genres.idGenre "
						+ "where idBook = '" + bookID + "'");
				while(rs1.next()){
					genres.add(rs1.getString("GenreName"));
				}
				foundBooks.add(new Book(bookName, authors, genres, newsTitle, newsText, newsDate)); 
			}

		} catch (ConnectionPoolException | SQLException e) {
			// TODO logging
			throw new DAOException();
		} finally{	
			pool.closeConnection(con, st, rs);
		}
		return foundBooks;
	}
	
	
	
}
