package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {

	
	private ImdbDAO dao;
	private Map<Integer, Director> idMap;
	private Graph<Director, DefaultWeightedEdge> grafo;
	private List<Director> best;
	private int bestLunghezza;
	
	public Model() {
		dao = new ImdbDAO();
		idMap = new HashMap<>();
		for(Director d : dao.listAllDirectors()) {
			idMap.put(d.getId(), d);
		}
	}
	
	public void creaGrafo(int anno) {
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(this.grafo, dao.getVertici(anno, idMap));
		
		for(Collegati c : this.dao.getArchi(idMap, anno)) {
			Graphs.addEdgeWithVertices(this.grafo, c.getD1(), c.getD2(), c.getPeso());
		}
	}
	public List<Collegati> getAdiacenti(Director d){
		List<Collegati> result = new ArrayList<>();
		for(DefaultWeightedEdge edge : this.grafo.edgesOf(d)) {
			result.add(new Collegati(d, Graphs.getOppositeVertex(this.grafo, edge, d),(int) this.grafo.getEdgeWeight(edge)));
		}
		Collections.sort(result);
		return result;
		
	}
	
	public List<Director> cercaAffini(int n, Director d){
		bestLunghezza = 0;
		best = new ArrayList<>();
		List<Director> parziale = new ArrayList<>();
		parziale.add(d);
		ricorsione(parziale, n, 0, bestLunghezza);
		return best;
	}
	private void ricorsione(List<Director> parziale, int n, int livello, int lunghezza) {
		
		if(lunghezza > n)
			return;
		
		if(livello == this.grafo.vertexSet().size()-1)
			return;
		
		Director ultimo = parziale.get(parziale.size()-1);
		
		if(parziale.size()>best.size()) {
			best = new ArrayList<>(parziale);
			bestLunghezza = lunghezza;
		}
		
		for(Collegati c : getAdiacenti(ultimo)) {
			if(c.getPeso() <= n && !parziale.contains(c.getD2())) {
				parziale.add(c.getD2());	
				lunghezza+=c.getPeso();
				ricorsione(parziale, n, livello+1, lunghezza);
				
				//backtracking
				parziale.remove(parziale.size()-1);
			}
		}
	}

	
	public int getBestLunghezza() {
		return bestLunghezza;
	}

	public Collection<Director> getDirectors() {
		return this.grafo.vertexSet();
	}

	public int getNVertici() {
		return this.grafo.vertexSet().size();
	}

	public int getNArchi() {
		return this.grafo.edgeSet().size();
	}
	
}
