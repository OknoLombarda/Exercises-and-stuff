package me.inventory;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Inventory {
	private HashMap<String, Product> storage;
	
	public Inventory() {
		storage = new HashMap<>();
	}
	
	public Inventory(List<Product> products) {
		this();
		addAll(products);
	}
	
	public void add(Product product) {
		storage.put(String.valueOf(product.getId()), product);
	}
	
	public void addAll(List<Product> products) {
		storage.putAll(products.stream().collect(Collectors.toMap(p -> String.valueOf(p.getId()), p -> p)));
	}
	
	public boolean remove(int id) {
		String key = String.valueOf(id);
		
		if (storage.containsKey(key)) {
			storage.remove(key);
			return true;
		}
		else
			return false;
	}
	
	public boolean remove(Product product) {
		return remove(product.getId());
	}
	
	public void removeAll(List<Product> products) {
		for (Product p : products)
			storage.remove(String.valueOf(p.getId()));
	}
	
	public double sumUp() {
		double sum = 0;
		
		for (Product p : storage.values())
			sum += p.getPrice();
		
		return sum;
	}
}
