package me.movies;

import java.util.HashSet;

public class Movie implements Comparable<Movie> {
	private String name;
	private int totalAmount;
	private int inStock;
	private HashSet<String> holderId;
	
	public Movie(String name, int amount) {
		this.name = name;
		totalAmount = amount;
		inStock = amount;
		holderId = new HashSet<String>();
	}
	
	public void add(int amount) {
		totalAmount += amount;
		inStock += amount;
	}
	
	public void remove(int amount) {
		totalAmount -= amount;
		inStock -= amount;
	}
	
	public boolean addHolder(String id) {
		if (inStock > 0) {
			holderId.add(id);
			inStock--;
			return true;
		}
		return false;
	}
	
	public boolean removeHolder(String id) {
		if (holderId.contains(id)) {
			holderId.remove(id);
			inStock++;
			return true;
		}
		return false;
	}
	
	public String getName() {
		return name;
	}
	
	public int compareTo(Movie other) {
		return name.compareTo(other.getName());
	}
	
	public boolean equals(Object other) {
		if (this == other)
			return true;
		
		if (other == null)
			return false;
		
		if (getClass() != other.getClass())
			return false;
		
		Movie otherMovie = (Movie) other;
		
		return name.equals(otherMovie.getName());
	}
	
	public String toString() {
		return name + ": " + inStock + " / " + totalAmount;
	}
	
	public int hashCode() {
		return name.hashCode() * 5;
	}
}
