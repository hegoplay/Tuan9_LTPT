package bai2.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import all.Constant;
import bai2.entities.Staff;
import bai3.utils.ConnectDB;

public class ClientHandle implements Runnable {

	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;

	public ClientHandle(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());

			while (true) {
				String message = in.readUTF();
				if(message.equalsIgnoreCase("Add")) {
					System.out.println("joined add case\n");
					String[] staff = in.readUTF().split(Constant.split);
					Staff s = new Staff(staff[0], staff[1], staff[2], staff[3], staff[4], Integer.parseInt(staff[5]));
					StaffDAO.addStaff(s);
					System.out.println("Added staff: " + s.getId());
				}
				if (message.equalsIgnoreCase("Update")) {
					System.out.println("joined update case\n");
					String[] staff = in.readUTF().split("_");
					Staff s = new Staff(staff[0], staff[1], staff[2], staff[3], staff[4], Integer.parseInt(staff[5]));
					StaffDAO.updateStaff(s);
					System.out.println("Updated staff: " + s.getId());
				}
				if (message.equalsIgnoreCase("Delete")) {
					System.out.println("joined delete case\n");
					String id = in.readUTF();
					StaffDAO.deleteStaff(id);
					System.out.println("Deleted staff: " + id);
				}
				if (message.equalsIgnoreCase("Find by Staff Id")) {
					System.out.println("joined search case\n");
					String id = in.readUTF();
					Staff s = StaffDAO.getStaffById(id);;
					if(s.getId() == null) {
						sendMessage("null");
						
					}
					else {
						sendMessage(s.getId() + Constant.split + s.getFirstName() + Constant.split + s.getLastName()
						+ Constant.split + s.getEmail() + Constant.split + s.getPhoneNumber() + Constant.split
						+ s.getActive());
						
					}
					System.out.println("Searched staff: " + s.getId());
				}
				System.out.println("Received: " + message);
//				logArea.append("Listen from: " + socket.getInetAddress().getHostAddress() + " - " + message + "\n");
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.out.println("Disconnected from client.");
		}finally {
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void sendMessage(String message) {
		try {
			out.writeUTF(message);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
