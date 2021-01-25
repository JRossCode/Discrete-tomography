
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;

public class Drawing extends Canvas {
	
	
	int n;
	int m;
	Grille gr;
	
	public Drawing(int n,int m,Grille gr) {
		this.n=n;
		this.m=m;
		this.gr=gr;
	}
        

    public void paint(Graphics g) {
    	Color c= new Color(0,0,0);
    	Color err= new Color(255,0,0);
    	
    	for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (gr.grille[i][j]==1) {
					g.setColor(c);
					g.fillRect(j*10,i*10,10,10);
				}
				else if (gr.grille[i][j]==-1) {
					g.setColor(c);
					g.drawRect(j*10,i*10,10,10);
				}
				else {
					g.setColor(err);
					g.fillRect(j*10,i*10,10,10);

				}
			}
		}
    }
}