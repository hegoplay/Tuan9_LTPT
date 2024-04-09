package bai1.connect;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import bai1.service.CalculateService;
import bai1.service.CalculateServiceImpl;
import bai1.utils.ClientHandler;

public class ServerApp {
	public final static int PORT = 11111;
	

	public static void main(String[] args) {
		try {
			startServer();
		} catch (RemoteException | AlreadyBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void startServer() throws RemoteException, AlreadyBoundException {
		// TODO Auto-generated method stub
		
		CalculateServiceImpl impl = new CalculateServiceImpl();
		CalculateService skeleton = (CalculateService) UnicastRemoteObject.exportObject(impl,0);
	
		Registry registry = LocateRegistry.createRegistry(PORT);
		registry.bind("CalculateService", skeleton);
		
		System.out.println("Server is listening on port " + PORT);
	}
	
	
	
}
