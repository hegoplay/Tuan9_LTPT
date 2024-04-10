package bai4.apps;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import bai4.entities.Employee;
import bai4.entities.EmployeeProject;
import bai4.entities.Project;

public class ClientApps {
	private static Socket socket;
	private static ObjectOutputStream out;
	private static ObjectInputStream in;

	public static void main(String[] args) {
		try {
			System.out.println("Client is running...");
			socket = new Socket("localhost", 9909);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());

//			addData();
			
			String name = "Unity Shader";
			out.writeObject(Options.GET_PROJECT_NAME);
			out.writeUTF(name);
			out.flush();
			Project project;
			project = (Project) in.readObject();
			System.out.println(project);
			
			System.out.println("---Get all projects by email---");
			
			String email = "thmnh113@gmail.com";
			out.writeObject(Options.GET_ALL_PROJECTS_EMAIL);
			out.writeUTF(email);
			out.flush();
			List<Project> projects;
			projects = (List<Project>) in.readObject();
			projects.forEach(System.out::println);
			
			System.out.println("---Get all employees by name---");
			
			String firstName = "Nguyen";
			String lastName = "Van";
			out.writeObject(Options.GET_EMPLOYEE_NAME);
			out.writeUTF(firstName);
			out.writeUTF(lastName);
			out.flush();
			List<Employee> employees;
			employees = (List<Employee>) in.readObject();
			employees.forEach(System.out::println);
			
			System.out.println("---GET STATISTIC---");
			
			out.writeObject(Options.GET_STATISTIC);
			out.flush();
			Map<Employee, Object[]> mp = (Map<Employee, Object[]>) in.readObject();
			mp.forEach((k, v) -> {
				System.out.println(k);
				System.out.println("Number of projects: " + v[0]);
				System.out.println("Total time: " + v[1]);
			});
			
			out.writeObject(Options.EXIT);
			out.flush();
			in.close();
			out.close();
			socket.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void addData() {
		Employee employees[] = new Employee[] {
				new Employee(1L, "Nguyen", "Van A", "thmnh113@gmail.com", "0123456789", new HashSet<>()),
				new Employee(2L, "Nguyen", "Van B", "thmnh114@gmail.com", "0123326789", new HashSet<>()) };
		EmployeeProject emplProjs[] = new EmployeeProject[] { new EmployeeProject(1, null, employees[0]),
				new EmployeeProject(2, null, employees[1]), new EmployeeProject(3, null, employees[0]) };
		Project projectLs[] = new Project[] {
				new Project(1L, "Unity Shader", "Game", Set.of("1.0", "2.0", "3.0"),
						Set.of(emplProjs[0], emplProjs[1])),
				new Project(2L, "Spring Boot", "Web", Set.of("1.0", "1.1", "1.1.1"), Set.of(emplProjs[2])) };

		for (Project p : projectLs) {
			for (EmployeeProject ep : p.getEmpls()) {
				ep.setProject(p);
			}
		}
		for (EmployeeProject ep : emplProjs) {
			ep.getEmployee().getProjs().add(ep);
		}
		for (Employee e : employees) {
			importData(Options.ADD_EMPLOYEE, e);
		}
		for (Project p : projectLs) {
			importData(Options.ADD_PROJECT, p);
		}
		for (EmployeeProject ep : emplProjs) {
			importData(Options.ADD_EMPLOYEE_PROJECT, ep);
		}

	}

	private static void importData(Options options, Object obj) {
		try {
			out.writeObject(options);
			out.writeObject(obj);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
