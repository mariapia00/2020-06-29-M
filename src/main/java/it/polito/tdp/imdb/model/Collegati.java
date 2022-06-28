package it.polito.tdp.imdb.model;

public class Collegati implements Comparable<Collegati>{

	private Director d1;
	private Director d2;
	private int peso;
	public Collegati(Director d1, Director d2, int peso) {
		super();
		this.d1 = d1;
		this.d2 = d2;
		this.peso = peso;
	}
	public Director getD1() {
		return d1;
	}
	public Director getD2() {
		return d2;
	}
	public int getPeso() {
		return peso;
	}
	@Override
	public int compareTo(Collegati o) {
		return o.getPeso()-this.peso;
	}
	@Override
	public String toString() {
		return "Collegati [d2="+ d2.getId()+"  -  " + d2.getFirstName() +" "+ d2.getLastName()+"  -  "+ peso+" ]\n\n";
	}
	
}
