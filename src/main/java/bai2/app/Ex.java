package bai2.app;

import javax.swing.SwingUtilities;

public class Ex {
	public static void main(String[] args) {
		new Runnable() {
			@Override
			public void run() {
				ClientUIApps frame = new ClientUIApps();
				frame.setVisible(true);
				frame.connectToServer("localhost", 9823);
			}
		}.run();
		
	}
}
