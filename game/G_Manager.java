package game;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;

import utilities.Apple;
import utilities.Job;
import utilities.Runnable_Input;
import utilities.Client;
import utilities.Snake;

public class G_Manager implements Runnable {
	public Game thisGame;

	public volatile boolean sendGameInfo;
	public volatile boolean sendCountdown;
	public volatile boolean gameOver;

	private Thread input;
	public int inputPort;
	private ArrayBlockingQueue<Job> in_communicator;

	public HashMap<Client, ArrayBlockingQueue<Job>> out_communicators;

	public G_Manager(Game g, int inputPort, long multicastTimeInterval)
			throws IOException {
		/**
		 * A manager for 1 game: - listening each player moves on port inputPort
		 * (using 1 Thread) - multicasting everyones position every
		 * multicastTimeInterval (ms) on outputPort (using another Thread)
		 */
		System.out.println("G_Manager has been initialized:");

		thisGame = g;
		in_communicator = new ArrayBlockingQueue<Job>(100);
		this.inputPort = inputPort;
		input = new Thread(new Runnable_Input(inputPort, in_communicator, "G"));
		System.out.println("\t> input Thread initialized on port " + inputPort);
		out_communicators = new HashMap<Client, ArrayBlockingQueue<Job>>();
		System.out.println("\t> output Thread initialized");

		System.out.println("\t> END");

		sendGameInfo = true;
		sendCountdown = true;
		gameOver = false;
	}

	@Override
	public void run() {
		System.out.println("G_Manager has been started");
		input.start();

		try {
			sendGameInfo = true;
			while (sendGameInfo) {
				// at this point all players asked to play and were displayed
				// the info
				sendGameInfo = thisGame.hasRoom();
				// Thread.sleep(2000);
			}

			Thread.sleep(1000);
			gameOver = false;

			for (Client c : out_communicators.keySet()) {
				Job j = new Job(Job.Type.SEND_POSITIONS);
				j.id(c.id);
				j.port(this.inputPort);
				out_communicators.get(c).put(j);
				System.out.println("G_Manager sent job \"" + j.type()
						+ "\" to Runnable_Output for Client " + c.id);
			}
			
			Thread moveSnakes;
			moveSnakes = new Thread(new G_MoveSnakes(thisGame));
			moveSnakes.start();
			
			
			byte id=-1;
			while (!gameOver) {
				
				Job j = in_communicator.take();
				
				if(id==j.jobId()){
					continue;
				}
				else System.out.println(j.jobId());
				id=j.jobId();
				synchronized (thisGame.snakes) {
					Snake s = thisGame.snakes.get(j.id()&255);
					System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>"+(s==null));
					s.direction(j.direction());
					
					
				}

				System.out.println("Snake " + j.id() + " changed direction");
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
