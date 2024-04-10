package bai6;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {
	
	Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	
	public ClientHandler(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			ois = new ObjectInputStream(socket.getInputStream());
			oos = new ObjectOutputStream(socket.getOutputStream());
			boolean isRunning = true;
			while (isRunning) {
				String msg = ois.readUTF();
				
				ServerApp.sendMessage(msg);
				if (msg.split(":")[1].equalsIgnoreCase("exit")) {
					isRunning = false;
				}
			}
			ois.close();
			oos.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendMessage(String msg) {
		// TODO Auto-generated method stub
		try {
			oos.writeUTF(msg);
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
