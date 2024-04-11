package bai7;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;



public class UDPServer {
	private static  DatagramSocket serverSocket;
	private static int port = 2520; 

	private static final int PIECES_OF_FILE_SIZE = 1024 * 32;
	private static final String parPath = "";
	public static void main(String[] args) throws SocketException {
//		Tạo 1 kết nối mới với port 2520
		serverSocket = new DatagramSocket(port);
		while(true) {
			try {
				sendFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	private static void sendFile() throws IOException, InterruptedException {
//		Nhập địa chỉ file cần gửi từ client
		byte[] receiveData = new byte[PIECES_OF_FILE_SIZE];
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		serverSocket.receive(receivePacket);
//		Chuyển địa chỉ file từ dạng byte sang string
		String sourcePath = new String(receivePacket.getData()).trim();
		System.out.println("Client: " + receivePacket.getAddress().getHostAddress() + ":" + receivePacket.getPort() + " request download file: " + sourcePath);
		File file = new File(parPath +sourcePath);
		FileInfo fileInfo;
//		Kiểm tra file có tồn tại không
		if(file.exists() && file.isFile()) {
			fileInfo= new FileInfo();
		}
		else {
			fileInfo = null;
		}
//		trả dữ liệu null nếu file rỗng
		if(fileInfo == null) {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(fileInfo);
			oos.flush();
			DatagramPacket sendData = new DatagramPacket(bos.toByteArray(), bos.toByteArray().length, receivePacket.getAddress(), receivePacket.getPort());
			serverSocket.send(sendData);
			return;
		}
//		xử lý các số liệu của file
		byte[] bytePart = new byte[PIECES_OF_FILE_SIZE];
		long fileSize = file.length();
		int piecesOfFile = (int) (fileSize / PIECES_OF_FILE_SIZE);
		int lastByteLength = (int) (fileSize % PIECES_OF_FILE_SIZE);
		if (lastByteLength > 0) {
			piecesOfFile++;
		}
		else {
			lastByteLength = PIECES_OF_FILE_SIZE;
		}
		InputStream in = new FileInputStream(file);
		BufferedInputStream bin = new BufferedInputStream(in);
		byte[][] fileData = new byte[piecesOfFile][PIECES_OF_FILE_SIZE];
		for (int i = 0; i < piecesOfFile-1; i++) {
			int bytesRead = bin.read(fileData[i], 0, PIECES_OF_FILE_SIZE);
		}
		bin.read(fileData[piecesOfFile - 1], 0, lastByteLength);
		fileInfo.setFileName(file.getName());
		fileInfo.setFileSize(fileSize);
		fileInfo.setPiecesOfFile(piecesOfFile);
		fileInfo.setLastByteLength(lastByteLength);
//		Chuyển thông tin file từ dạng object sang byte
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(fileInfo);
		oos.flush();
//		Truyển gửi các dữ liệu của file dưới dạng array bytes
		DatagramPacket sendData = new DatagramPacket(bos.toByteArray(), bos.toByteArray().length, receivePacket.getAddress(), receivePacket.getPort());
		serverSocket.send(sendData);
		Thread.sleep(40);
		System.out.println("Sending data...");
//		return;
		for (int i = 0; i < piecesOfFile; i++) {
			sendData = new DatagramPacket(fileData[i], PIECES_OF_FILE_SIZE, receivePacket.getAddress(),
					receivePacket.getPort());
			serverSocket.send(sendData);
			Thread.sleep(40);
		}
//		sendData = new DatagramPacket(fileData[piecesOfFile - 1], PIECES_OF_FILE_SIZE, receivePacket.getAddress(),
//				receivePacket.getPort());
		
		System.out.println("Send file success");
		bin.close();
	}
}
