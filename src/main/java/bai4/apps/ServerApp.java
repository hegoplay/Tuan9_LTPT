package bai4.apps;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import all.Constant;

public class ServerApp {
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Constant.getConnection();
		System.out.println("Server is running...");
		ServerSocket serverSocket = new ServerSocket(9909,50);
		boolean isClosed = false;
		while (!isClosed) {
			Socket socket = serverSocket.accept();
			ClientHandler clientHandler = new ClientHandler(socket);
			Thread thread = new Thread(clientHandler);
			thread.start();
			System.out.println("Client is connected...");
			
		}
//		Constant.closeConnection();
	}
}
