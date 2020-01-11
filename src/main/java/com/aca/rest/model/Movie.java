package com.aca.rest.model;

import java.time.LocalDateTime;

import javax.json.bind.annotation.JsonbProperty;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Movie {

	@JsonbProperty(nillable = true)
	private String title;
	private String genre;
	private int releaseYear;
	private int movieId;
	private LocalDateTime createDate;


	public Movie() {}
	
	public Movie(String title, String genre, int releaseYear, int movieId) {
		this.title = title;
		this.genre = genre;
		this.releaseYear = releaseYear;
		this.movieId = movieId;
		this.setCreateDate(LocalDateTime.now());
	}
	
	public String toString() {
		
		return "title: " + title + ", release year" + releaseYear + ", genre: " + genre + ", movie id: " + movieId;
	}
	
	

	public LocalDateTime getCreateDate() {
		return createDate;
	}



	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}



	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}

	public int getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(int releaseYear) {
		this.releaseYear = releaseYear;
	}

	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}
	
	public boolean hasTitle() {
		if (this.getTitle() != null) {
			return true;
		}
		return false;
	}
	
	public boolean hasGenre() {
		if (this.getGenre() != null) {
			return true;
		}
		return false;
	}
	
	public boolean hasReleaseYear() {
		if (this.getReleaseYear() != 0) {
			return true;
		}
		return false;
	}

}
