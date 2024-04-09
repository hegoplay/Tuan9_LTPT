package bai3.apps;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import all.Constant;

public class ServerApp {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Server is running...");
		Constant.getConnection();
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(9909);
			boolean stop = false;
			while(!stop) {
				System.out.println("Waiting for client...");
				Socket socket = serverSocket.accept();
				System.out.println("Client: " + socket.getInetAddress()+" connected");
				new Thread(new ClientHandler(socket)).start();
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
		
	}
}
