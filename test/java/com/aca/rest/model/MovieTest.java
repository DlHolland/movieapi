package com.aca.rest.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

public class MovieTest {
	
	private String actionGenre = "Action";
	private Movie movie;
	
	@Before
	public void setUp() throws Exception {
		movie = new Movie();
		movie.setGenre(actionGenre);
		movie.setReleaseYear(2000);
	}

	@After
	public void tearDown() throws Exception {
		movie = null;
	}

	@Test
	public void testGetGenre() {
		Assert.assertEquals(actionGenre, movie.getGenre());
	}
	
	@Test
	public void testGetReleaseYear() {
		Assert.assertEquals(2000, movie.getReleaseYear());
	}

}
