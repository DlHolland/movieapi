package com.aca.rest.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.aca.rest.dao.MovieDao;
import com.aca.rest.dao.MovieDbDao;
import com.aca.rest.dao.MovieMockDao;
import com.aca.rest.model.EmailMessage;
import com.aca.rest.model.Movie;
import com.aca.rest.model.TextSNS;

public class MovieService {

	private MovieDao dao = new MovieDbDao();

	
	public List<Movie> getAllMovies() {
		return dao.getAllMovies();
	}
	
	public List<Movie> getByGenre(String genre) {
		validateGenre(genre);
		return dao.getByGenre(genre);
	}
	
	public List<Movie> getByReleaseYear(int releaseYear) {
		return dao.getByReleaseYear(releaseYear);
	}
	
	public List<Movie> getByMovieId(int movieId) {
		return dao.getMovieById(movieId);
	}
	
	private void validateGenre(String genre) {
		if (genre.equalsIgnoreCase("action") ||
				genre.equalsIgnoreCase("Sci-Fi, Thriller") ||
				genre.equalsIgnoreCase("Drama")) {
		} else {
			ErrorResponse.invalidGenre(genre);
		}	
		//not needed but sometimes used
		return;
	}
	
	public List<Movie> deleteById(int movieId) {
		
		List<Movie>	movies = dao.getMovieById(movieId);
		
		if (movies.size() == 1) {
			dao.deleteById(movies.get(0));
		} else {
			
			ErrorResponse.invalidDelete(movieId);
		}	
		
		return movies;
		
	}

	public List<Movie> add(Movie newMovie) {
		validateReleaseYear(newMovie.getReleaseYear());
		List<Movie> movies = dao.add(newMovie);
		SnsPublish.publishNewMovie(movies.get(0));
		
		return movies;
	}
	
	private void validateReleaseYear(int releaseYear) {
		int minYear = 1890;
		LocalDate localDate = LocalDate.now().plusYears(1);
		int maxYear = localDate.getYear();
		
		if (releaseYear < minYear || releaseYear > maxYear) {
			ErrorResponse.invalidReleaseYear(releaseYear, minYear, maxYear);
		}
	}
	
	private void validateTitle(String title) {
		
		if (title.length() < 1) {
			ErrorResponse.invalidTitle(title);
		}
		
		if(title.length() > 40) {
			ErrorResponse.invalidTitle(title);
		}
	}

	public List<Movie> update(Movie updateMovie) {
		
		boolean validUpdate = false;
		if(updateMovie.getReleaseYear() != 0) {
			validateReleaseYear(updateMovie.getReleaseYear());
			validUpdate = true;
		}
		
		if(updateMovie.getGenre() != null) {
			validateGenre(updateMovie.getGenre());
			validUpdate = true;
		}
		
		if(updateMovie.hasTitle()) {
			validateTitle(updateMovie.getTitle());
			validUpdate = true;
		}
		
		if (!validUpdate) {
			ErrorResponse.invalidUpdate();
		}
		
		return dao.update(updateMovie);
	}
	
	public String sendEmail(EmailMessage emailMessage) {
		String messageId = SnsPublish.sendEmail(emailMessage);		
		return messageId;
	}
	
	public String sendText(TextSNS textSNS) {
		String messageId = SnsPublish.sendText(textSNS);
		return messageId;
	}
	
	
}
