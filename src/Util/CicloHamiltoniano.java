package Util;

import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class CicloHamiltoniano {
	private static String MENSAGEM_ERRO = "Este grafo n�o possui circuito";
	private List<Vertice> vertices;
	private Vertice inicial;
	private List<List<Aresta>> caminhos;
	
	public CicloHamiltoniano (){
		vertices = new ArrayList<Vertice>();
		caminhos = new ArrayList<List<Aresta>>();
	}
	
	public void iniciar(){
		inicial = vertices.get(0);
		if(iniciarAnalise(inicial, new ArrayList<Vertice>(), new ArrayList<Aresta>()) == null)
			System.out.println(MENSAGEM_ERRO);
	}
	
	public void montarAresta(String a, String b, String c){
		int vertice1 = Integer.parseInt(a);
		int vertice2 = Integer.parseInt(b);
		int peso = Integer.parseInt(c);
		montarAresta(vertice1, vertice2, peso);
	}
	
	public void montarAresta(int vertice1, int vertice2, int peso){
		Vertice v1 = new Vertice(vertice1);
		Vertice v2 = new Vertice(vertice2);
		
		if(vertices.contains(v1))
			v1 = vertices.get(vertices.indexOf(v1));
		else
			vertices.add(v1);

		if(vertices.contains(v2))
			v2 = vertices.get(vertices.indexOf(v2));
		else
			vertices.add(v2);		

		new Aresta(v1, v2, peso);	
	}
	
	/*
	 * Ordem do algoritmo:
	 * O(V!)
	 */
	
	public List<Aresta> iniciarAnalise(Vertice origem, List<Vertice> visitados, List<Aresta> caminho){
		if(origem.getQtdArestas() <= 1){
			System.out.println(MENSAGEM_ERRO);
			return null;
		}
		if(!visitados.contains(origem)) //S� verifica v�rtices n�o visitados, n�mero de v�rtices diminui a cada recurs�o
			visitados.add(origem);
		else 
			return null;
		
		int controle = Integer.MAX_VALUE, anteriorControle;
		List<Aresta> auxCaminho;
		if(visitados.size() < vertices.size()){ 
			/*
			 * O n�mero de arestas analisadas diminui a cada recurs�o pois arestas ligando a v�rtices visitados n�o s�o analisadas.
			 */
			for(Aresta a : origem.getArestas()){ 
				if(a.getPeso() < controle && !visitados.contains(a.getDestino(origem))){
					caminho.add(a);
					anteriorControle = controle;
					controle = a.getPeso();
					System.out.println(caminho);
					/*
					 * Recurs�o:
					 * Chama para cada v�rtice n�o-visitado at� este ponto,
					 * e a cada recurs�o analisa todas as arestas ligando a 
					 * v�rtices ainda n�o visitados. Para cada an�lise de aresta,
					 * chama novamente para os v�rtices n�o visitados, ou seja:
					 * V . V-1 . V-2 ... 1
					 * Logo o c�digo roda V! vezes.
					 */
					auxCaminho = iniciarAnalise(a.getDestino(origem), visitados, caminho);
					if(auxCaminho != null)
						return auxCaminho;
					else {
						controle = anteriorControle;
						caminho.remove(a);
					}
				}
			}
		} else if(origem.getVizinhos().contains(inicial)){
			caminho.add(origem.getCaminho(inicial, caminho));
			System.out.println("Possui ciclo: " + caminho);
			return caminho;
		}
		
		System.out.println("N�o Possui ciclo por aqui " + caminho);
		visitados.remove(origem);
		return null;
	}
		
	public void lerArquivo(String arquivo) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(new File(arquivo)));
		int v = Integer.parseInt(br.readLine());
		int a = Integer.parseInt(br.readLine());
		if(a < v) {
			br.close();
			System.out.println(MENSAGEM_ERRO);
			System.exit(-1);
		}
		else {
			for(String linha = br.readLine(); linha != null; linha = br.readLine()){
				String [] aux = linha.split(" ");
				montarAresta(aux[0], aux[1], aux[2]);
			}
			System.out.println("Leitura do arquivo: [OK]");
		}
		br.close();
	}
	
	public static void main(String [] a){
		CicloHamiltoniano g = new CicloHamiltoniano();
		try {
			g.lerArquivo(JOptionPane.showInputDialog(null, "arquivo do grafo"));
			g.iniciar();
		} 
		catch (HeadlessException | IOException e) {
			System.out.println("Deu algo muito errado na leitura do arquivo");
		}
	}

}
