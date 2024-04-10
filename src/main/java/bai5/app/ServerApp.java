package bai5.app;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerApp {
	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(9999);
			System.out.println("Server is running...");
			while (true) {
				new Thread(new ClientHandler(server.accept())).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
