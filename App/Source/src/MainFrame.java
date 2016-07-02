import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.gargoylesoftware.htmlunit.WebClient;

public class MainFrame {
	// Global variables
	public static JFrame frmDefifootManager; // The main frame of the project
	public static JPanel bodyPanel = new JPanel(); // The body's panel of the window
	public static WebClient webClient = new WebClient(); // To keep connection to http://defifoot.com/
	public static Properties prop = new Properties(); // Take the data from config.properties file
	public static Boolean isLoggedIn = false; // User logged in or not
	public static Font titleFont = new Font("Segoe UI", Font.BOLD, 14),
					   smallFont = new Font("Trebuchet MS", Font.PLAIN, 11),
					   mediumFont = new Font("Trebuchet MS", Font.PLAIN, 14),
					   buttonFont = new Font("Tahoma", Font.PLAIN, 16); // Fonts used in the project
	public static String username = new String(); // Username of the user online
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@SuppressWarnings("static-access")
			public void run() {
				try {
					MainFrame window = new MainFrame();
					window.frmDefifootManager.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public MainFrame() throws Exception {
		initialize();
	}
	
	private void initialize() {
		// Get the data from configuration files
		new ReadPropertyFile().main("properties/config.properties");
		new ReadPropertyFile().main("properties/colors.properties");

		// Properties for WebClient
		webClient.getOptions().setTimeout(2700000);
		webClient.getOptions().setJavaScriptEnabled(false);
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setPrintContentOnFailingStatusCode(false);
		
		frmDefifootManager = new JFrame();
		frmDefifootManager.setTitle("Defifoot Manager");
		frmDefifootManager.setUndecorated(true);
		frmDefifootManager.setResizable(false);
		frmDefifootManager.setBounds(100, 100, 750, 600);
		frmDefifootManager.getContentPane().setBackground(Color.decode(MainFrame.prop.getProperty("lime_green")));
		frmDefifootManager.setIconImage(new ImageIcon(this.getClass().getResource("images/logo.png")).getImage());
		frmDefifootManager.setLocationRelativeTo(null);
		frmDefifootManager.getContentPane().setLayout(null);
		
		
		/********************************* Common *********************************/
		new Header();
		new Footer();
		new Toast();
		/******************************* End Common *******************************/
		
		
		/********************************** Body **********************************/
		bodyPanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		bodyPanel.setBackground(Color.decode(MainFrame.prop.getProperty("white")));
		bodyPanel.setBounds(2, 142, 746, 429);
		bodyPanel.setLayout(null);
		frmDefifootManager.getContentPane().add(bodyPanel);
		
		new Generate();
		new Filter();
		new Function3();
		new MyProfile();
		/******************************** End Body ********************************/
	}
}