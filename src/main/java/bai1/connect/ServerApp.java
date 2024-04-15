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

import all.Constant;
import bai1.service.CalculateDAO;
import bai1.service.CalculateService;
import bai1.utils.ServicesList;

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
		
	
		Registry registry = LocateRegistry.createRegistry(PORT);
		Constant.getConnection();
		
		CalculateDAO calculateDAO = (CalculateDAO) new CalculateService(Constant.manager);
		registry.bind(ServicesList.CALCULATE.getServiceName(), calculateDAO);
		
		System.out.println("Server is listening on port " + PORT);
	}
	
	
	
}
