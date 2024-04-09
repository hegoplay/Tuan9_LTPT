package bai2.app;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import all.Constant;
import bai2.utils.ClientHandle;

public class ServerApp {
	final static int PORT = 9823;
	private static List<ClientHandle> clients = new CopyOnWriteArrayList<>();
	private static ServerSocket serverSocket;

	public static void main(String[] args) {
		Constant.getConnection();
		// TODO Auto-generated method stub
		ServerApp server = new ServerApp();
		server.startServer();
		System.out.println("Server started.");

		

	}

	private void startServer() {
		try {
			serverSocket = new ServerSocket(PORT, 50);
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						while (!serverSocket.isClosed()) {
							Socket clientSocket = serverSocket.accept();
							System.out.println(clientSocket);
							ClientHandle handler = new ClientHandle(clientSocket);
							clients.add(handler);
							new Thread(handler).start();
						}
					} catch (IOException e) {
						System.out.println("Server stopped.");
					}
				}
			}).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			Constant.closeConnection();
		}
	}

}
