package Util;

import java.util.ArrayList;
import java.util.List;

public class Vertice {
	private String nome;
	private List<Aresta> arestas;
	private List<Vertice> vizinhos;
	
	public Vertice(int nome){
		this.nome = nome + "";
		arestas = new ArrayList<Aresta>();
		vizinhos = new ArrayList<Vertice>();
	}
	
	public Vertice(String nome){
		this.nome = nome;
		arestas = new ArrayList<Aresta>();
		vizinhos = new ArrayList<Vertice>();
	}
	
	public void addAresta(Aresta aresta){
		if(!arestas.contains(aresta)) {
			arestas.add(aresta);
			vizinhos.add(aresta.getDestino(this));
		}
	}
	
	public void removerAresta(Aresta aresta){
		if(arestas.contains(aresta)){
			arestas.remove(aresta);
			vizinhos.remove(aresta.getDestino(this));
		}
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj instanceof Vertice) {
			Vertice aux = (Vertice) obj;
			if(aux.getNome().equals(this.nome))
				return true;
		}
		return false;
	}
	
	public Aresta getCaminho(Vertice destino, List<Aresta> caminho){
		int controle = Integer.MAX_VALUE;
		Aresta aux = null;
		for(Aresta a : arestas){
			if(a.getPeso() < controle && !caminho.contains(a) && (a.getDestino().equals(destino) || a.getOrigem().equals(destino))){
				controle = a.getPeso();
				aux = a;
			}
		}
		return aux;
	}
	
	public List<Aresta> getArestas(){
		return arestas;
	}
	
	public List<Vertice> getVizinhos(){
		return vizinhos;
	}
	
	public String getNome(){
		return nome;
	}
	
	public int getQtdArestas(){
		return arestas.size();
	}
	
	public String toString(){
		return "Vertice: " + nome;
	}
}
