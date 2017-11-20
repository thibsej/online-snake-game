
public class Point {

	public int x;
	public int y;
	
	public Point(Byte x, Byte y){
		this.x=x&255;
		this.y=y&255;
	}
	
	public int compareX(Point p){
		return this.x-p.x;
	}
	
	public int compareY(Point p){
		return this.y-p.y;
	}
	
	public boolean equals(Point p){
		return(this.compareX(p)==0 && this.compareY(p)==0); 
	}
	
	public Point translate(int a,int b){
		//translate selon x et y d'un nombre de case donné
		//Pour rester en byte, on raisonne modulo 256
		// pour reculer prendre comme valeur (127-a,127-b)
		this.x=(this.x+a)%127;
		this.y=(this.y+b)%127;
		return this;
	}
}
