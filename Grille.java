import java.util.ArrayList;


/*Le projet etant réalisé en Java on va donc travailler avec la classe Grille qui contiendra la "grille" a
 * proprement parler (tableau 2D), toutes les fonctions de test/coloration s'appliquant sur celle-ci et toutes
 * les structures et variables nécessaire a ses fonctions*/

public class Grille {
	
								/*-------------Variables et structures---------------*/
	
	//Dimensions de la grille//
	 int n;
	 int m;
	
	//Tableau 2D représentant la grille//
	 int [][] grille; 
	 
	/*Deux tableau 2D contenant les séquences pour chaque ligne, 
	 * chaque ligne/colone du tableau repésente la ligne/colone de la grille avec la séquence correspondante*/
	 int [][] SeqLig;
	 int [][] SeqCol;

	 
	 							/*--------Constructeur (permet d'initialiser la grille----------*/

	public Grille(int n,int m,int [][] SeqLig,int [][] SeqCol) {
		this.n=n;
		this.m=m;
		this.SeqLig=SeqLig;
		this.SeqCol=SeqCol;
		grille = new int[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				grille[i][j]=0;
			}
			
		}
	}
	

								/*-------------Algorithmes de test pour les séquences T()-----------*/

	///Description des paramètres de T////
	
	/* PARAMETRES DE BASES:
	 * 
	 * i :l'indice de la ligne ou l'on veux placer notre séquence 
	 * j :l'indice de la case ou l'on commence a tester si la séquence sera plaçable
	 * l :l'indice de la partie en cour de la séquence
	 * s :la séquence a placer
	 *  
	 * PARAMETRES SUPPLEMENTAIRES (principe de l'algo):
	 * 
	 * idl :l'indice de la dernière séquence a essayer de placer (certaines séquences ne pourrons etre placer qu'a des endroit précis)
	 * jl :l'indice de la borne supérieur du sous-tableau qui peux encore être tester (le tableau sans les zones déja prise par des séquence d'indice supérieur a idl)
	 * */
	
	public boolean Tl(int i, int j,int l,int idl,int jl,int[] s) {
		
		
	/*-------------Condition l==0 : On a traiter toute les partie de la séquence-----------*/
		
		if(l==0) { //On a traiter toutes les parties de la séquence 
			if(checkNl(i,0,j)) { //On verifie si il reste des cases noires dans la partie du tableau que l'on a pas parcouru
				if(idl==0) {//On vérifie si il reste des parties de la séquence pouvant encore être tester a sur d'autre intervals
					
					//Dans ce cas, toutes les séquence sont "fixée", il ya donc une séquence déja présente ne permettant pas d'acceuillir notre séquence  
					return false;
				}
				else {
					//Sinon on rapelle notre fonction sur la partie du tableau encore testable avec les séquence encore disponibles (d'ou l'utilité d'idl et jl)
					return Tl(i,jl-1,idl,idl,jl-1,s);
				}
			}
			else {
				//Si il n'y a pas de case noir dans la partie du tableau non parcourue alors on peux bien placer la séquence
				return true;
			}
		}
		
	/*-------------Condition j<s[l]-1: La séquence ne rentre pas dans l'interval restant-----------*/

		else if(j<s[l]-1) { 
			return false;
		}

	/*-------------Condition j==s[l]-1: La séquence fait la taille de l'interval restant-----------*/

		else if(j==s[l]-1) {
			//Si la séquence fait la taille du reste du tableau, on test si il n'y a pas de blanc dans cette zone et si il sagit de la dernière séquence
			return ((!checkBl(i,0,j))&&(l==1));
		}
		
	/*-------------Condition j>s[l]-1: La séquence est inférieure a l'interval restant-----------*/
		
		else {
			
		/*-------Si on commence sur une case blanche---*/
			if(grille[i][j]==-1) { 
				//Si la case est blanche, on recommance une case avant 
				return Tl(i,j-1,l,idl,jl-1,s);
			}
			
		/*-----Si on commence sur une case noire-------*/
			else if(grille[i][j]==1) {//Si la case est noire
				if(checkBl(i, j-s[l]+1, j-1)||(grille[i][j-s[l]]==1)) {
					/*Si une case blanche est présente dans l'intervale de nécessaire pour s[l], 
					 * ou qu'une case noir est présente après la séquence, cette séquence ne peux être placer avec cette case noire*/
					return false;
				}
				else {
					/*Sinon on apelle la fonction sur la séquence suivante en vérouillant la 
					 * séquence que l'on vient de placer et la partie du tableau quelle occupe*/
				 return Tl(i,j-s[l]-1,l-1,idl-1,jl-s[l]-1,s);
				}
			}
			
		/*-----Si on commence sur une case vide (cas le plus complexe)-------*/
			else {
				int k;
				
				for (k=j-1; k > j-s[l]; k--) {//On parcour l'interval de la séquence en vérifiant su des cases blache sont présentes
					if(grille[i][k]==-1) {
						break;
					}
				}
				if(k==j-s[l]) {// Si on a pu caser notre séquence sans blanche
					//System.out.println("nombre de case placable sur démarage vide");
					if(grille[i][k]==1) {//Si une case noire est présente juste après la séquence, on recommance une case avant j
						//System.out.println("case noir après séquence sur démarage vide");
						return Tl(i,j-1,l,idl,jl-1,s);
					}
					else {
						if(Tl(i,k-1,l-1,idl-1,k-1,s)) {//Si on peux placer le reste des entier de la séquence correctement 
							return true;
						}
						else {
							return Tl(i,j-1,l,idl,jl-1,s); //Sinon on recommence en décalant de 1 avant j
						}
					}
				}
				
				else {
					if(checkNl(i, k, j-1)) {//Si une case noir est présente avant la case blanche qui coupe notre séquence on ne peux placer la séquence avec cette case noire 
						return false;
					}
					else {
						return Tl(i,k-1,l,idl,k-1,s);
					}
				}
			}
		}
	}
	
	public boolean Tc(int i, int j,int l,int idl,int jl,int[] s) {
		
		if(l==0) { 
			if(checkNc(i,0,j)) { 
				if(idl==0) {
					return false;
				}
				else {
					return Tc(i,jl-1,idl,idl,jl-1,s);
				}
			}
			else {
				return true;
			}
		}
		
		else if(j<s[l]-1) {
			return false;
		}
		
		else if(j==s[l]-1) {
			
			return ((!checkBc(i,0,j))&&(l==1));
		}
		
		else {
			if(grille[j][i]==-1) { 
				return Tc(i,j-1,l,idl,jl-1,s);
			}
			
			else if(grille[j][i]==1) {
				if(checkBc(i, j-s[l]+1, j-1)||(grille[j-s[l]][i]==1)) {
					return false;
				}
				else {
				 return Tc(i,j-s[l]-1,l-1,idl-1,jl-s[l]-1,s);
				}
			}
			
			else {
				int k;
				
				for (k=j-1; k > j-s[l]; k--) {
					if(grille[k][i]==-1) {
						break;
					}
				}
				if(k==j-s[l]) {
					if(grille[k][i]==1) {
						return Tc(i,j-1,l,idl,jl-1,s);
					}
					else {
						if(Tc(i,k-1,l-1,idl-1,k-1,s)) {
							return true;
						}
						else {
							return Tc(i,j-1,l,idl,jl-1,s); 
						}
					}
				}
				
				else {
					if(checkNc(i, k, j-1)) {
						return false;
					}
					else {
						return Tc(i,k-1,l,idl,k-1,s);
					}
				}
			}
		}
	}
	
	
									/*-------------Fonctions suppélmentaires (nécessaire pour T)---------------*/

	public boolean checkNl(int i,int j1,int j2) {		
		for (int k = j1; k <= j2; k++) {
			if(grille[i][k]==1) {
				return true;
			}
		}
		return false;
	}
	
	public boolean checkNc(int i,int j1,int j2) {		
		for (int k = j1; k <= j2; k++) {
			if(grille[k][i]==1) {
				return true;
			}
		}
		return false;
	}
	
	public boolean checkBl(int i,int j1,int j2) {		
		for (int k = j1; k <= j2; k++) {
			if(grille[i][k]==-1) {
				return true;
			}
		}
		return false;
	}
	
	public boolean checkBc(int i,int j1,int j2) {		
		for (int k = j1; k <= j2; k++) {
			if(grille[k][i]==-1) {
				return true;
			}
		}
		return false;
	}
	
	public boolean coloreLig(int i,int [] sl,int j,ArrayList<Integer>nouveaux) {
		boolean okN;
		boolean okB;
		boolean ok;
		
		if(j>m-1) { //On a parcourue toute les case de la ligne sans impossibilités 
			return true;
		}
		if(grille[i][j]==0) {
			
			grille[i][j]=-1;
			okB=Tl(i, m-1, sl.length-1, sl.length-1, m-1, sl);
			grille[i][j]=1;
			okN=Tl(i, m-1, sl.length-1, sl.length-1, m-1, sl);
			
			if((!okN)&&(!okB)) {//Impossible dans les deux cas, on renvoie faux 
				//System.out.println("Impossible");
				return false;
			}
			else if(okN&&!okB) {//Bon pour case noire
				//System.out.println("Ok noir");
				nouveaux.add(j);
				return coloreLig(i, sl, j+1,nouveaux);
			}
			else if(!okN&&okB){//Bon pour case blanche 
				//System.out.println("Ok blanche");
				nouveaux.add(j);
				grille[i][j]=-1;
				return coloreLig(i, sl,j+1,nouveaux);
			}
			else {//Si les deux sont bon on remet la case vide 
				//System.out.println("Ok deux");
				grille[i][j]=0;
				return coloreLig(i, sl,j+1,nouveaux);
			}
		}
		else {
			ok=Tl(i, m-1, sl.length-1, sl.length-1, m-1, sl);
			if(ok) {
				return coloreLig(i, sl,j+1,nouveaux);
			}
			else {
				return false;
			}
		}
		
		
		
	}
	
	public boolean coloreCol(int i,int [] s,int j,ArrayList<Integer>nouveaux) {
		boolean okN;
		boolean okB;
		boolean ok;
		
		if(j>n-1) { //On a parcourue toute les case de la ligne sans impossibilités 
			return true;
		}
		//System.out.println("Coloration case ("+j+","+i+")");
		if (grille[j][i]==0) {
			
			grille[j][i]=-1;
			okB=Tc(i, n-1, s.length-1, s.length-1, n-1, s);
			grille[j][i]=1;
			okN=Tc(i, n-1, s.length-1, s.length-1, n-1, s);
			
			if((!okN)&&(!okB)) {//Impossible dans les deux cas, on renvoie faux 
				return false;
			}
			else if(okN&&!okB) {//Bon pour case noire
				//System.out.println("Ok noir");
				nouveaux.add(j);
				return coloreCol(i, s, j+1,nouveaux);
			}
			else if(!okN&&okB){//Bon pour case blanche 
				//System.out.println("Ok blanc");
				grille[j][i]=-1;
				nouveaux.add(j);
				return coloreCol(i, s, j+1,nouveaux);
			}
			else {//Si les deux sont bon on remet la case vide 
				//System.out.println("Ok deux");
				grille[j][i]=0;
				return coloreCol(i, s, j+1,nouveaux);
			}
		}
		else {
			ok=Tc(i, n-1, s.length-1, s.length-1, n-1, s);
			if(ok) {
				return coloreCol(i, s, j+1,nouveaux);
			}
			else {
				return false;
			}
		}
		
	}

	public int coloration() {//1 vrais -1 faux 0 ne sais pas 
		
		int [][]grilleCop=this.grille;
		
		ArrayList<Integer> LignesAVoir = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			LignesAVoir.add(i);
		}
		
		ArrayList<Integer> ColonnesAVoir = new ArrayList<>();
		for (int i = 0; i < m; i++) {
			ColonnesAVoir.add(i);
		}

		while (!LignesAVoir.isEmpty()||!ColonnesAVoir.isEmpty()) {
			
			ArrayList<Integer> elemSupL = new ArrayList<>();
			ArrayList<Integer> elemSupC = new ArrayList<>();//Va nous servir a enlever les lignes qu'on a parcouru de LigneAVoir
			
			for (Integer i : LignesAVoir) {
				ArrayList<Integer> nouveaux = new ArrayList<>();
				boolean ok;
				ok=coloreLig(i, SeqLig[i],0,nouveaux);
				if(!ok) {
					return -1;
				}			
				//union de nouveaux et colonnesAvoir
				for (Integer j : nouveaux) {
					if(!ColonnesAVoir.contains(j)){
						ColonnesAVoir.add(j);
					}
				}
				
				elemSupL.add(i);
			
			}
			LignesAVoir.removeAll(elemSupL);
			
			for (Integer j : ColonnesAVoir) {
				
				ArrayList<Integer> nouveaux = new ArrayList<>();
				boolean ok;
				ok=coloreCol(j, SeqCol[j],0,nouveaux);
				if(!ok) {
					return -1;
				}
				
				//union de nouveaux et colonnesAvoir
				for (Integer i : nouveaux) {
					if(!LignesAVoir.contains(i)){
						LignesAVoir.add(i);
					}
				}
				
				elemSupC.add(j);
			}
			ColonnesAVoir.removeAll(elemSupC);
			//this.display();
		}
		for (int i = 0; i < n; i++) {//On vérifie si il y a précense d'une case vide 
			for (int j = 0; j < m; j++) {
				if(grilleCop[i][j]==0) {
					return 0;
				}
			}
		}
		return 1;
	}
	
	
	public String toString() {
		String st="";
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				st = st +grille[i][j]+ " ";
			}
			st=st+"\n";
		}
		return st;
	}
	
	public void display() {
		System.out.println(this.toString());
	}

}
