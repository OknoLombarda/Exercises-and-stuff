package me.movies;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MovieStore {
	private HashMap<String, Movie> movies;
	private HashMap<String, Account> accounts;
	
	public MovieStore() {
		movies = new HashMap<>();
		accounts = new HashMap<>();
	}
	
	public boolean giveMovie(String name, String accountId) {
		if (!movies.containsKey(name) || !accounts.containsKey(accountId))
			return false;
		
		if (!movies.get(name).addHolder(accountId))
			return false;
		
		if (!accounts.get(accountId).addMovie(name))
			return false;
		
		return true;
	}
	
	public boolean receiveMovie(String name, String accountId) {
		if (!movies.containsKey(name) || !accounts.containsKey(accountId))
			return false;
		
		if (!movies.get(name).removeHolder(accountId))
			return false;
		
		accounts.get(accountId).removeMovie(name);
		return true;
	}
	
	public String checkRentals() {
		StringBuilder sb = new StringBuilder();
		int count = 1;
		for (Account a : accounts.values()) {
			if (a.checkOverdue()) {
				sb.append(count).append(". ID:").append(a.toString()).append("\n");
				count++;
				HashMap<String, LocalDate> movies = a.getMovies();
				LocalDate now = LocalDate.now();
				for (Map.Entry<String, LocalDate> entry : movies.entrySet()) {
					if (entry.getValue().isBefore(now))
						sb.append("   ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
				}
			}
		}
		return sb.toString();
	}
	
	public void addMovie(String name, int amount) {
		if (movies.containsKey(name)) {
			Movie movie = movies.get(name);
			movie.add(amount);
			movies.put(name, movie);
		}
		else
			movies.put(name, new Movie(name, amount));
	}
	
	public boolean removeMovie(String name) {
		if (movies.containsKey(name)) {
			movies.remove(name);
			return true;
		}
		return false;
	}
	
	public Account createAccount() {
		Random rand = new Random();
		String id;
		do {
			id = String.valueOf(rand.nextInt());
		} while(accounts.containsKey(id));
		return new Account(id);
	}
	
	public boolean removeAccount(String id) {
		if (accounts.containsKey(id)) {
			accounts.remove(id);
			return true;
		}
		return false;
	}
}
