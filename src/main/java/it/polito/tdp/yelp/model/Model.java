package it.polito.tdp.yelp.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.yelp.db.YelpDao;

public class Model {
	
	private YelpDao dao;
	private Graph<User, DefaultWeightedEdge> grafo;
	private Map<String, User> idMapUsers;
	private List<User> piuSimili;
	private int grado;
	
	
	public Model() {
		this.dao=new YelpDao();
	}
	

	public Set<User> creaGrafo(int anno, int n) {
		this.grafo=new SimpleWeightedGraph <User, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		this.idMapUsers=new HashMap<>();
		List<User> vertici=this.dao.getAllUsers(n);
		for(User u: vertici) {
			this.idMapUsers.put(u.getUserId(), u);
			
		}
		
		Graphs.addAllVertices(this.grafo, vertici);
		
		List<Coppia> archi=this.dao.getAllPairs(anno,n,this.idMapUsers);
		for(Coppia c: archi) {
			DefaultWeightedEdge e=this.grafo.addEdge(c.user1, c.user2);
			this.grafo.setEdgeWeight(e, c.getPeso());
		}
		System.out.println(this.grafo.vertexSet().size());
		return this.grafo.vertexSet();
		
		
	}


	public void getSimile(User user) {
		
		this.piuSimili=new ArrayList<>();
		this.grado=0;
		User piuSimile=null;
		for(User u: Graphs.neighborListOf(this.grafo, user)) {
			if(grado==0) {
				this.piuSimili.add(user);
				grado=(int)this.grafo.getEdgeWeight(this.grafo.getEdge(user, u));
				this.piuSimili.add(u);
			}
			if(grado==this.grafo.getEdgeWeight(this.grafo.getEdge(user, u))) {
				this.piuSimili.add(u);
			}if(grado<this.grafo.getEdgeWeight(this.grafo.getEdge(user, u))) {
				this.piuSimili.clear();
				this.piuSimili.add(u);
				this.grado=(int)this.grafo.getEdgeWeight(this.grafo.getEdge(user, u));
			}
		}	
	}


	public List<User> getPiuSimili() {
		return piuSimili;
	}


	public int getGrado() {
		return grado;
	}


	public void simula(int x1, int x2) {
		
		Simulatore sim=new Simulatore(x1,x2, this.grafo);
		sim.initialize();
		
	}
	
	
}
