import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class Serveur_Snake {

	public static void main(String[] args) throws IOException {
		envoie();
		//recevoir();
	}

	private static void recevoir() throws IOException {
		DatagramChannel clientSocket = DatagramChannel.open();
		clientSocket.socket().bind(new InetSocketAddress(5656));
		System.out.println("attente...");
		ByteBuffer FileRequest = ByteBuffer.allocate(1024);
		clientSocket.receive(FileRequest);
		System.out.println("recu!");
	}

	static void envoie() throws IOException {
		DatagramChannel clientSocket = DatagramChannel.open();
		clientSocket.socket().bind(new InetSocketAddress(0));
		InetSocketAddress serveur = new InetSocketAddress("129.104.29.1",
				5656);
		clientSocket.connect(serveur);

		ByteBuffer FileRequest = ByteBuffer.allocate(1024);
		FileRequest.putShort((short) 14);
		FileRequest.put((byte) 1);
		FileRequest.flip();

		clientSocket.send(FileRequest, serveur);
		System.out.println("envoie");
	}

}
