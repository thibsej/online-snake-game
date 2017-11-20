package game;

import java.util.HashSet;

import utilities.GameOptions;
import utilities.Snake;

public class G_MoveSnakes implements Runnable {
	private Game thisGame;

	public G_MoveSnakes(Game g) {
		thisGame = g;
	}

	@Override
	public void run() {

		int counter = 0;
		while (!thisGame.manager.gameOver) {
			synchronized (thisGame.manager.thisGame.snakes) {
				for (Snake s : thisGame.snakes.values()) {
					HashSet<Snake> snakes = (HashSet<Snake>) thisGame.snakes
							.values();
					Snake killer = s.isInCollision(snakes);
					if (killer != null) {
						thisGame.snakes.remove(s.id);
						if (s != killer)
							killer.score += 5;
					} else {
						if (s.isInCollision(thisGame.apple.a)) {
							s.grow();
							thisGame.resetApple();
							counter = 0;
						} else
							s.move();
					}

				}
			}
			if (counter % GameOptions.appleLifeTime == 0)
				thisGame.resetApple();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			counter++;

		}

	}
}
