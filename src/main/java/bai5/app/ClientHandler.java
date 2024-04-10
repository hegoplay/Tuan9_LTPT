package bai5.app;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {

	Socket socket;

	public ClientHandler(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			boolean isRunning = true;
			while (isRunning) {
				String path = in.readUTF();
				if (path.equals("exit")) {

					break;
				}
				File f = new File(path);
				ArrayList<File> lstFiles = null;
				if (f.exists() && f.isDirectory()) {
					lstFiles = new ArrayList<File>();
					File[] files = f.listFiles();
					for (File file : files) {

						lstFiles.add(file);

					}
				}
				out.writeObject(lstFiles);
			}
			in.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		;
	}

}
