package it.polito.tdp.yelp.model;

import java.util.Objects;

public class Event implements Comparable<Event> {
	
	enum eventType{
		INTERVISTA,FERIE;
	}
	
	
	private int giorno;
	private int intervistatore;
	private eventType tipo;
	private User intervistato;
	
	
	public Event(int giorno, int intervistatore, eventType tipo, User intervistato) {
		super();
		this.giorno = giorno;
		this.intervistatore = intervistatore;
		this.tipo = tipo;
		this.intervistato = intervistato;
	}
	
	
	public User getIntervistato() {
		return intervistato;
	}


	public void setIntervistato(User intervistato) {
		this.intervistato = intervistato;
	}


	public eventType getTipo() {
		return tipo;
	}
	public void setTipo(eventType tipo) {
		this.tipo = tipo;
	}
	public int getGiorno() {
		return giorno;
	}
	public void setGiorno(int giorno) {
		this.giorno = giorno;
	}
	public int getIntervistatore() {
		return intervistatore;
	}
	public void setIntervistatore(int intervistatore) {
		this.intervistatore = intervistatore;
	}
	@Override
	public int hashCode() {
		return Objects.hash(intervistatore);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		return intervistatore == other.intervistatore;
	}
	@Override
	public int compareTo(Event o) {
		return this.giorno-o.giorno;
	}
	
	
	
	

}
