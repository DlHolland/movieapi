package com.aca.rest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.aca.rest.model.Movie;

public class MovieDbDao implements MovieDao {

	private final static String selectAllMovies = 
			"SELECT MovieId, Title, GenreId, ReleaseYear, CreateDate, UpdateDate " + 
			" FROM movie";
	
	private final static String selectMoviesById = 
			"SELECT MovieId, Title, GenreId, ReleaseYear, CreateDate, UpdateDate " + 
			" FROM movie " +
			" WHERE MovieId = ?";
	
	private final static String selectMoviesByGenre = 
			"SELECT MovieId, Title, GenreId, ReleaseYear, CreateDate, UpdateDate " + 
					" FROM movie " +
					" WHERE genreId = ?";
	
	private final static String selectMoviesByReleaseYear = 
			"SELECT MovieId, Title, GenreId, ReleaseYear, CreateDate, UpdateDate " + 
					" FROM movie " +
					" WHERE ReleaseYear = ?";
	
	private final static String deleteMoviesByMovieId = 
			"DELETE FROM MOVIE WHERE movieId = ? ";
	
	private final static String addMovies = 
			"INSERT INTO movie (Title, ReleaseYear, GenreId) VALUES " +
			" (?, ?, ?) ";

		

	
	@Override
	public List<Movie> getAllMovies() {

		List<Movie> movies = new ArrayList<Movie>();
		
		Connection conn = MariaDbUtil.getConnection();
		
		Statement statement = null;
		ResultSet rs = null;
		
		try {
			statement = conn.createStatement();
			rs = statement.executeQuery(selectAllMovies);
			while(rs.next()) {
				Movie movie = makeMovie(rs);
				movies.add(movie);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (null != conn) {
				try {
					rs.close();
					statement.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
				
		return movies;
	}
	
	

	private Movie makeMovie(ResultSet rs) throws SQLException {

		Movie movie = new Movie();
		movie.setMovieId(rs.getInt("MovieId"));
		movie.setTitle(rs.getString("Title"));
		movie.setGenre(rs.getString("GenreId"));
		movie.setReleaseYear(rs.getInt("ReleaseYear"));
		movie.setCreateDate(rs.getObject("CreateDate", LocalDateTime.class));
		return movie;
	}



	@Override
	public List<Movie> getByGenre(String genre) {
		List<Movie> movies = new ArrayList<Movie>();
		
		Connection conn = MariaDbUtil.getConnection();
		
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		
		try {
			preparedStatement = conn.prepareStatement(selectMoviesByGenre);
			preparedStatement.setString(1, genre);
			rs = preparedStatement.executeQuery();
			while(rs.next()) {
				Movie movie = makeMovie(rs);
				movies.add(movie);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (null != conn) {
				try {
					rs.close();
					preparedStatement.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
				
		return movies;
	}

	@Override
	public List<Movie> getByReleaseYear(int releaseYear) {
List<Movie> movies = new ArrayList<Movie>();
		
		Connection conn = MariaDbUtil.getConnection();
		
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		
		try {
			preparedStatement = conn.prepareStatement(selectMoviesByReleaseYear);
			preparedStatement.setInt(1, releaseYear);
			rs = preparedStatement.executeQuery();
			while(rs.next()) {
				Movie movie = makeMovie(rs);
				movies.add(movie);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (null != conn) {
				try {
					rs.close();
					preparedStatement.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
				
		return movies;
	}

	@Override
	public List<Movie> getMovieById(int id) {
		List<Movie> movies = new ArrayList<Movie>();
		
		Connection conn = MariaDbUtil.getConnection();
		
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		
		try {
			preparedStatement = conn.prepareStatement(selectMoviesById);
			preparedStatement.setInt(1, id);
			rs = preparedStatement.executeQuery();
			while(rs.next()) {
				Movie movie = makeMovie(rs);
				movies.add(movie);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (null != conn) {
				try {
					rs.close();
					preparedStatement.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
				
		return movies;
		
	}

	@Override
	public void deleteById(Movie movieToDelete) {
		
		Connection conn = MariaDbUtil.getConnection();
		
		PreparedStatement preparedStatement = null;
		
		try {
			preparedStatement = conn.prepareStatement(deleteMoviesByMovieId);
			preparedStatement.setInt(1, movieToDelete.getMovieId());
			int rowCount = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (null != conn) {
				try {
					preparedStatement.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}

	@Override
	public List<Movie> add(Movie newMovie) {
		List<Movie> movies = new ArrayList<Movie>();
		Connection conn = MariaDbUtil.getConnection();
		
		PreparedStatement preparedStatement = null;
		
		try {
			preparedStatement = conn.prepareStatement(addMovies);
			preparedStatement.setString(1, newMovie.getTitle());
			preparedStatement.setInt(2, newMovie.getReleaseYear());
			preparedStatement.setString(3, newMovie.getGenre());
			int rowCount = preparedStatement.executeUpdate();
			System.out.println("rows inserted: " + rowCount);
			
			newMovie.setMovieId(getLastKey(conn));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (null != conn) {
				try {
					preparedStatement.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
		movies.add(newMovie);
		return movies;
	}
	
	private Integer getLastKey(Connection conn) throws SQLException {
		Integer key = 0;
		Statement statement = conn.createStatement();
		ResultSet result = statement.executeQuery(" SELECT LAST_INSERT_ID()");
		
		while(result.next()) {
			key = result.getInt("LAST_INSERT_ID()");
		}
		return key;
	}

	@Override
	public List<Movie> update(Movie updateMovie) {
		String sql1 = "UPDATE MOVIE ";
		String sql2 = " SET ";
		String sql3 = " WHERE MovieId = ? ";
		
		if (updateMovie.hasGenre()) {
			sql2 = sql2 + " GenreId = ? ";
		}
		
		if (updateMovie.hasReleaseYear()) {
			if (updateMovie.hasGenre()) {
				sql2 = sql2 + ", ";
			}
			sql2 = sql2 + " ReleaseYear = ? ";
		}
		
		if (updateMovie.hasTitle()) {
			if (updateMovie.hasGenre() || updateMovie.hasReleaseYear()) {
				sql2 = sql2 + ", ";
			}
			sql2 = sql2 + " Title = ? ";
		}
		
		String finalSql = sql1 + sql2 + sql3;
		
		
		List<Movie> movies = new ArrayList<Movie>();
		
		Connection conn = MariaDbUtil.getConnection();
		
		PreparedStatement preparedStatement = null;
		
		int questions = 1;
		try {
			preparedStatement = conn.prepareStatement(finalSql);
			if (updateMovie.hasGenre()) {
				preparedStatement.setString(1, updateMovie.getGenre());
				questions++;
			}
			if (updateMovie.hasReleaseYear()) {
				if (updateMovie.hasGenre()) {
					preparedStatement.setInt(2, updateMovie.getReleaseYear());
					questions++;

				} else {
					preparedStatement.setString(1, updateMovie.getGenre());
					questions++;

				}
			}
			if ( updateMovie.hasTitle()) {
				if (updateMovie.hasGenre()) {
					if (updateMovie.hasReleaseYear()) {
						preparedStatement.setString(3, updateMovie.getTitle());
						questions++;
					} else {
						preparedStatement.setInt(2, updateMovie.getReleaseYear());
						questions++;

					}
				} else {
					if (updateMovie.hasReleaseYear()) {
						preparedStatement.setInt(2, updateMovie.getReleaseYear());
						questions++;

					}
					preparedStatement.setString(1, updateMovie.getGenre());
					questions++;

				}
			}
			preparedStatement.setInt(questions, updateMovie.getMovieId());
			int rowCount = preparedStatement.executeUpdate();
			System.out.println("rows updated: " + rowCount);

			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (null != conn) {
				try {
					preparedStatement.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
		
		movies.add(updateMovie);
		return movies;
	}

}
