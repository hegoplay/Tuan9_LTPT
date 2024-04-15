package bai1.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import jakarta.persistence.EntityManager;

public class CalculateService extends UnicastRemoteObject implements CalculateDAO{

	private static final long serialVersionUID = -3269081498278213065L;
	private EntityManager entityManager;
	
	public CalculateService(EntityManager entityManager) throws RemoteException {
		this.setEntityManager(entityManager);
	}

	@Override
	public int add(int a, int b) throws Exception {
		// TODO Auto-generated method stub
		return a+b;
	}

	@Override
	public int sub(int a, int b) throws Exception {
		// TODO Auto-generated method stub
		return a-b;
	}

	@Override
	public int mul(int a, int b) throws Exception {
		// TODO Auto-generated method stub
		return a*b;
	}

	@Override
	public int div(int a, int b) throws Exception {
		// TODO Auto-generated method stub
		if (b == 0) {
			throw new Exception("Can't divide by zero.");
		}
		
		return a/b;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	
	
}
