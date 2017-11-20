import java.nio.ByteBuffer;
import java.util.*;

public class Snake {
	//la queue est le premier PointCorps de snake, le dernier point de la liste est la tete
	public LinkedList<PointCorps> snake;
	
	public Snake(LinkedList<PointCorps> s){
		this.snake=s;
	}
	
	/*boolean isCollided(Snake s){
		Point tete=this.snake.getLast().pt;
		for(PointCorps t: s.snake){
			if(this.tete.compareX(t.pt)==0 && (t.dir==2 ||t.dir==3)){
				return true;
			}
			if(this.tete.compareY(t.pt)==0 && (t.dir==0 ||t.dir==1)){
				return true;
			}
		}
		
		return false;
	}*/
	
	void move(){
		if(snake.size()==1){
			//switch()
		}
		
	}

	void changeDirection(int n){
		
	}
	
	Snake fromBufferToSnake(ByteBuffer buffer){
		return null;
	}
	
}
