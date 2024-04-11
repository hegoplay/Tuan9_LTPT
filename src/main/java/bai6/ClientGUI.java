package bai6;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.TitledBorder;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

import javax.swing.JTextArea;

public class ClientGUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private Socket socket;
	private String name;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private String host;
	private JTextArea textArea;
	private Thread thread;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		String host = JOptionPane.showInputDialog("Nhập địa chỉ máy chủ");
		String userName = JOptionPane.showInputDialog("Nhập vào nick chat của bạn");

		ClientGUI frame;
		try {
			frame = new ClientGUI(host, userName);
			frame.setVisible(true);
			frame.connectServer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Create the frame.
	 * 
	 * @throws IOException
	 * @throws UnknownHostException
	 */
	public ClientGUI(String host, String name) throws UnknownHostException, IOException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 505, 357);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel pnlMsg = new JPanel();
		pnlMsg.setBorder(new TitledBorder(null, "Enter message", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(pnlMsg, BorderLayout.SOUTH);
		GridBagLayout gbl_pnlMsg = new GridBagLayout();
		gbl_pnlMsg.columnWidths = new int[] { 0, 0 };
		gbl_pnlMsg.rowHeights = new int[] { 0, 0 };
		gbl_pnlMsg.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_pnlMsg.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		pnlMsg.setLayout(gbl_pnlMsg);

		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 0, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 0;
		pnlMsg.add(textField, gbc_textField);
		textField.setColumns(10);

		JButton btnSend = new JButton("Send");
		GridBagConstraints gbc_btnSend = new GridBagConstraints();
		gbc_btnSend.gridx = 1;
		gbc_btnSend.gridy = 0;
		pnlMsg.add(btnSend, gbc_btnSend);

		JScrollPane scrCenter = new JScrollPane();
		scrCenter.setViewportBorder(
				new TitledBorder(null, "Messages", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(scrCenter, BorderLayout.CENTER);

		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setColumns(5);
		scrCenter.setViewportView(textArea);
		this.name = name;
		this.host = host;
		btnSend.addActionListener(this);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String msg = name + "(" + LocalDateTime.now() + "):" + textField.getText();
		if (msg.contains("exit")) {
			try {
				thread.interrupt();
				oos.close();
				ois.close();
				socket.close();
				System.exit(0);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		textField.setText("");
		try {
			oos.writeUTF(msg);
			oos.flush();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public void connectServer() throws UnknownHostException, IOException {
		socket = new Socket(host, 8980);
		oos = new ObjectOutputStream(socket.getOutputStream());
		ois = new ObjectInputStream(socket.getInputStream());
		
		thread = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (true) {
					try {
						String msg = ois.readUTF();
						textArea.append(msg + "\n");
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}

			}
		});
		thread.run();
	}
	
}
