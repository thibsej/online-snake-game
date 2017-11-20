package utilities;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.LinkedList;

public class BufferHandler {
	
	public static ByteBuffer goPlayThere(int gamePort, byte clientId){
		ByteBuffer buffer=ByteBuffer.allocate(4);
		buffer.put((byte)0);
		buffer.putShort((short)gamePort);
		buffer.put(clientId);
		return buffer;
	}

	public static ByteBuffer helloClient(String serverName, int connectionPort) {
		/**
		 * Creates a buffer ServerName and connectionPort
		 */
		ByteBuffer attenteJoueur = ByteBuffer.allocate(1024);
		byte taille = (byte) serverName.length();
		attenteJoueur.put(taille);
		for (int i = 0; i < taille; i++)
			attenteJoueur.put((byte) serverName.charAt(i));
		attenteJoueur.putShort((short) connectionPort);

		attenteJoueur.flip();
		return attenteJoueur;
	}
/*
	public static ByteBuffer updatePositions(HashMap<Byte, Snake> snakes) {
		/**
		 * Creates a buffer containing all snake positions using the Client
		 * instances given
		 *//*
		// TODO
		ByteBuffer buf = ByteBuffer.allocate(1024);
		byte size = (byte) snakes.size();
		for (Snake s : snakes.values()) {
			encodeOneSnake(s, buf);
		}
		buf.put(size);
		return buf;
	}*/

	static void encodeOneSnake(Snake s, ByteBuffer buf) {
		/**
		 * We describe a snake more compactly : we only provide the points where
		 * the direction changes, and the length where it goes straight
		 */
		// we assume snakes's length is at least 2 in order to avoid NULL
		// Pointer Exception
		// we implement the new representation of snake before pushing it into
		// the buffer
		LinkedList<Point> obj= new LinkedList<Point>();
		for(Point p: s.points){
			obj.addFirst(p);
		}

		LinkedList<Byte> length = new LinkedList<>();
		LinkedList<Byte> direction = new LinkedList<>();
		Point queue = obj.poll();
		Point successeur = obj.poll();
		Point tmp = obj.poll();
		byte l = 1;
		byte dir = findDirection(queue, successeur);
		direction.push(dir);

		while (tmp != null) {
			if (findDirection(successeur, tmp) == dir) {
				l++;
				
			} else {
				length.addLast(l);
				l = 1;
				dir = findDirection(successeur, tmp);
				direction.addLast(dir);

			}
			successeur = tmp;
			tmp = obj.poll();
		}
		length.addLast(l);
		l = (byte) length.size();

		// Then we prepare the buffer
		buf.put(s.id);
		buf.put((byte) queue.x);
		buf.put((byte) queue.y);
		buf.put(l);
		while (!length.isEmpty()) {
			buf.put(direction.poll());
			buf.put(length.poll());
		}

		return;

	}

	static byte findDirection(Point a, Point b) {// Attention, la direction va de A
		// vers B
		if ((b.x)% GameOptions.gridSize == (a.x + 1)
				% GameOptions.gridSize) {
			return 2;
		}
		if ((b.x)%GameOptions.gridSize == (a.x-1)
				% GameOptions.gridSize) {
			return 0;
		}
		if ((b.y)% GameOptions.gridSize == (a.y + 1)
				% GameOptions.gridSize){
			return 1;
		}
		if ((b.y)% GameOptions.gridSize == (a.y - 1)
				% GameOptions.gridSize) {
			return 3;
		}
		System.out.println("ERROR : POINTS ARE NOT CLOSE TO EACH OTHER");
		return 4;

	}

}
