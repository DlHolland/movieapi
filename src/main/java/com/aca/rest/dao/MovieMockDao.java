package com.aca.rest.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.aca.rest.model.Movie;

public class MovieMockDao implements MovieDao {

	
	private static int idCounter = 1;
	private static List<Movie> movies = new ArrayList<Movie>();
	
	static {
		
		movies.add(new Movie("No Country for Old Men", "Drama", 2007, idCounter));
		movies.add(new Movie("Ex Machina", "Sci-Fi, Thriller", 2017, getNextKey()));
		movies.add(new Movie("Blade Runner", "Sci-Fi, Thriller", 1986, getNextKey()));
		movies.add(new Movie("Beasts of the Southern Wild", "Drama", 2011, getNextKey()));
		movies.add(new Movie("Take Shelter", "Drama", 2011, getNextKey()));
		movies.add(new Movie("", "Action", 2015, getNextKey()));
		movies.add(new Movie(null, "Action", 2015, getNextKey()));
	}
	
	/* (non-Javadoc)
	 * @see com.aca.rest.dao.MovieDao#getAllMovies()
	 */
	@Override
	public List<Movie> getAllMovies() {
		
		List<Movie> myMovies = new ArrayList<Movie>();
		myMovies.addAll(movies);
		return myMovies;
	}
	
	private static int getNextKey() {
		return idCounter = idCounter + 1;
	}

	/* (non-Javadoc)
	 * @see com.aca.rest.dao.MovieDao#getByGenre(java.lang.String)
	 */
	@Override
	public List<Movie> getByGenre(String genre) {
		List<Movie> myMovies = new ArrayList<Movie>();
		
		for ( Movie movie : MovieMockDao.movies) {
			if (movie.getGenre().equalsIgnoreCase(genre)) {
				myMovies.add(movie);
			}
		}
		return myMovies;
	}
	
	/* (non-Javadoc)
	 * @see com.aca.rest.dao.MovieDao#getByReleaseYear(int)
	 */
	@Override
	public List<Movie> getByReleaseYear(int releaseYear) {
		List<Movie> myMovies = new ArrayList<Movie>();
		
		for (Movie movie : MovieMockDao.movies) {
			if (movie.getReleaseYear() == releaseYear) {
				myMovies.add(movie);
			}
		}
		return myMovies;
	}
	
	/* (non-Javadoc)
	 * @see com.aca.rest.dao.MovieDao#getMovieById(int)
	 */
	@Override
	public List<Movie> getMovieById(int movieId) {
		List<Movie> myMovies = new ArrayList<Movie>();
		
		for (Movie movie : movies) {
			if (movie.getMovieId() == movieId) {
				myMovies.add(movie);
			}
		}
		
		return myMovies;
	}

	/* (non-Javadoc)
	 * @see com.aca.rest.dao.MovieDao#deleteById(com.aca.rest.model.Movie)
	 */
	@Override
	public void deleteById(Movie movieToDelete) {

		
				movies.remove(movieToDelete);
	}

	/* (non-Javadoc)
	 * @see com.aca.rest.dao.MovieDao#add(com.aca.rest.model.Movie)
	 */
	@Override
	public List<Movie> add(Movie newMovie) {

		newMovie.setCreateDate(LocalDateTime.now());
		newMovie.setMovieId(getNextKey());
		movies.add(newMovie);
		
		List<Movie> myMovies = new ArrayList<Movie>();
		myMovies.add(newMovie);
		return myMovies;
	}

	/* (non-Javadoc)
	 * @see com.aca.rest.dao.MovieDao#update(com.aca.rest.model.Movie)
	 */
	@Override
	public List<Movie> update(Movie updateMovie) {

		List<Movie> myMovies = new ArrayList<Movie>();
		
		for (Movie movie : movies) {
			if (movie.getMovieId() == updateMovie.getMovieId()) {
				movie.setGenre(updateMovie.getGenre());
				movie.setReleaseYear(updateMovie.getReleaseYear());
				movie.setTitle(updateMovie.getTitle());
				myMovies.add(movie);
			}
		}
		return myMovies;
	}
}
