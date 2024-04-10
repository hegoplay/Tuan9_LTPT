package bai4.utils;

import all.Constant;
import bai4.entities.Employee;
import bai4.entities.EmployeeProject;
import bai4.entities.Project;
import jakarta.persistence.criteria.CriteriaBuilder;

public class EmployeeProjectDAO {
	public static void addEmployeeProject(EmployeeProject employeeProject) {
		// TODO Auto-generated method stub
		Constant.manager.getTransaction().begin();
		Constant.manager.persist(employeeProject);
		Constant.manager.getTransaction().commit();
	}

	public static void removeEmployeeProject(EmployeeProject employeeProject) {
		// TODO Auto-generated method stub
		Constant.manager.getTransaction().begin();
		Constant.manager.remove(employeeProject);
		Constant.manager.getTransaction().commit();
	}

	public static void updateEmployeeProject(EmployeeProject employeeProject) {
		// TODO Auto-generated method stub
		Constant.manager.getTransaction().begin();
		Constant.manager.merge(employeeProject);
		Constant.manager.getTransaction().commit();
	}

	public static EmployeeProject getEmployeeProject(Employee e, Project p) {
		// TODO Auto-generated method stub
		EmployeeProject employeeProject = Constant.manager.createQuery(
				"SELECT ep FROM EmployeeProject ep WHERE ep.employee = :employee AND ep.project = :project",
				EmployeeProject.class).setParameter("employee", e).setParameter("project", p).getSingleResult();
		return employeeProject;
		
	}
}
