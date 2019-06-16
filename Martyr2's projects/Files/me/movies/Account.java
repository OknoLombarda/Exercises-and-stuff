package me.movies;

import java.time.LocalDate;
import java.util.HashMap;

public class Account implements Comparable<Account> {
	private String id;
	private HashMap<String, LocalDate> moviesOnHand;
	
	public Account(String id) {
		this.id = id;
		moviesOnHand = new HashMap<>();
	}
	
	public boolean addMovie(String movieName) {
		LocalDate now = LocalDate.now();
		
		for (LocalDate ld : moviesOnHand.values())
			if (ld.isBefore(now))
				return false;
		
		moviesOnHand.put(movieName, now.plusWeeks(1));
		return true;
	}
	
	public boolean removeMovie(String movieName) {
		if (moviesOnHand.containsKey(movieName)) {
			moviesOnHand.remove(movieName);
			return true;
		}
		return false;
	}
	
	public HashMap<String, LocalDate> getMovies() {
		return moviesOnHand;
	}
	
	public boolean checkOverdue() {
		LocalDate now = LocalDate.now();
		for (LocalDate ld : moviesOnHand.values())
			if (ld.isBefore(now))
				return true;
		
		return false;
	}
	
	public int compareTo(Account other) {
		return id.compareTo(other.toString());
	}
	
	public boolean equals(Object other) {
		if (this == other)
			return true;
		
		if (other == null)
			return false;
		
		if (getClass() != other.getClass())
			return false;
		
		Account otherAccount = (Account) other;
		
		return id.equals(otherAccount.toString());
	}
	
	public String toString() {
		return id;
	}
	
	public int hashCode() {
		return id.hashCode() * 12;
	}
}
