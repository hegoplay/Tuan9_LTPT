package bai5;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.KeyStore.Entry;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTree;

public class RemoteManager extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel pnlMain;
	private JTextField txtServer;
	private JTextField txtRequest;
	private JButton btnGo;
	private Socket socket = null;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private JTree tree;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RemoteManager frame = new RemoteManager();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public RemoteManager() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 300);
		pnlMain = new JPanel();
		pnlMain.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(pnlMain);
		pnlMain.setLayout(new BorderLayout(0, 0));

		JPanel pnlPath = new JPanel();
		pnlMain.add(pnlPath, BorderLayout.NORTH);
		GridBagLayout gbl_pnlPath = new GridBagLayout();
		gbl_pnlPath.columnWidths = new int[] { 0, 0, 0 };
		gbl_pnlPath.rowHeights = new int[] { 0, 0, 0 };
		gbl_pnlPath.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_pnlPath.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		pnlPath.setLayout(gbl_pnlPath);

		JLabel lblServer = new JLabel("Server");
		lblServer.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_lblServer = new GridBagConstraints();
		gbc_lblServer.insets = new Insets(0, 0, 5, 5);
		gbc_lblServer.anchor = GridBagConstraints.EAST;
		gbc_lblServer.gridx = 0;
		gbc_lblServer.gridy = 0;
		pnlPath.add(lblServer, gbc_lblServer);

		txtServer = new JTextField();
		txtServer.setText("localhost");
		GridBagConstraints gbc_txtServer = new GridBagConstraints();
		gbc_txtServer.gridwidth = 2;
		gbc_txtServer.insets = new Insets(0, 0, 5, 0);
		gbc_txtServer.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtServer.gridx = 1;
		gbc_txtServer.gridy = 0;
		pnlPath.add(txtServer, gbc_txtServer);
		txtServer.setColumns(10);

		JLabel lblRequestURL = new JLabel("Request URL: ");
		lblRequestURL.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_lblRequestURL = new GridBagConstraints();
		gbc_lblRequestURL.anchor = GridBagConstraints.EAST;
		gbc_lblRequestURL.insets = new Insets(0, 0, 0, 5);
		gbc_lblRequestURL.gridx = 0;
		gbc_lblRequestURL.gridy = 1;
		pnlPath.add(lblRequestURL, gbc_lblRequestURL);

		txtRequest = new JTextField();
		txtRequest.setText("C:\\Users\\ADMIN\\Documents\\Workspace\\Aseprite");
		GridBagConstraints gbc_txtRequest = new GridBagConstraints();
		gbc_txtRequest.insets = new Insets(0, 0, 0, 5);
		gbc_txtRequest.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtRequest.gridx = 1;
		gbc_txtRequest.gridy = 1;
		pnlPath.add(txtRequest, gbc_txtRequest);
		txtRequest.setColumns(10);

		btnGo = new JButton("Go");
		GridBagConstraints gbc_btnGo = new GridBagConstraints();
		gbc_btnGo.gridx = 2;
		gbc_btnGo.gridy = 1;
		pnlPath.add(btnGo, gbc_btnGo);

		JScrollPane scrCenter = new JScrollPane();
		pnlMain.add(scrCenter, BorderLayout.CENTER);

		tree = new JTree();
		scrCenter.setViewportView(tree);

		btnGo.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String request = txtRequest.getText();
		try {
			if (socket == null) {
				String server = txtServer.getText();
				socket = new Socket(server, 9999);
				out = new ObjectOutputStream(socket.getOutputStream());
				in = new ObjectInputStream(socket.getInputStream());
			}

		} catch (Exception e2) {
//			e2.printStackTrace();
			JOptionPane.showMessageDialog(this, "Cannot connect to server!");
		}
		try {

//				tree.add(ls);
			tree.removeAll();
			DefaultMutableTreeNode root = new DefaultMutableTreeNode(request);
			ArrayDeque<Map.Entry<DefaultMutableTreeNode, String>> queue = new ArrayDeque<>();

			queue.add(Map.entry(root, request));
			while (!queue.isEmpty()) {
				Map.Entry<DefaultMutableTreeNode, String> entry = queue.getFirst();
				queue.remove();
				DefaultMutableTreeNode node = entry.getKey();
				String path = entry.getValue();
//				System.out.println(path);
				out.writeUTF(path);
				out.flush();
				ArrayList<File> ls = (ArrayList<File>)in.readObject();
				if (ls == null) {
					break;
				}
				if (request.equals("exit")) {
					socket.close();
					socket = null;
				}
				System.out.println(ls);
				for (File file : ls) {
					if (file.isDirectory()) {
						DefaultMutableTreeNode child = new DefaultMutableTreeNode(file.getName());
						node.add(child);
						queue.add(Map.entry(child, path +"\\"+ file.getName()));
					} 
					if (file.isFile()){
						DefaultMutableTreeNode child = new DefaultMutableTreeNode(file.getName());
						node.add(child);
					}
				}

			}
			tree.setModel(new javax.swing.tree.DefaultTreeModel(root));
			((DefaultTreeModel) tree.getModel()).reload();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
}
