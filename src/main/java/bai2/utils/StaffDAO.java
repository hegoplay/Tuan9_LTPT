package bai2.utils;

import all.Constant;
import bai2.entities.Staff;

public class StaffDAO {
	public static Staff getStaffById(String id) {

		Staff s = Constant.manager.find(Staff.class, id);
		if (s==null) {
			s = new Staff();
			s.setId("null");
		}
		return s;
	}

	public static void addStaff(Staff staff) {
		Constant.manager.getTransaction().begin();
		Constant.manager.persist(staff);
		Constant.manager.getTransaction().commit();

	}

	public static void updateStaff(Staff staff) {
		Constant.manager.getTransaction().begin();
		Constant.manager.merge(staff);
		Constant.manager.getTransaction().commit();
	}

	public static void deleteStaff(String id) {
		Constant.manager.getTransaction().begin();
		Staff staff = getStaffById(id);
		Constant.manager.remove(staff);
		Constant.manager.getTransaction().commit();
	}

	public static void findStaff(String id) {
		Staff staff = getStaffById(id);
		System.out.println(staff);
	}
}
