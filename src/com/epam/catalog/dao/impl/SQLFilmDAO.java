package com.epam.catalog.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.epam.catalog.bean.Film;
import com.epam.catalog.bean.SearchCriteries;
import com.epam.catalog.dao.FilmDAO;
import com.epam.catalog.dao.connection.ConnectionPool;
import com.epam.catalog.dao.connection.ConnectionPoolException;
import com.epam.catalog.dao.exception.DAOException;
import com.epam.catalog.util.Util;

public class SQLFilmDAO implements FilmDAO{

	@Override
	public void addFilm(Film film) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		Util util = new Util();
		try {
			pool.initPoolData();
			Connection con = pool.takeConnection();
			Statement st = null; 
			st = con.createStatement();
			int filmID = util.getNewID(st, "film");
			int affRows = st.executeUpdate("INSERT INTO catalogdb.film (`idFilm`, `FilmName`,"
					+ " `NewsTitle`, `NewsText`, `NewsDate`) "
					+ "VALUES ('" + filmID  + "', '" + film.getName()+ "', '" 
					+ film.getNews().getTitle() + "','" + film.getNews().getText() 
					+ "','" + film.getNews().getDate() +"')");
			int genreID;
			for(String genre: film.getGenres()){
				genreID = util.getIDifExists(st, "genres", "GenreName", genre);
				if (genreID == 0){
					genreID = util.getNewID(st, "genres");
					affRows = st.executeUpdate("INSERT INTO catalogdb.genres (`idGenre`, "
							+ "`GenreName`) VALUES ('" + genreID + "', '" + genre +"')");
				}
				
				affRows = st.executeUpdate("INSERT INTO catalogdb.filmgenre (`idFilmGenre`,"
						+ "`idFilm`,`idGenre`) VALUES('" + util.getNewID(st, "filmgenre") + "','"
						+ filmID + "', '" + genreID + "')");
			}
			int actorID;
			for(String actor: film.getActors()){
				actorID = util.getIDifExists(st, "actors", "ActorsName", actor);
				if (actorID == 0){
					actorID = util.getNewID(st, "actors");
					affRows = st.executeUpdate("INSERT INTO catalogdb.actors (`idActor`, "
							+ "`ActorsName`) VALUES ('" + actorID + "', '" + actor +"')");
				}
				
				affRows = st.executeUpdate("INSERT INTO catalogdb.actfilm (`idActFilm`,"
						+ "`idFilm`,`idActor`) VALUES('" + util.getNewID(st, "actfilm") + "','"
						+ filmID + "', '" + actorID + "')");
			}
			
			
		} catch (ConnectionPoolException | SQLException e) {
			// TODO logging
			throw new DAOException();
		}
		
	}

	@Override
	public ArrayList<Film> findNews(SearchCriteries criteries) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		ArrayList<Film> foundFilms = new ArrayList<Film>();
		String filmName;
		ArrayList<String> genres = new ArrayList<String>();
		ArrayList<String> actors = new ArrayList<String>();
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
		String soughtActors = "";
		if(criteries.getCriteries().containsKey("actors")){
			criteriesLength = criteries.getCriteries().get("actors").length;
			String[] actorsInCr = criteries.getCriteries().get("actors");
			for(int i=0; i<criteriesLength; i++){
				if (i==0){
					soughtActors = "='" + actorsInCr[i] + "'";
				}
				else
					soughtActors += " OR actors.ActorsName ='" + actorsInCr[i] + "' ";
			}
		} else {
			soughtActors = "is not null";
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
			rs = st.executeQuery("select * from film where FilmName " + soughtName + " AND idFilm IN "
					+ "(select idFilm from actfilm join actors on actors.idActor = actfilm.idActor "
					+ "where actors.ActorsName " + soughtActors + " and idFilm IN (select idFilm "
							+ "from filmgenre join genres on genres.idGenre = filmgenre.idGenre "
							+ "where genres.GenreName " + soughtGenres +"))");
			int filmID;
			while(rs.next()){
				filmName = rs.getString("FilmName");
				filmID = rs.getInt("idFilm");
				newsTitle = rs.getString("NewsTitle");
				newsText = rs.getString("NewsText");
				newsDate = rs.getString("NewsDate");
				rs1=st1.executeQuery("select ActorsName from actors "
						+ "join actfilm on actfilm.idActor = actors.idActor "
						+ "where idFilm = '" + filmID + "'");
				while(rs1.next()){
					actors.add(rs1.getString("ActorsName"));
				}
				rs1 = st1.executeQuery("select GenreName from genres "
						+ "join filmgenre on filmgenre.idGenre = genres.idGenre "
						+ "where idFilm = '" + filmID + "'");
				while(rs1.next()){
					genres.add(rs1.getString("GenreName"));
				}
				foundFilms.add(new Film(filmName,  genres, actors, newsTitle, newsText, newsDate)); 
			}

		} catch (ConnectionPoolException | SQLException e) {
			// TODO logging
			throw new DAOException();
		} finally{	
			pool.closeConnection(con, st, rs);
		}
		return foundFilms;

	}

}
