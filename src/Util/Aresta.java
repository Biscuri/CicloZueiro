package Util;

public class Aresta {
	private Vertice origem, destino;
	private int peso;
	
	public Aresta(Vertice o, Vertice d){
		this.origem = o;
		this.destino = d;
		peso = 0;
	}
	
	public Aresta(Vertice o, Vertice d, int peso){
		this.origem = o;
		this.destino = d;
		this.peso = peso;
		
		o.addAresta(this);
		d.addAresta(this);
	}
	
	public boolean equals(Object obj){
		if(obj instanceof Aresta){
			Aresta aux = (Aresta) obj;
			if(aux.getPeso() == this.peso){
				if(aux.getOrigem().equals(this.origem) && aux.getDestino().equals(this.destino))
					return true;
				else if(aux.getOrigem().equals(this.destino) && aux.getDestino().equals(this.origem))
					return true;
			}
		}
		return false;
	}
	
	public void setPeso(int peso){
		this.peso = peso;
	}
	
	public int getPeso(){
		return peso;
	}
	
	public Vertice getOrigem(){
		return origem;
	}
	
	public Vertice getDestino(){
		return destino;
	}
	
	public Vertice getDestino(Vertice referencial){
		if(referencial.equals(this.origem))
			return destino;
		else
			return origem;
	}
	
	public Vertice getOrigem(Vertice referencial){
		if(referencial.equals(this.destino))
			return origem;
		else
			return destino;
	}
	
	public String toString(){
		return "("+ origem.getNome() + ") --" + peso + "-- (" + destino.getNome() + ")";
	}

}
