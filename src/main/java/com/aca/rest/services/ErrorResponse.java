package com.aca.rest.services;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import com.aca.rest.model.MovieError;

public class ErrorResponse {

	
	public static void invalidDelete(int movieId) {
		
		MovieError error = new MovieError();
		error.setId(102);
		error.setMessage("invalid delete request!");
		
		throwError(error);

	}

	public static void invalidGenre(String genre) {

		MovieError error = new MovieError();
		error.setId(101);
		error.setMessage("invalid value of genre '" + genre + "', valid values are 'action', 'Sci-fi', 'thriller', or 'drama'");
		
		throwError(error);

	
	}

	public static void invalidReleaseYear(int releaseYear, int minYear, int maxYear) {

		MovieError error = new MovieError();
		error.setId(103);
		error.setMessage("invalid value of release year '" + releaseYear + "', valid values are between"
				+ minYear + " and " + maxYear);
		throwError(error);

		
	}
	
	private static void throwError(MovieError error) {
		Response response = Response.status(400)
				.entity(error)
				.build();
		throw new WebApplicationException(response);
	}

	public static void invalidTitle(String title) {

		MovieError error = new MovieError();
		error.setId(104);
		error.setMessage("invalid value of title '" + title + "', cannot enter a value less than 1 character or greater than 40 characters");
		
		throwError(error);
		
	}

	public static void invalidUpdate() {

		MovieError error = new MovieError();
		error.setId(105);
		error.setMessage("invalid update request");
		
		throwError(error);

		
	}
	
}
