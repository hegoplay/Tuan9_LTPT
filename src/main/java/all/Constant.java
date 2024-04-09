package all;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Constant {
	public static EntityManagerFactory entityFactory;
	public static EntityManager manager;
	public static final String split = "_";
	public static void getConnection() {
		entityFactory = Persistence.createEntityManagerFactory("JPA_ORM_Student_MSSQL");
		manager = entityFactory.createEntityManager();
	}
	public static void closeConnection() {
		manager.close();
		entityFactory.close();
	}
	public static void inputValues() {
		
	}
}
