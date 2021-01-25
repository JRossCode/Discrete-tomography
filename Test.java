
import java.awt.Canvas;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;


public class Test {
	
	static int N;
	static int M;
	static Grille grd;
	
	public static void main(String[] args) {
		Grille gr ;
		int [][] seqLigne;
		int [][] seqCol;
				
		File f = new File("./src/15.txt");
		String st;
		ArrayList<ArrayList<Integer>>L=new ArrayList<>();
		ArrayList<ArrayList<Integer>>C=new ArrayList<>();
		
		////Lecture du fichier, initialisation de la grille et des séquences
		try {
			BufferedReader get = new BufferedReader(new FileReader(f));
			
			while((st=get.readLine())!=null) {//determine le nombre de ligne 				
				if(st.contains("#")) {
					break;
				}
				Scanner scan = new Scanner(st);
				ArrayList<Integer>tmp=new ArrayList<>();
				tmp.add(0);
				while (scan.hasNextInt()) {
					tmp.add(scan.nextInt());
				}
				L.add(tmp);
				scan.close();
			}
			
			while((st=get.readLine())!=null) {//determine le nombre de ligne 
				Scanner scan = new Scanner(st);
				ArrayList<Integer>tmp=new ArrayList<>();
				tmp.add(0);
				while (scan.hasNextInt()) {
					tmp.add(scan.nextInt());
				}
				C.add(tmp);
				scan.close();

			}
			get.close();
			
			
			///Converti l'arraylist en tableau pour les séquences
			System.out.println("Liste séquence lignes");
			seqLigne = new int [L.size()][];
			for (int i = 0; i < seqLigne.length; i++) {
				String aff="";
				int n=L.get(i).size();
				seqLigne[i]=new int [n];
				for (int j = 0; j < n; j++) {
					int elem=L.get(i).get(j);
					aff+=elem;
					seqLigne[i][j]=elem;
				}
				System.out.println(aff+"\n");
			}
			
			System.out.println("Liste séquence colonnes");
			seqCol = new int [C.size()][];
			for (int i = 0; i < seqCol.length; i++) {
				String aff="";
				int n=C.get(i).size();
				seqCol[i]=new int [n];
				for (int j = 0; j < n; j++) {
					int elem=C.get(i).get(j);
					aff+=elem;
					seqCol[i][j]=elem;
				}
				System.out.println(aff+"\n");
			}			
			
			//initialisation de la grille
			N=L.size();
			M=C.size();
			gr =new Grille(L.size(), C.size(),seqLigne,seqCol);
			grd=gr;
			System.out.println("Grille initialisée");
			//gr.display();
			System.out.println("\n");
			//gr.grille[21][14]=1;
			int res= gr.coloration();
			
			System.out.println(res);
			//gr.display();
			
			JFrame frame = new JFrame("My Drawing");
	        Canvas canvas = new Drawing(N,M,gr);
	        canvas.setSize(N*10, M*10);
	        frame.add(canvas);
	        frame.pack();
	        frame.setVisible(true);
			
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
