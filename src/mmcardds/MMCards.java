package mmcardds;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MMCards extends JFrame {
	public static long startTimeMillis = 0;
	static int width = 1300;
	static int height = 870;
	static String PLAYER="";	
	
	public MMCards() {
		setTitle("My Game");
		setSize(width, height);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		
		PLAYER = JOptionPane.showInputDialog("Please enter the name of player");		
		//KHỞI TẠO TIME SAU KHI PLAYER CUNG CẤP IN4
		startTimeMillis = System.currentTimeMillis();		
		add(new Settings());
		
		setVisible(true);
	}
}
