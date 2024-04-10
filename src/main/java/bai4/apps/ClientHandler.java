package bai4.apps;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import bai4.entities.Employee;
import bai4.entities.EmployeeProject;
import bai4.entities.Project;
import bai4.utils.EmployeeDAO;
import bai4.utils.EmployeeProjectDAO;
import bai4.utils.ProjectDAO;

public class ClientHandler implements Runnable {

	private Socket socket;

	ClientHandler(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

			boolean isRunning = true;
			long id;
			while (isRunning) {
				Options option = (Options) in.readObject();
				switch (option) {
				case EXIT:
					isRunning = false;
					break;
				case ADD_EMPLOYEE:
					Employee employee = (Employee) in.readObject();
					EmployeeDAO.addEmployee(employee);
					break;
				case ADD_PROJECT:
					Project project = (Project) in.readObject();
					ProjectDAO.addProject(project);
					break;
				case ADD_EMPLOYEE_PROJECT:
					EmployeeProject ep = (EmployeeProject) in.readObject();
					EmployeeProjectDAO.addEmployeeProject(ep);
					break;
				case GET_EMPLOYEE:
					id = in.readLong();
					out.writeObject(EmployeeDAO.getEmployee(id));
					out.flush();
					break;
				case GET_PROJECT:
					id = in.readLong();
					out.writeObject(ProjectDAO.getProjectById(id));
					out.flush();
					break;
				case GET_PROJECT_NAME:
					String name =  in.readUTF();
					out.writeObject(ProjectDAO.getProject(name));
					out.flush();
					break;
				case GET_ALL_PROJECTS_EMAIL:
					String email = in.readUTF();
					out.writeObject(ProjectDAO.getProjects(email));
					out.flush();
					break;
				case GET_EMPLOYEE_NAME:
					String firstName = in.readUTF();
					String lastName = in.readUTF();
					out.writeObject(EmployeeDAO.getEmployeeByName(firstName, lastName));
					out.flush();
				case GET_STATISTIC:
					out.writeObject(EmployeeDAO.getStatistic());
					out.flush();
	
					break;
				default:
					System.out.println("Invalid option");
					break;
				}
			}
			in.close();
			out.close();
			socket.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Class not found");
		}

	}

}
