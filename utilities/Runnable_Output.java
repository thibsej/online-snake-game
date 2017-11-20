package utilities;

import game.G_Manager;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;

public class Runnable_Output extends UDP_Sender implements Runnable {
	private ByteBuffer buffer;
	private ArrayBlockingQueue<Job> communicator;
	String parentPrefix;
	G_Manager manager;

	public Runnable_Output(String address, int port,
			ArrayBlockingQueue<Job> communicator, String prefix,
			G_Manager manager) throws IOException {
		super(address, port);
		buffer = null;
		this.communicator = communicator;
		this.parentPrefix = prefix;
		this.manager = manager;
	}

	@Override
	public void run() {
		System.out.println(parentPrefix + "_Output has been started");
		while (true) {
			try {
				Job j = this.communicator.take();
				switch (j.type()) {
				case SEND_GAME_INFO:
					while (true) {
						buffer = BufferHandler.goPlayThere(j.port(), j.id());
						buffer.flip();
						this.send(buffer);
						System.out
								.println("Runnable_Output sent a " + j.type());
						Thread.sleep(10);
						if (!manager.sendGameInfo)
							break;
					}
					break;

				case SEND_POSITIONS:
					while (!manager.gameOver) {
						synchronized (this.manager.thisGame.snakes) {
							for (Snake s : this.manager.thisGame.snakes
									.values()) {

								System.out.println(s);
							}
						}

						synchronized (this.manager.thisGame.snakes) {
							buffer = Snake
									.encodeAllSnakes(this.manager.thisGame.snakes);
							buffer.put(this.manager.thisGame.apple.toBuffer());
						}
						buffer.flip();
						this.send(buffer);

						System.out.println("All snake positions sent to Client"
								+ j.id());

						/*
						 * for (Snake s : this.manager.thisGame.snakes.values())
						 * { System.out.println(s); }
						 */
						Thread.sleep(100);

					}
					break;
				default:
					System.out.println("ERROR > Runnable_Output Received a "
							+ j.type()
							+ "job when waiting for a SEND_GAME_INFO");
					break;

				}
			} catch (InterruptedException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}