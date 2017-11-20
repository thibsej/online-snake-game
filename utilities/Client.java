package utilities;

import java.util.HashMap;

public class Client {
	private static HashMap<Integer, Client> clients;
	private static int maxid=0;
	
	public int id;
	public String address;
	public int listeningPort;
	
	public Client(String address, int listeningPort){
		/**
		 * Defined by a physical address and a listeningPort
		 */
		this.id=++maxid;
		this.address=address;
		this.listeningPort=listeningPort;
	}
	
	public static void register(Client c){
		/**
		 * registers Client c
		 * returns the previous registered Client for this id or null
		 * 
		 */
		if(clients.containsKey(c.id)) clients.remove(c.id);
		clients.put(c.id, c);
	}
	
	public static Client remove(int id){
		return clients.remove(id);
	}
	
	@Override
	public String toString(){
		return "Client "+this.id+" listening on "+this.address+":"+this.listeningPort;
	}
	
	public static String print(){
		String res="";
		for(int i=0;i<maxid;i++){
			
		}
		return res;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
