package bai2.app;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import all.Constant;
import bai2.entities.Staff;

import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import javax.swing.JButton;

public class ClientUIApps extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtId;
	private JTextField txtFirstName;
	private JTextField txtLastName;
	private JTextField txtEmail;
	private JTextField txtPhone;
	private JTextField txtActive;

	private final int lengthLblSize = 80;
	final static int PORT = 9999;
	

	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private Thread listenerThread;
	private JButton btnAdd;
	private JButton btnUpdate;
	private JButton btnDelete;
	private JButton btnFind;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public ClientUIApps() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JLabel lblInfo = new JLabel("Staff Infomation");
		lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfo.setFont(new Font("Tahoma", Font.BOLD, 38));
		contentPane.add(lblInfo, BorderLayout.NORTH);

		JPanel pnlCenter = new JPanel();
		contentPane.add(pnlCenter, BorderLayout.CENTER);
		pnlCenter.setLayout(new GridLayout(5, 1, 0, 10));

		JPanel pnlId = new JPanel();
		pnlCenter.add(pnlId);
		pnlId.setLayout(new BoxLayout(pnlId, BoxLayout.X_AXIS));

		JLabel lblId = new JLabel("Staff ID: ");
		lblId.setPreferredSize(new Dimension(lengthLblSize, 20));
		lblId.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblId.setHorizontalAlignment(SwingConstants.LEFT);
		pnlId.add(lblId);

		txtId = new JTextField();
		txtId.setText("ST911");
		pnlId.add(txtId);
		txtId.setColumns(10);

		JPanel pnlName = new JPanel();
		pnlCenter.add(pnlName);
		pnlName.setLayout(new BoxLayout(pnlName, BoxLayout.X_AXIS));

		JLabel lblFirstName = new JLabel("First Name: ");
		lblFirstName.setPreferredSize(new Dimension(lengthLblSize, 20));
		lblFirstName.setHorizontalAlignment(SwingConstants.LEFT);
		lblFirstName.setFont(new Font("Tahoma", Font.BOLD, 12));
		pnlName.add(lblFirstName);

		txtFirstName = new JTextField();
		txtFirstName.setText("Ian");
		txtFirstName.setColumns(10);
		pnlName.add(txtFirstName);

		JLabel lblLastName = new JLabel("Last Name: ");
		lblLastName.setHorizontalAlignment(SwingConstants.LEFT);
		lblLastName.setFont(new Font("Tahoma", Font.BOLD, 12));
		pnlName.add(lblLastName);

		txtLastName = new JTextField();
		txtLastName.setText("McNair");
		txtLastName.setColumns(10);
		pnlName.add(txtLastName);

		JPanel pnlEmail = new JPanel();
		pnlCenter.add(pnlEmail);
		pnlEmail.setLayout(new BoxLayout(pnlEmail, BoxLayout.X_AXIS));

		JLabel lblEmail = new JLabel("Email: ");
		lblEmail.setPreferredSize(new Dimension(lengthLblSize, 20));
		lblEmail.setHorizontalAlignment(SwingConstants.LEFT);
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 12));
		pnlEmail.add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.setText("ian.mcnair@gmail.com");
		txtEmail.setColumns(10);
		pnlEmail.add(txtEmail);

		JPanel pnlPhone = new JPanel();
		pnlCenter.add(pnlPhone);
		pnlPhone.setLayout(new BoxLayout(pnlPhone, BoxLayout.X_AXIS));

		JLabel lblPhone = new JLabel("Phone: ");
		lblPhone.setPreferredSize(new Dimension(lengthLblSize, 20));
		lblPhone.setHorizontalAlignment(SwingConstants.LEFT);
		lblPhone.setFont(new Font("Tahoma", Font.BOLD, 12));
		pnlPhone.add(lblPhone);

		txtPhone = new JTextField();
		txtPhone.setText("346 855-156");
		txtPhone.setColumns(10);
		pnlPhone.add(txtPhone);

		JPanel pnlActive = new JPanel();
		pnlCenter.add(pnlActive);
		pnlActive.setLayout(new BoxLayout(pnlActive, BoxLayout.X_AXIS));

		JLabel lblActive = new JLabel("Active: ");
		lblActive.setPreferredSize(new Dimension(lengthLblSize, 20));
		lblActive.setHorizontalAlignment(SwingConstants.LEFT);
		lblActive.setFont(new Font("Tahoma", Font.BOLD, 12));
		pnlActive.add(lblActive);

		txtActive = new JTextField();
		txtActive.setText("1");
		txtActive.setColumns(10);
		pnlActive.add(txtActive);

		JPanel pnlFeature = new JPanel();
		contentPane.add(pnlFeature, BorderLayout.SOUTH);

		btnAdd = new JButton("Add");
		pnlFeature.add(btnAdd);

		btnUpdate = new JButton("Update");
		pnlFeature.add(btnUpdate);

		btnDelete = new JButton("Delete");
		pnlFeature.add(btnDelete);

		btnFind = new JButton("Find by Staff Id");
		pnlFeature.add(btnFind);

		btnAdd.addActionListener(this);
		btnUpdate.addActionListener(this);
		btnDelete.addActionListener(this);
		btnFind.addActionListener(this);
		pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object source = e.getSource();
		
		if (source == btnAdd) {
			try {
				out.writeUTF(btnAdd.getText());
				Staff s = new Staff(txtId.getText(), txtFirstName.getText(), txtLastName.getText(),
						txtEmail.getText(), txtPhone.getText(), Integer.parseInt(txtActive.getText()));
				out.writeUTF(s.getId() + Constant.split + s.getFirstName() + Constant.split + s.getLastName() +
						Constant.split + s.getEmail() + Constant.split + s.getPhoneNumber() +
						Constant.split + s.getActive());
				out.flush();
				System.out.println("Send: ADD");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (source == btnUpdate) {
			try {
				out.writeUTF(btnUpdate.getText());
				Staff s = new Staff(txtId.getText(), txtFirstName.getText(), txtLastName.getText(), txtEmail.getText(),
						txtPhone.getText(), Integer.parseInt(txtActive.getText()));
				out.writeUTF(s.getId() + Constant.split + s.getFirstName() + Constant.split + s.getLastName()
						+ Constant.split + s.getEmail() + Constant.split + s.getPhoneNumber() + Constant.split
						+ s.getActive());
				out.flush();
				System.out.println("Send: UPDATE");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (source == btnDelete) {
			try {
				out.writeUTF(btnDelete.getText());
				out.writeUTF(txtId.getText());
				out.flush();
				System.out.println("Send: DELETE");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (source == btnFind) {
			try {
				out.writeUTF(btnFind.getText());
				out.writeUTF(txtId.getText());
				out.flush();
				String message = in.readUTF();
			
				System.out.println(message);
				if (message.equalsIgnoreCase("null")) {
					txtId.setText("");
					txtFirstName.setText("");
					txtLastName.setText("");
					txtEmail.setText("");
					txtPhone.setText("");
					txtActive.setText("");
//					return;
				}
				else {
					String[] staff = message.split(Constant.split);
					txtId.setText(staff[0]);
					txtFirstName.setText(staff[1]);
					txtLastName.setText(staff[2]);
					txtEmail.setText(staff[3]);
					txtPhone.setText(staff[4]);
					txtActive.setText(staff[5]);
					
				}
				System.out.println("Send: FIND");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public void connectToServer(String serverAddress, int serverPort) {
		try {
			socket = new Socket(serverAddress, serverPort);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());

//			listenerThread = new Thread(() -> {
//				try {
//					while (!socket.isClosed()) {
//						String message = in.readUTF();
//						System.out.println(message);
//					}
//				} catch (IOException e) {
//
//				}
//
//			});
//			listenerThread.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
