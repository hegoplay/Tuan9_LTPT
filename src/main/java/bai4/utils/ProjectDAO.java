package bai4.utils;

import java.util.List;

import all.Constant;
import bai4.entities.Project;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class ProjectDAO {
	public static void addProject(Project project) {
		// insert project
		Constant.manager.getTransaction().begin();
		Constant.manager.persist(project);
		Constant.manager.getTransaction().commit();
	}

	public static void updateProject(Project project) {
		// update project
		EntityTransaction trans = Constant.manager.getTransaction();
		try {
			trans.begin();
			Constant.manager.merge(project);
			trans.commit();

		} catch (Exception e) {
			if (trans.isActive())
				trans.rollback();
			e.printStackTrace();
		}
	}

	public static void deleteProject(Project project) {
		// delete project
		Constant.manager.getTransaction().begin();
		Constant.manager.remove(project);
		Constant.manager.getTransaction().commit();
	}

	public static Project getProjectById(long projectId) {
		// get project by id
		return Constant.manager.find(Project.class, projectId);
	}

	public static Project getProject(String name) {
		// get project by name
		Project p;
		try {
			p = Constant.manager.createNamedQuery("Project.findByName", Project.class).setParameter("name", name)
					.getSingleResult();
		} catch (Exception e) {
			p = null;
			e.printStackTrace();
		}
		return p;
	}

//	Tìm tất cả các dự án có nhân viên nào đó thực hiện khi biết địa chỉ email. 
	public static List<Project> getProjects(String empl_email) {
		CriteriaBuilder cb = Constant.manager.getCriteriaBuilder();
		CriteriaQuery<Project> cq = cb.createQuery(Project.class);
		Root<Project> root = cq.from(Project.class);

		Predicate where = cb.conjunction();
		where = cb.or(where, root.join("empls").get("employee").get("email").in(empl_email));
		cq = cq.where(where);
		List<Project> ls = Constant.manager.createQuery(cq).getResultList();
		return ls;
	}
}
