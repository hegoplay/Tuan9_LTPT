package bai7;

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
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		DatagramSocket clientSocket = new DatagramSocket(0);
        //DatagramSocket clientSocket = new DatagramSocket();
        byte[] sendData = new byte[PIECES_OF_FILE_SIZE];   //store outgoing data.    
        byte[] receiveData = new byte[PIECES_OF_FILE_SIZE];  //store incoming data    
        //Amount of data = 65535 - 20 (IP Header) - 8 (UDP Header) = 65508 
        
        InetAddress serverAddress = InetAddress.getByName("localhost");
        
        String destDict = "C:\\Users\\ADMIN\\Pictures\\meme\\";
        
        String fileName = "7c483ad0dafb70199a6e72f42ed0403e.jpg";
//        String stringSendData = "Hello Server!";
//      truyền dẫn địa chỉ file muốn lấy
        sendData = (destDict + fileName).getBytes();
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
//		Khởi tạo stream để thu thập dữ liệu bytes của bức ảnh
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		Thực hiện đưa các dữ liệu vào stream
		for (int i = 0; i < fileInfo.getPiecesOfFile()-1; i++) {
			receivePacket = new DatagramPacket(receiveData, receiveData.length);
			clientSocket.receive(receivePacket);
			baos.write(receiveData, 0, PIECES_OF_FILE_SIZE);
		}
		receivePacket = new DatagramPacket(receiveData, receiveData.length);
		clientSocket.receive(receivePacket);
		baos.write(receiveData, 0, fileInfo.getLastByteLength());;
//		Đọc dữ liệu từ stream và ghi vào anh
		BufferedImage image = javax.imageio.ImageIO.read(new ByteArrayInputStream(baos.toByteArray()));
		JFrame frame = new JFrame();
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new JLabel(new javax.swing.ImageIcon(image)));
		frame.setVisible(true);
		frame.setTitle("Bức ảnh đã nhận");
		System.out.println("Done");
//		System.out.println(fileInfo);
	}
}
