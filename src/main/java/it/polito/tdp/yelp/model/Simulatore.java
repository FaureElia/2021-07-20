package it.polito.tdp.yelp.model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.yelp.model.Event.eventType;

public class Simulatore {
	
//	costanti
private int x1;
private int x2;
// stato sistema
Graph<User, DefaultWeightedEdge> grafo;
List<User> daIntervistare;
private int giorniPassati;
private int intervistati;
List<User> giaIntervistati;
private Map<Integer, Integer> intervistatoreIntervistati;



//coda
private PriorityQueue <Event> queue;
	
	
	
	
	public Simulatore(int x1,int x2,Graph<User,DefaultWeightedEdge> grafo) {
		this.x1=x1;
		this.x2=x2;
		this.grafo=grafo;
		this.daIntervistare=new ArrayList<>(this.grafo.vertexSet());
		this.giaIntervistati=new ArrayList<>();
		this.giorniPassati=1;
		this.intervistati=0;
		}
	
	public void initialize() {
		this.queue=new PriorityQueue();
		this.intervistatoreIntervistati=new HashMap<>();
		
		for(int i=1; i<x1+1; i++) {
			User intervistato=scegliIntervistatoCasuale();
			this.queue.add(new Event(1,i,eventType.INTERVISTA,intervistato));
			this.giaIntervistati.add(intervistato);
			this.daIntervistare.remove(intervistato);
			this.intervistatoreIntervistati.put(i,0);
		}
		
		run();
		System.out.println("fine:\n");
		System.out.println(this.intervistatoreIntervistati);
		System.out.println("giorni impiegato: "+this.giorniPassati);
		
	}
	
	private User scegliIntervistatoCasuale() {
		return this.daIntervistare.get((int)Math.random()*this.daIntervistare.size());
	}

	private void run() {
		
		while(this.intervistati<this.x2) {
			Event e=this.queue.poll();
			
			gestici(e);
		}
		
	}

	private void gestici(Event e) {
		System.out.println("disponibili: "+this.daIntervistare.size());
		System.out.println("intervistati: "+this.intervistati);
		
		int giorno=e.getGiorno();
		int giornalista=e.getIntervistatore();
		User intervistato=e.getIntervistato();
		eventType tipo=e.getTipo();
		
		System.out.println(tipo);
		if(this.giorniPassati<giorno) {
			this.giorniPassati=giorno;
		}
		
		switch (tipo) {
		case INTERVISTA:
			double random=Math.random();
			if(random<0.6) {
				//successo
				this.intervistati++;
				System.out.println("Successo");
				System.out.println("nuovo intervistato");
				this.intervistatoreIntervistati.put(giornalista,intervistatoreIntervistati.get(giornalista)+1);
				User nuovo=getDaIntervistare(intervistato);
				this.daIntervistare.remove(nuovo);
				this.giaIntervistati.add(nuovo);
				this.queue.add(new Event(giorno+1,giornalista,eventType.INTERVISTA, nuovo));
				
			}
			else if(random>0.6 && random<0.8) {
				//ferie
				System.out.println("Successo ma ferie");
				this.intervistati++;
				User nuovo=getDaIntervistare(intervistato);
				this.daIntervistare.remove(nuovo);
				this.giaIntervistati.add(nuovo);
				this.intervistatoreIntervistati.put(giornalista,intervistatoreIntervistati.get(giornalista)+1);
				this.queue.add(new Event(giorno+1,giornalista,eventType.FERIE, nuovo));
				
				
			}else {
				//failure
				System.out.println("Fallimento");
				this.queue.add(new Event(giorno+1,giornalista,eventType.INTERVISTA, intervistato));
				
			}
			break;
		case FERIE:
			System.out.println("nuovo intervistato");
			this.queue.add(new Event(giorno+1,giornalista,eventType.INTERVISTA, intervistato));
		}
		
		
	}

	private User getDaIntervistare(User user) {
		List<User> piuSimili=new ArrayList<>();
		int grado=0;
		User piuSimile=null;
		for(User u: Graphs.neighborListOf(this.grafo, user)) {
			if(grado==0) {
				piuSimili.add(u);
				grado=(int)this.grafo.getEdgeWeight(this.grafo.getEdge(user, u));
				piuSimili.add(u);
			}
			if(grado==this.grafo.getEdgeWeight(this.grafo.getEdge(user, u))) {
				piuSimili.add(u);
			}if(grado<this.grafo.getEdgeWeight(this.grafo.getEdge(user, u))) {
				piuSimili.clear();
				piuSimili.add(u);
				grado=(int)this.grafo.getEdgeWeight(this.grafo.getEdge(user, u));
			}
		}
		piuSimili.removeAll(this.giaIntervistati);
		
		if(piuSimili.size()==0) {
			return this.scegliIntervistatoCasuale();
		}
		else {
			return piuSimili.get((int)Math.random()*piuSimili.size());
		}
	}
}
