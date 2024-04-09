package bai3.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ConnectDB {
	public EntityManagerFactory entityFactory;
	public EntityManager manager;
	public void getConnection() {
		entityFactory = Persistence.createEntityManagerFactory("JPA_ORM_Student_MSSQL");
		manager = entityFactory.createEntityManager();
	}
	public void closeConnection() {
		manager.close();
		entityFactory.close();
	}
}
