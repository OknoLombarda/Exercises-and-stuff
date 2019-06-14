package me.inventory;

public class Product implements Comparable<Product> {
	private int id;
	private double price;
	private int quantity;
	
	public Product(int id, double price, int quantity) {
		this.id = id;
		this.price = price;
		this.quantity = quantity;
	}
	
	public boolean equals(Object other) {
		if (this == other)
			return true;
		
		if (other == null)
			return false;
		
		if (getClass() != other.getClass())
			return false;
		
		Product otherProduct = (Product) other;
		
		return id == otherProduct.getId();
	}
	
	public int hashCode() {
		return id * 27 + (int) price * 13 + quantity * 11;
	}
	
	public String toString() {
		return "ID=" + id + ", PRICE=" + price + ", QUANTITY=" + quantity;
	}
	
	public int compareTo(Product otherProduct) {
		double otherPrice = otherProduct.getPrice();
		if (price > otherPrice)
			return 1;
		else if (price < otherPrice)
			return -1;
		else
			return 0;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int newId) {
		id = newId;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double newPrice) {
		price = newPrice;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int newQuantity) {
		quantity = newQuantity;
	}
}
