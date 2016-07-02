import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

import org.json.simple.JSONObject;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Login to the web site http://defifoot.com/
 */
public class Login {
	public static JPanel connectionPanel = new JPanel(); // Connection's panel
	private static JTextField usernameTextField = new JTextField(); // Username text's field
	private static JPasswordField passwordField = new JPasswordField(); // Password's field
	private static JLabel loginLabel = new JLabel(""); // Login process' label
	private static JLabel loginFailedLabel = new JLabel(""); // Login failed message's label
	private static JButton loginButton = new JButton("Se connecter"); // Login's button
	private static JSONObject result = new JSONObject(); // Contains status of login process
	
	public static void main() {
		connectionPanel.repaint();
		connectionPanel.setBorder(new TitledBorder(new MatteBorder(3, 3, 3, 3, Color.decode(MainFrame.prop.getProperty("dark_gray"))), "Se connecter ¨¤ Defifoot", TitledBorder.LEADING, TitledBorder.TOP, null, Color.decode(MainFrame.prop.getProperty("dark_gray"))));
		connectionPanel.setOpaque(false);
		connectionPanel.setLayout(null);
		connectionPanel.setVisible(true);
		connectionPanel.setBounds(173, 89, 400, 250);
		MainFrame.bodyPanel.add(connectionPanel);

		JPanel usernamePanel = new JPanel();
		usernamePanel.setBackground(new Color(240, 240, 240));
		usernamePanel.setBorder(new MatteBorder(1, 1, 1, 1, Color.decode(MainFrame.prop.getProperty("dark_gray"))));
		usernamePanel.setBounds(119, 57, 163, 35);
		usernamePanel.setLayout(null);
		connectionPanel.add(usernamePanel);

		JLabel usernameLabel = new JLabel("");
		usernameLabel.setIcon(new ImageIcon(Login.class.getResource("images/icons/user.png")));
		usernameLabel.setBounds(9, 9, 16, 16);
		usernamePanel.add(usernameLabel);

		usernameTextField.setText("Pseudo");
		usernameTextField.setEditable(true);
		usernameTextField.setOpaque(false);
		usernameTextField.setBorder(null);
		usernameTextField.setFont(MainFrame.mediumFont);
		usernameTextField.setForeground(Color.decode(MainFrame.prop.getProperty("dark_gray")));
		usernameTextField.setBounds(34, 0, 120, 35);
		usernamePanel.add(usernameTextField);
		usernameTextField.requestFocusInWindow();

		JPanel passwordPanel = new JPanel();
		passwordPanel.setBackground(new Color(240, 240, 240));
		passwordPanel.setBorder(new MatteBorder(1, 1, 1, 1, Color.decode(MainFrame.prop.getProperty("dark_gray"))));
		passwordPanel.setBounds(119, 107, 163, 35);
		passwordPanel.setLayout(null);
		connectionPanel.add(passwordPanel);

		JLabel passwordLabel = new JLabel("");
		passwordLabel.setIcon(new ImageIcon(Login.class.getResource("images/icons/password.png")));
		passwordLabel.setBounds(9, 9, 16, 16);
		passwordPanel.add(passwordLabel);

		passwordField.setText("MotDePasse");
		passwordField.setEditable(true);
		passwordField.setOpaque(false);
		passwordField.setBorder(null);
		passwordField.setFont(MainFrame.mediumFont);
		passwordField.setForeground(Color.decode(MainFrame.prop.getProperty("dark_gray")));
		passwordField.setBounds(34, 0, 120, 35);
		passwordPanel.add(passwordField);
		loginFailedLabel.setVisible(true);
		
		loginButton.setHorizontalTextPosition(SwingConstants.LEFT);
		loginButton.setEnabled(false);
		loginButton.setBorderPainted(false);
		loginButton.setFocusPainted(false);
		loginButton.setFont(MainFrame.buttonFont);
		loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		loginButton.setBackground(Color.decode(MainFrame.prop.getProperty("bleu_de_france")));
		loginButton.setForeground(Color.decode(MainFrame.prop.getProperty("white")));
		loginButton.setBounds(119, 157, 163, 35);
		connectionPanel.add(loginButton);

		loginLabel.setVisible(false);
		loginLabel.setIcon(new ImageIcon(Login.class.getResource("images/icons/ajax-loader.gif")));
		loginLabel.setBounds(184, 202, 32, 32);
		connectionPanel.add(loginLabel);

		loginFailedLabel.setVisible(false);
		loginFailedLabel.setHorizontalAlignment(SwingConstants.CENTER);
		loginFailedLabel.setFont(MainFrame.mediumFont);
		loginFailedLabel.setForeground(Color.decode(MainFrame.prop.getProperty("coral_red")));
		loginFailedLabel.setBounds(35, 202, 330, 32);
		connectionPanel.add(loginFailedLabel);
		
		usernameTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent fe) {
				if(usernameTextField.isEditable()) {
					if(Objects.equals(usernameTextField.getText(), "Pseudo")) { // Delete the placeholder text
						usernameTextField.setText("");
						usernameTextField.setForeground(Color.decode(MainFrame.prop.getProperty("onyx")));
					}
					if(Objects.equals(passwordField.getForeground(), Color.decode(MainFrame.prop.getProperty("dark_gray")))) { // Disable the login button
						loginButton.setEnabled(false);
					}
				}
			}
	
