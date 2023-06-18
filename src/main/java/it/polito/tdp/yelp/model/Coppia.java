package it.polito.tdp.yelp.model;

public class Coppia {
	User user1;
	User user2;
	int peso;
	public User getUser1() {
		return user1;
	}
	public void setUser1(User user1) {
		this.user1 = user1;
	}
	public User getUser2() {
		return user2;
	}
	public void setUser2(User user2) {
		this.user2 = user2;
	}
	public int getPeso() {
		return peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	public Coppia(User user1, User user2, int peso) {
		super();
		this.user1 = user1;
		this.user2 = user2;
		this.peso = peso;
	}
	
	

}
