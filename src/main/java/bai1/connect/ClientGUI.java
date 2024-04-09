package bai1.connect;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import bai1.service.CalculateService;

import java.awt.Component;
import javax.swing.Box;
import javax.swing.JButton;
import java.awt.Font;

public class ClientGUI implements ActionListener{

	private JFrame frame;
	private JTextField txtNhapA;
	private JTextField txtNhapB;
	private JTextField txtKetQua;
	private JButton btnCong;
	
	
	private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Thread listenerThread;
	private JButton btnTru;
	private JButton btnNhan;
	private JButton btnChia;
	private Registry registry;
	private CalculateService service;
	private JButton btnXoa;
	private JButton btnThoat;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		ClientGUI window = new ClientGUI();
		window.frame.setVisible(true);
		try {
			window.connectToServer("localhost", ServerApp.PORT);
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public ClientGUI() {
		initialize();
		JButton Button = new JButton("Close");
		Button.addActionListener(e -> {
			closeConnection();
		});
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel pnlContent = new JPanel();
		frame.getContentPane().add(pnlContent, BorderLayout.CENTER);
		pnlContent.setLayout(new GridLayout(7, 0, 0, 30));
		
		JLabel lblNhapSoA = new JLabel("Nhập số A:");
		lblNhapSoA.setFont(new Font("Tahoma", Font.BOLD, 14));
		pnlContent.add(lblNhapSoA);
		
		txtNhapA = new JTextField();
		pnlContent.add(txtNhapA);
		txtNhapA.setColumns(10);
		
		JLabel lblNhapB = new JLabel("Nhập số B:");
		lblNhapB.setFont(new Font("Tahoma", Font.BOLD, 14));
		pnlContent.add(lblNhapB);
		
		txtNhapB = new JTextField();
		pnlContent.add(txtNhapB);
		txtNhapB.setColumns(10);
		
		JLabel lblKetQua = new JLabel("Kết Quả");
		lblKetQua.setFont(new Font("Tahoma", Font.BOLD, 14));
		pnlContent.add(lblKetQua);
		
		txtKetQua = new JTextField();
		txtKetQua.setEditable(false);
		lblKetQua.setLabelFor(txtKetQua);
		pnlContent.add(txtKetQua);
		txtKetQua.setColumns(10);
		
		JPanel pnlOptions = new JPanel();
		pnlContent.add(pnlOptions);
		pnlOptions.setLayout(new GridLayout(0, 6, 0, 0));
		
		btnCong = new JButton("+");
		pnlOptions.add(btnCong);
		
		btnTru = new JButton("-");
		pnlOptions.add(btnTru);
		
		btnNhan = new JButton("*");
		pnlOptions.add(btnNhan);
		
		btnChia = new JButton("/");
		pnlOptions.add(btnChia);
		
		btnXoa = new JButton("Xóa");
		pnlOptions.add(btnXoa);
		
		btnThoat = new JButton("Thoát");
		pnlOptions.add(btnThoat);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		frame.getContentPane().add(horizontalStrut, BorderLayout.WEST);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		frame.getContentPane().add(horizontalStrut_1, BorderLayout.EAST);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		frame.getContentPane().add(verticalStrut, BorderLayout.NORTH);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		frame.getContentPane().add(verticalStrut_1, BorderLayout.SOUTH);
		btnChia.addActionListener(this);
		btnCong.addActionListener(this);
		btnTru.addActionListener(this);
		btnNhan.addActionListener(this);
		btnXoa.addActionListener(this);
		btnThoat.addActionListener(this);
	
	}
	private void connectToServer(String serverAddress, int serverPort) throws RemoteException, NotBoundException {
        registry = LocateRegistry.getRegistry(serverAddress, serverPort);
        
        service = (CalculateService) registry.lookup("CalculateService");
    
        
        
	}
	private void closeConnection() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj = e.getSource();
		if (obj == btnCong) {
			try {
				int a = Integer.parseInt(txtNhapA.getText());
				int b = Integer.parseInt(txtNhapB.getText());
				int result = service.add(a, b);
				txtKetQua.setText(result + "");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (obj == btnTru) {
			try {
				int a = Integer.parseInt(txtNhapA.getText());
				int b = Integer.parseInt(txtNhapB.getText());
				int result = service.sub(a, b);
				txtKetQua.setText(result + "");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (obj == btnNhan) {
			try {
				int a = Integer.parseInt(txtNhapA.getText());
				int b = Integer.parseInt(txtNhapB.getText());
				int result = service.mul(a, b);
				txtKetQua.setText(result + "");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (obj == btnChia) {
			try {
				int a = Integer.parseInt(txtNhapA.getText());
				int b = Integer.parseInt(txtNhapB.getText());
				int result = service.div(a, b);
				txtKetQua.setText(result + "");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (obj == btnXoa) {
			txtNhapA.setText("");
			txtNhapB.setText("");
			txtKetQua.setText("");
		}
		if (obj == btnThoat) {
			System.exit(0);
		}
	}
}