			@Override
			public void focusLost(FocusEvent fe) {
				if(Objects.equals(usernameTextField.getText(), "") || Objects.equals(usernameTextField.getText(), "Pseudo")) { // Add the placeholder text
					usernameTextField.setText("Pseudo");
					usernameTextField.setForeground(Color.decode(MainFrame.prop.getProperty("dark_gray")));
				}
			}
		});
	
		usernameTextField.addKeyListener(new KeyAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void keyReleased(KeyEvent ke) {
				if(usernameTextField.isEditable()) {
					if(Objects.equals(usernameTextField.getText(), "")) { // Disable the login button
						loginButton.setEnabled(false);
					} else {
						if(Objects.equals(passwordField.getPassword(), "") || Objects.equals(passwordField.getForeground(), Color.decode(MainFrame.prop.getProperty("dark_gray")))) { // Disable the login button
							loginButton.setEnabled(false);
						} else { // Enable the login button
							loginButton.setEnabled(true);
							if(ke.getKeyCode() == 10) { // Login when click on Enter keyboard key;
								login(usernameTextField.getText(), passwordField.getText());
							}
						}
					}
					loginFailedLabel.setVisible(false);
				}
			}
		});
	
		passwordField.addFocusListener(new FocusAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void focusGained(FocusEvent fe) {
				if(usernameTextField.isEditable()) {
					if(Objects.equals(passwordField.getText(), "MotDePasse")) { // Delete the placeholder text
						passwordField.setText("");
						passwordField.setForeground(Color.decode(MainFrame.prop.getProperty("onyx")));
					}
					if(Objects.equals(usernameTextField.getForeground(), Color.decode(MainFrame.prop.getProperty("dark_gray")))) { // Disable the login button
						loginButton.setEnabled(false);
					}
				}
			}
	
			@SuppressWarnings("deprecation")
			@Override
			public void focusLost(FocusEvent fe) {
				if(Objects.equals(passwordField.getText(), "") || Objects.equals(passwordField.getText(), "MotDePasse")) { // Add the placeholder text
					passwordField.setText("MotDePasse");
					passwordField.setForeground(Color.decode(MainFrame.prop.getProperty("dark_gray")));
				}
			}
		});
	
		passwordField.addKeyListener(new KeyAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void keyReleased(KeyEvent ke) {
				if(usernameTextField.isEditable()) {
					if(Objects.equals(passwordField.getText(), "")) { // Disable the login button
						loginButton.setEnabled(false);
					} else {
						if(Objects.equals(usernameTextField.getText(), "") || Objects.equals(usernameTextField.getForeground(), Color.decode(MainFrame.prop.getProperty("dark_gray")))) { // Disable the login button
							loginButton.setEnabled(false);
						} else { // Enable the login button
							loginButton.setEnabled(true);
							if(ke.getKeyCode() == 10) { // Login when click on Enter keyboard key;
								login(usernameTextField.getText(), passwordField.getText());
							}
						}
					}
					loginFailedLabel.setVisible(false);
				}
			}
		});
	
		loginButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent me) {
				if(me.getComponent().isEnabled()) { // Add the enabled button's design
					((AbstractButton) me.getComponent()).setBorderPainted(true);
					((JComponent) me.getComponent()).setBorder(BorderFactory.createLineBorder(Color.decode(MainFrame.prop.getProperty("bleu_de_france"))));
					me.getComponent().setBackground(Color.decode(MainFrame.prop.getProperty("white")));
					me.getComponent().setForeground(Color.decode(MainFrame.prop.getProperty("bleu_de_france")));
				}
			}
	
			@Override
			public void mouseExited(MouseEvent me) {
				if(me.getComponent().isEnabled()) { // Add the enabled button's design
					((AbstractButton) me.getComponent()).setBorderPainted(false);
					me.getComponent().setBackground(Color.decode(MainFrame.prop.getProperty("bleu_de_france")));
					me.getComponent().setForeground(Color.decode(MainFrame.prop.getProperty("white")));
				}
			}
	
			@SuppressWarnings("deprecation")
			@Override
			public void mouseClicked(MouseEvent me) {
				if(loginButton.isEnabled()) {
					login(usernameTextField.getText(), passwordField.getText());
				}
			}
		});
	}
	
	public static void login(String pseudo, String motDePasse) {
		MainFrame.username = pseudo;
		Filter.updateStatus();
		Header.navBarPanel.setEnabled(false);
		loginLabel.setVisible(true);
		loginFailedLabel.setVisible(false);
		usernameTextField.setEditable(false);
		passwordField.setEditable(false);
		loginButton.setEnabled(false);
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
		executor.schedule(new Runnable() {
			public void run() {
				result = connect(pseudo, motDePasse);
				Header.navBarPanel.setEnabled(true);
				if(result.get("Error").equals(false)) { // Successfully logged in
					loginLabel.setVisible(false);
					Toast.toastLabel.setText("Vous vous ¨ºtes connect¨¦ avec succ¨¨s ^_^");
					Toast.toastLabel.getParent().getParent().setVisible(true);
					int delay = 2000;
					Timer timer = new Timer(delay, taskPerformer);
					timer.setRepeats(false);
					timer.start();
				} else { // Login failed
					loginLabel.setVisible(false);
					loginFailedLabel.setText("* " + result.get("ErrorMsg").toString());
					loginFailedLabel.setVisible(true);
					usernameTextField.setEditable(true);
					passwordField.setEditable(true);
					loginButton.setEnabled(true);
					loginButton.setBackground(Color.decode(MainFrame.prop.getProperty("bleu_de_france")));
					loginButton.setForeground(Color.decode(MainFrame.prop.getProperty("white")));
				}
			}
		}, 1000, TimeUnit.MILLISECONDS);
	}
	
	@SuppressWarnings("unchecked")
	public static JSONObject connect(String pseudo, String motDePasse) {
		JSONObject result = new JSONObject();
		try {
			MainFrame.webClient = new WebClient();
			MainFrame.webClient.getOptions().setTimeout(2700000);
			MainFrame.webClient.getOptions().setJavaScriptEnabled(false);
			MainFrame.webClient.getOptions().setCssEnabled(false);
			MainFrame.webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
			MainFrame.webClient.getOptions().setThrowExceptionOnScriptError(false);
			MainFrame.webClient.getOptions().setPrintContentOnFailingStatusCode(false);
	        HtmlPage page = (HtmlPage) MainFrame.webClient.getPage(MainFrame.prop.getProperty("url.main_site"));
	        HtmlForm form = page.getFormByName("defifoot_jeu_connexion");
	        form.getInputByName("pseudo").setValueAttribute(pseudo); 
	        HtmlInput passWordInput = form.getInputByName("password");
	        passWordInput.removeAttribute("disabled");
	        passWordInput.setValueAttribute(motDePasse); 
	        DomElement submitBtn = form.getFirstElementChild().getNextElementSibling().getNextElementSibling().getNextElementSibling();
	        page = submitBtn.click();
	        result.put("Error", page.asText().contains("Login ou mot de passe incorrect"));
	        result.put("ErrorMsg", page.asText().contains("Login ou mot de passe incorrect") ? "Login ou mot de passe incorrect" : "");
	    } catch (Exception e) {
	    	result.put("Error", true);
	        result.put("ErrorMsg", "Echec de connexion, veuillez r¨¦essayer plus tard.");
	    } finally {
	    	MainFrame.webClient.close();
	    }
		return result;
	}
	
	public static ActionListener taskPerformer = new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
			connectionPanel.setVisible(false);
			Toast.toastLabel.getParent().getParent().setVisible(false);
			MainFrame.isLoggedIn = true;
			switch(Header.functionChosen) {
				case 2:
					Filter.filterDetailsPanel.setVisible(true);
					break;
				case 4:
					MyProfile.myProfilePanel.setVisible(true);
					MyProfile.managerNameLabel.setText("");
					MyProfile.clubNameLabel.setText("");
					MyProfile.joueursTextLabel.setText("");
					MyProfile.npaTextLabel.setText("");
					MyProfile.npvTextLabel.setText("");
					MyProfile.ageTextLabel.setText("");
					MyProfile.experienceTextLabel.setText("");
					MyProfile.pressionTextLabel.setText("");
					MyProfile.flairTextLabel.setText("");
					MyProfile.updateClubData();
					break;
			}
		}
	};
}