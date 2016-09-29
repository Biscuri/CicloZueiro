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
	private static String MENSAGEM_ERRO = "Este grafo não possui circuito";
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
	
	public List<Aresta> iniciarAnalise(Vertice origem, List<Vertice> visitados, List<Aresta> caminho){
		if(origem.getQtdArestas() <= 1){
			System.out.println(MENSAGEM_ERRO);
			return null;
		}
		//marco o vertice como visitado
		if(!visitados.contains(origem))
			visitados.add(origem);
		//se já foi visitado nem faz nada
		else 
			return null;
		
		//variaveis de controle
		int controle = Integer.MAX_VALUE, anteriorControle;
		List<Aresta> auxCaminho;
		//verificando se ainda possui iterações por fazer
		if(visitados.size() < vertices.size()){
			//verificando cada aresta
			for(Aresta a : origem.getArestas()){
				//verifico se o peso é menor e se o destino não já foi visitado
				if(a.getPeso() < controle && !visitados.contains(a.getDestino(origem))){
					caminho.add(a);
					anteriorControle = controle;
					controle = a.getPeso();
					System.out.println(caminho);
					auxCaminho = iniciarAnalise(a.getDestino(origem), visitados, caminho);
					//retornando o caminho que foi encontrado se tudo ocorreu bem
					if(auxCaminho != null)
						return auxCaminho;
					//se não encontrou caminho valido reseta
					else {
						//resetando o ultimo estado
						controle = anteriorControle;
						caminho.remove(a);
					}
				}
			}
		}
		//se não possui mais iterações verifica
		else if(origem.getVizinhos().contains(inicial)){
			caminho.add(origem.getCaminho(inicial, caminho));
			System.out.println("Possui ciclo: " + caminho);
			return caminho;
		}
		
		//se não encontrou o caminho até aqui, remove dos visitados e retorna null
		System.out.println("Não Possui ciclo por aqui " + caminho);
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
