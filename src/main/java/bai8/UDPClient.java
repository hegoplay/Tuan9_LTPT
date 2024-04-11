package bai8;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class UDPClient {
	private static final int PIECES_OF_FILE_SIZE = 1024 * 32;
//	Địa chỉ lưu file
	private static final String destDict = "C:\\Users\\ADMIN\\Pictures\\meme1\\";
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		DatagramSocket clientSocket = new DatagramSocket(0);
        //DatagramSocket clientSocket = new DatagramSocket();
        byte[] sendData = new byte[PIECES_OF_FILE_SIZE];   //store outgoing data.    
        byte[] receiveData = new byte[PIECES_OF_FILE_SIZE];  //store incoming data    
        //Amount of data = 65535 - 20 (IP Header) - 8 (UDP Header) = 65508 
        
        InetAddress serverAddress = InetAddress.getByName("localhost");
        
        String fileName = "Ai no Uta - Kobasolo _ Lefty Hand Cream.mp3";
//        String stringSendData = "Hello Server!";
//      truyền dẫn địa chỉ file muốn lấy
        sendData = (fileName).getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, 2520);
        clientSocket.send(sendPacket);
        //receive file
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        clientSocket.receive(receivePacket);
     
//      Nhận thông tin của file trước
        ByteArrayInputStream bais = new ByteArrayInputStream(receiveData);
        ObjectInputStream ois = new ObjectInputStream(bais);
        FileInfo fileInfo = FileInfo.class.cast(ois.readObject());
		if (fileInfo == null) {
			System.out.println("File not found");
			return;
		}
		File file = new File(destDict + fileInfo.getFileName());
		if (file.exists()) {
			file.delete();
		}
		file.createNewFile();
		
		FileOutputStream fis = new FileOutputStream(file);
//		Thực hiện đưa các dữ liệu vào stream
		for (int i = 0; i < fileInfo.getPiecesOfFile()-1; i++) {
			receivePacket = new DatagramPacket(receiveData, receiveData.length);
			clientSocket.receive(receivePacket);
			fis.write(receiveData, 0, PIECES_OF_FILE_SIZE);
		}
		receivePacket = new DatagramPacket(receiveData, receiveData.length);
		clientSocket.receive(receivePacket);
		fis.write(receiveData, 0, fileInfo.getLastByteLength());;
//		Đọc dữ liệu từ stream và ghi vào anh
		fis.flush();
		fis.close();
		System.out.println("Done");
//		System.out.println(fileInfo);
	}
}
