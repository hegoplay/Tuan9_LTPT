package bai6;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class ServerApp {
	static ArrayList<ClientHandler> clients = new ArrayList<>();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerSocket server;
		
		try {
			server = new ServerSocket(8980,50);
			System.out.println("Server is running");
			while (true) {
				ClientHandler ch = new ClientHandler(server.accept());
				clients.add(ch);
				new Thread(ch).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void sendMessage(String msg) {
		// TODO Auto-generated method stub
		for (ClientHandler clientHandler : clients) {
			clientHandler.sendMessage(msg);
		}
	}
}
