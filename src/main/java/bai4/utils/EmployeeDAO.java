package bai4.utils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import all.Constant;
import bai4.entities.Employee;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;

public class EmployeeDAO {
	public static void addEmployee(Employee employee) {
		// TODO Auto-generated method stub
		Constant.manager.getTransaction().begin();
		Constant.manager.persist(employee);
		Constant.manager.getTransaction().commit();
	}

	public static void removeEmployee(Employee employee) {
		// TODO Auto-generated method stub
		Constant.manager.getTransaction().begin();
		Constant.manager.remove(employee);
		Constant.manager.getTransaction().commit();
	}

	public static void updateEmployee(Employee employee) {
		// TODO Auto-generated method stub
		Constant.manager.getTransaction().begin();
		Constant.manager.merge(employee);
		Constant.manager.getTransaction().commit();
	}

	public static Employee getEmployee(long id) {
		// TODO Auto-generated method stub
		return Constant.manager.find(Employee.class, id);
	}
//	4) Tìm các nhân viên khi biết tên (tìm tương đối): Yêu cầu dùng Text search trên cột 
//	firstName và lastName. 
	public static List<Employee> getEmployeeByName(String firstName, String lastName) {
		// TODO Auto-generated method stub
		CriteriaBuilder cb = Constant.manager.getCriteriaBuilder();
		jakarta.persistence.criteria.CriteriaQuery<Employee> query = cb.createQuery(Employee.class);
		jakarta.persistence.criteria.Root<Employee> root = query.from(Employee.class);
		query.select(root).where(cb.like(root.get("firstName"), "%" + firstName + "%"),
				cb.like(root.get("lastName"), "%" + lastName + "%"));
		return Constant.manager.createQuery(query).getResultList();
	}
//	5) Lập bảng lương cho các nhân viên. Thông tin gồm thông tin của nhân viên, tổng số giờ
//	nhân viên đó đã làm và tổng tiền lương. Biết rằng, tiền lương 1 giờ là 6$.
	public static Map<Employee, Object[]> getStatistic(){
		CriteriaBuilder cb = Constant.manager.getCriteriaBuilder();
		jakarta.persistence.criteria.CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
		jakarta.persistence.criteria.Root<Employee> root = query.from(Employee.class);
//		get total hour
		Expression<Number> sum = cb.sum(root.join("projs").get("hour"));
		//		get total salary
		Expression<Number> totalSalary = cb.prod(sum, 6);
		query.multiselect(root, sum, totalSalary).groupBy(root);
		List<Object[]> ls = Constant.manager.createQuery(query).getResultList();
		Map<Employee, Object[]> res = ls.stream().collect(Collectors.toMap(e -> (Employee) e[0], e -> new Object[] {e[1], e[2]}));
		return res;
	}
}
