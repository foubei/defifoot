import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

/**
 * My profile details
 */
public class MyProfile {
	public static JPanel myProfilePanel = new JPanel(); // My profile's details panel
	private static JLabel clubDataErrorLabel = new JLabel("<HTML><U>Echec du MySQL!</U></HTML>");
	private static JLabel clubLoadingLabel = new JLabel("");
	private static JLabel refreshLabel = new JLabel("");
	private static String lastUpdate = new String();
	public static JLabel managerNameLabel = new JLabel("");
	public static JLabel clubNameLabel = new JLabel("");
	public static JLabel joueursTextLabel = new JLabel("", JLabel.CENTER);
	public static JLabel npaTextLabel = new JLabel("", JLabel.CENTER);
	public static JLabel npvTextLabel = new JLabel("", JLabel.CENTER);
	public static JLabel ageTextLabel = new JLabel("", JLabel.CENTER);
	public static JLabel experienceTextLabel = new JLabel("", JLabel.CENTER);
	public static JLabel pressionTextLabel = new JLabel("", JLabel.CENTER);
	public static JLabel flairTextLabel = new JLabel("", JLabel.CENTER);
	private static JTabbedPane filterTabbedPane = new JTabbedPane(JTabbedPane.LEFT);
	private static JLabel resumeLoadingLabel = new JLabel("");
	private static JLabel nivLoadingLabel = new JLabel("");
	private static JLabel npaLoadingLabel = new JLabel("");
	private static JLabel profilLoadingLabel = new JLabel("");
	private static JLabel psyLoadingLabel = new JLabel("");
	private static JLabel transLoadingLabel = new JLabel("");
	private static JLabel resumeEmptyLabel = new JLabel("", JLabel.CENTER);
	private static JLabel nivEmptyLabel = new JLabel("", JLabel.CENTER);
	private static JLabel npaEmptyLabel = new JLabel("", JLabel.CENTER);
	private static JLabel profilEmptyLabel = new JLabel("", JLabel.CENTER);
	private static JLabel psyEmptyLabel = new JLabel("", JLabel.CENTER);
	private static JLabel transEmptyLabel = new JLabel("", JLabel.CENTER);
	private static JTable resumeTable = new JTable(new DefaultTableModel(new Object[]{"Numero", "Prenom", "Nom", "Position", "Age", "NPA", "NPV", "Reflexe", "Prise de balle", "detente", "Tete", "Tacle", "Passe", "Tir", "Dribble", "Controle", "Vitesse", "Endurance", "Physique"}, 0));
	private static JTable nivTable = new JTable(new DefaultTableModel(new Object[]{"Numero", "Prenom", "Nom", "Position", "Age", "NPA", "NPV", "Tete", "Frappe au but", "Controlle du ballon", "Dribble", "Recuperation au sol", "Passe", "Gardien", "Endurance", "Qualite psychologique"}, 0));
	private static JTable npaTable = new JTable(new DefaultTableModel(new Object[]{"Numero", "Prenom", "Nom", "Position", "Age", "NPA", "NPV", "GB", "DF", "LB", "MD", "MT", "MO", "AT", "BC"}, 0));
	private static JTable profilTable = new JTable(new DefaultTableModel(new Object[]{"Numero", "Prenom", "Nom", "Position", "Age", "NPA", "NPV", "Potentiel", "Vitesse", "Rigueur", "Regularite"}, 0));
	private static JTable psyTable = new JTable(new DefaultTableModel(new Object[]{"Numero", "Prenom", "Nom", "Position", "Age", "NPA", "NPV", "Pression", "Physique", "Forme", "Moral", "Flair", "Endurance", "Experience", "Statut", "Capitaine"}, 0));
	private static JTable transTable = new JTable(new DefaultTableModel(new Object[]{"Numero", "Prenom", "Nom", "Position", "Age", "NPA", "NPV", "Valeur", "Salaire", "Cote", "Condition d'arrivee", "date d'arrivee"}, 0));
	private static JScrollPane resumeScrollPane = new JScrollPane();
	private static JScrollPane nivScrollPane = new JScrollPane();
	private static JScrollPane npaScrollPane = new JScrollPane();
	private static JScrollPane profilScrollPane = new JScrollPane();
	private static JScrollPane psyScrollPane = new JScrollPane();
	private static JScrollPane transScrollPane = new JScrollPane();
	
	public MyProfile() {
		main();
	}
	
	private void main() {
		myProfilePanel.setOpaque(false);
		myProfilePanel.setBounds(0, 0, 746, 429);
		myProfilePanel.setLayout(null);
		myProfilePanel.setVisible(false);
		MainFrame.bodyPanel.add(myProfilePanel);
		
		JPanel clubPanel = new JPanel();
		clubPanel.setLayout(null);
		clubPanel.setBounds(0, 0, 746, 115);
		myProfilePanel.add(clubPanel);
		
		clubDataErrorLabel.setVisible(false);
		clubDataErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		clubDataErrorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		clubDataErrorLabel.setFont(MainFrame.smallFont);
		clubDataErrorLabel.setForeground(Color.decode(MainFrame.prop.getProperty("coral_red")));
		clubDataErrorLabel.setBounds(563, 15, 90, 24);
		clubDataErrorLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				updateClubData();
			}
		});
		clubPanel.add(clubDataErrorLabel);
		
		clubLoadingLabel.setVisible(false);
		clubLoadingLabel.setIcon(new ImageIcon(Login.class.getResource("images/icons/ajax-loader.gif")));
		clubLoadingLabel.setBounds(624, 11, 32, 32);
		clubPanel.add(clubLoadingLabel);
		
		refreshLabel.setVisible(false);
		refreshLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		refreshLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/icons/refresh.png")).getImage()));
		refreshLabel.setBounds(629, 15, 24, 24);
		refreshLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent me) {
				refreshLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/icons/refresh_reverse.png")).getImage()));
			}
			@Override
			public void mouseExited(MouseEvent me) {
				refreshLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/icons/refresh.png")).getImage()));
			}
			@Override
			public void mouseClicked(MouseEvent me) {
				refreshLabel.setVisible(false);
				MyProfile.updateClubData();
			}
		});
		clubPanel.add(refreshLabel);
		
		JLabel settingsLabel = new JLabel("");
		settingsLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		settingsLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/icons/settings.png")).getImage()));
		settingsLabel.setBounds(668, 15, 24, 24);
		settingsLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent me) {
				settingsLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/icons/settings_reverse.png")).getImage()));
			}
			@Override
			public void mouseExited(MouseEvent me) {
				settingsLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/icons/settings.png")).getImage()));
			}
			@Override
			public void mouseClicked(MouseEvent me) {
				new Settings();
			}
		});
		clubPanel.add(settingsLabel);
		
		JLabel logoutLabel = new JLabel("");
		logoutLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		logoutLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/icons/logout.png")).getImage()));
		logoutLabel.setBounds(707, 15, 24, 24);
		logoutLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent me) {
				logoutLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/icons/logout_reverse.png")).getImage()));
			}
			@Override
			public void mouseExited(MouseEvent me) {
				logoutLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/icons/logout.png")).getImage()));
			}
			@Override
			public void mouseClicked(MouseEvent me) {
				UIManager.put("OptionPane.background", Color.decode(MainFrame.prop.getProperty("white")));
				UIManager.put("Panel.background", Color.decode(MainFrame.prop.getProperty("white")));
				String[] options = { "Oui", "Non" };
				int dialog = JOptionPane.showOptionDialog(MainFrame.frmDefifootManager, "Etes vous sûre de vouloir se deconnecter?",
						"Se deconnecter?", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE,
						new ImageIcon(new ImageIcon(this.getClass().getResource("images/icons/warning.png")).getImage()),
						options, options[1]);

				if (dialog == JOptionPane.YES_OPTION) {
					MainFrame.isLoggedIn = false;
					MyProfile.myProfilePanel.setVisible(false);
					Login.connectionPanel.removeAll();
					Login.connectionPanel.updateUI();
					MainFrame.bodyPanel.remove(Login.connectionPanel);
					new Login();
					Login.main();
				}
			}
		});
		clubPanel.add(logoutLabel);
		
		JPanel ManagerDetailsPanel = new JPanel();
		ManagerDetailsPanel.setLocation(0, 15);
		ManagerDetailsPanel.setOpaque(false);
		ManagerDetailsPanel.setLayout(null);
		ManagerDetailsPanel.setSize(746, 60);
		clubPanel.add(ManagerDetailsPanel);
		
		JLabel avatarLabel = new JLabel("");
		avatarLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/icons/manager_avatar.png")).getImage()));
		avatarLabel.setBounds(263, 0, 55, 55);
		ManagerDetailsPanel.add(avatarLabel);
		
		managerNameLabel.setForeground(Color.decode(MainFrame.prop.getProperty("onyx")));
		managerNameLabel.setFont(MainFrame.mediumFont);
		managerNameLabel.setBounds(333, 10, 150, 15);
		ManagerDetailsPanel.add(managerNameLabel);
		
		clubNameLabel.setForeground(Color.decode(MainFrame.prop.getProperty("onyx")));
		clubNameLabel.setFont(MainFrame.mediumFont);
		clubNameLabel.setBounds(333, 30, 150, 15);
		ManagerDetailsPanel.add(clubNameLabel);
		
		JPanel clubDetailsPanel = new JPanel();
		clubDetailsPanel.setOpaque(false);
		clubDetailsPanel.setBackground(Color.WHITE);
		clubDetailsPanel.setLayout(null);
		clubDetailsPanel.setBounds(0, 75, 746, 40);
		clubPanel.add(clubDetailsPanel);
		
		JPanel joueursDetailsPanel = new JPanel();
		joueursDetailsPanel.setOpaque(false);
		joueursDetailsPanel.setLayout(null);
		joueursDetailsPanel.setBounds(15, 0, 104, 40);
		clubDetailsPanel.add(joueursDetailsPanel);
		
		joueursTextLabel.setForeground(Color.decode(MainFrame.prop.getProperty("onyx")));
		joueursTextLabel.setBounds(0, 5, 104, 15);
		joueursTextLabel.setFont(MainFrame.mediumFont);
		joueursDetailsPanel.add(joueursTextLabel);
		
		JLabel joueursValueLabel = new JLabel("Joueurs", JLabel.CENTER);
		joueursValueLabel.setForeground(Color.decode(MainFrame.prop.getProperty("dark_gray")));
		joueursValueLabel.setFont(MainFrame.smallFont);
		joueursValueLabel.setBounds(0, 20, 104, 15);
		joueursDetailsPanel.add(joueursValueLabel);
		
		JPanel npaDetailsPanel = new JPanel();
		npaDetailsPanel.setOpaque(false);
		npaDetailsPanel.setLayout(null);
		npaDetailsPanel.setBounds(119, 0, 102, 40);
		clubDetailsPanel.add(npaDetailsPanel);
		
		npaTextLabel.setForeground(Color.decode(MainFrame.prop.getProperty("onyx")));
		npaTextLabel.setBounds(0, 5, 102, 15);
		npaTextLabel.setFont(MainFrame.mediumFont);
		npaDetailsPanel.add(npaTextLabel);
		
		JLabel npaValueLabel = new JLabel("NPA", JLabel.CENTER);
		npaValueLabel.setForeground(Color.decode(MainFrame.prop.getProperty("dark_gray")));
		npaValueLabel.setFont(MainFrame.smallFont);
		npaValueLabel.setBounds(0, 20, 102, 15);
		npaDetailsPanel.add(npaValueLabel);
		
		JPanel npvDetailsPanel = new JPanel();
		npvDetailsPanel.setOpaque(false);
		npvDetailsPanel.setLayout(null);
		npvDetailsPanel.setBounds(221, 0, 102, 40);
		clubDetailsPanel.add(npvDetailsPanel);
		
		npvTextLabel.setForeground(Color.decode(MainFrame.prop.getProperty("onyx")));
		npvTextLabel.setBounds(0, 5, 102, 15);
		npvTextLabel.setFont(MainFrame.mediumFont);
		npvDetailsPanel.add(npvTextLabel);
		
		JLabel npvValueLabel = new JLabel("NPV", JLabel.CENTER);
		npvValueLabel.setForeground(Color.decode(MainFrame.prop.getProperty("dark_gray")));
		npvValueLabel.setFont(MainFrame.smallFont);
		npvValueLabel.setBounds(0, 20, 102, 15);
		npvDetailsPanel.add(npvValueLabel);
		
		JPanel ageDetailsPanel = new JPanel();
		ageDetailsPanel.setOpaque(false);
		ageDetailsPanel.setLayout(null);
		ageDetailsPanel.setBounds(323, 0, 102, 40);
		clubDetailsPanel.add(ageDetailsPanel);
		
		ageTextLabel.setForeground(Color.decode(MainFrame.prop.getProperty("onyx")));
		ageTextLabel.setBounds(0, 5, 102, 15);
		ageTextLabel.setFont(MainFrame.mediumFont);
		ageDetailsPanel.add(ageTextLabel);
		
		JLabel ageValueLabel = new JLabel("Age", JLabel.CENTER);
		ageValueLabel.setForeground(Color.decode(MainFrame.prop.getProperty("dark_gray")));
		ageValueLabel.setFont(MainFrame.smallFont);
		ageValueLabel.setBounds(0, 20, 102, 15);
		ageDetailsPanel.add(ageValueLabel);
		
		JPanel experienceDetailsPanel = new JPanel();
		experienceDetailsPanel.setOpaque(false);
		experienceDetailsPanel.setLayout(null);
		experienceDetailsPanel.setBounds(425, 0, 102, 40);
		clubDetailsPanel.add(experienceDetailsPanel);
		
		experienceTextLabel.setForeground(Color.decode(MainFrame.prop.getProperty("onyx")));
		experienceTextLabel.setBounds(0, 5, 102, 15);
		experienceTextLabel.setFont(MainFrame.mediumFont);
		experienceDetailsPanel.add(experienceTextLabel);
		
		JLabel experienceValueLabel = new JLabel("Experience", JLabel.CENTER);
		experienceValueLabel.setForeground(Color.decode(MainFrame.prop.getProperty("dark_gray")));
		experienceValueLabel.setFont(MainFrame.smallFont);
		experienceValueLabel.setBounds(0, 20, 102, 15);
		experienceDetailsPanel.add(experienceValueLabel);
		
		JPanel pressionDetailsPanel = new JPanel();
		pressionDetailsPanel.setOpaque(false);
		pressionDetailsPanel.setLayout(null);
		pressionDetailsPanel.setBounds(527, 0, 102, 40);
		clubDetailsPanel.add(pressionDetailsPanel);
		
		pressionTextLabel.setForeground(Color.decode(MainFrame.prop.getProperty("onyx")));
		pressionTextLabel.setBounds(0, 5, 102, 15);
		pressionTextLabel.setFont(MainFrame.mediumFont);
		pressionDetailsPanel.add(pressionTextLabel);
		
		JLabel pressionValueLabel = new JLabel("Pression", JLabel.CENTER);
		pressionValueLabel.setForeground(Color.decode(MainFrame.prop.getProperty("dark_gray")));
		pressionValueLabel.setFont(MainFrame.smallFont);
		pressionValueLabel.setBounds(0, 20, 102, 15);
		pressionDetailsPanel.add(pressionValueLabel);
		
		JPanel flairDetailsPanel = new JPanel();
		flairDetailsPanel.setOpaque(false);
		flairDetailsPanel.setLayout(null);
		flairDetailsPanel.setBounds(629, 0, 102, 40);
		clubDetailsPanel.add(flairDetailsPanel);
		
		flairTextLabel.setForeground(Color.decode(MainFrame.prop.getProperty("onyx")));
		flairTextLabel.setBounds(0, 5, 102, 15);
		flairTextLabel.setFont(MainFrame.mediumFont);
		flairDetailsPanel.add(flairTextLabel);
		
		JLabel flairValueLabel = new JLabel("Flair", JLabel.CENTER);
		flairValueLabel.setForeground(Color.decode(MainFrame.prop.getProperty("dark_gray")));
		flairValueLabel.setFont(MainFrame.smallFont);
		flairValueLabel.setBounds(0, 20, 102, 15);
		flairDetailsPanel.add(flairValueLabel);
		
		JPanel resumePanel = new JPanel();
		resumePanel.setBackground(Color.decode(MainFrame.prop.getProperty("white")));
		resumePanel.setLayout(null);
		JLabel resumeLabel = new JLabel("General", JLabel.CENTER);
		resumeLabel.setForeground(Color.decode(MainFrame.prop.getProperty("white")));
		resumeLabel.setPreferredSize(new Dimension(100, 35));
		resumeLabel.setFont(MainFrame.buttonFont);
		
		JPanel nivPanel = new JPanel();
		nivPanel.setBackground(Color.decode(MainFrame.prop.getProperty("white")));
		nivPanel.setLayout(null);
		JLabel nivLabel = new JLabel("Niveaux", JLabel.CENTER);
		nivLabel.setForeground(Color.decode(MainFrame.prop.getProperty("lime_green")));
		nivLabel.setPreferredSize(new Dimension(100, 35));
		nivLabel.setFont(MainFrame.buttonFont);
		nivLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		JPanel npaPanel = new JPanel();
		npaPanel.setBackground(Color.decode(MainFrame.prop.getProperty("white")));
		npaPanel.setLayout(null);
		JLabel npaLabel = new JLabel("NPA", JLabel.CENTER);
		npaLabel.setForeground(Color.decode(MainFrame.prop.getProperty("lime_green")));
		npaLabel.setPreferredSize(new Dimension(100, 35));
		npaLabel.setFont(MainFrame.buttonFont);
		npaLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		JPanel profilPanel = new JPanel();
		profilPanel.setBackground(Color.decode(MainFrame.prop.getProperty("white")));
		profilPanel.setLayout(null);
		JLabel profilLabel = new JLabel("Progression", JLabel.CENTER);
		profilLabel.setForeground(Color.decode(MainFrame.prop.getProperty("lime_green")));
		profilLabel.setPreferredSize(new Dimension(100, 35));
		profilLabel.setFont(MainFrame.buttonFont);
		profilLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		JPanel psyPanel = new JPanel();
		psyPanel.setBackground(Color.decode(MainFrame.prop.getProperty("white")));
		psyPanel.setLayout(null);
		JLabel psyLabel = new JLabel("Stats psy", JLabel.CENTER);
		psyLabel.setForeground(Color.decode(MainFrame.prop.getProperty("lime_green")));
		psyLabel.setPreferredSize(new Dimension(100, 35));
		psyLabel.setFont(MainFrame.buttonFont);
		psyLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		JPanel transPanel = new JPanel();
		transPanel.setBackground(Color.decode(MainFrame.prop.getProperty("white")));
		transPanel.setLayout(null);
		JLabel transLabel = new JLabel("Transferts", JLabel.CENTER);
		transLabel.setForeground(Color.decode(MainFrame.prop.getProperty("lime_green")));
		transLabel.setPreferredSize(new Dimension(100, 35));
		transLabel.setFont(MainFrame.buttonFont);
		transLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		filterTabbedPane.addTab("General", resumePanel);
		filterTabbedPane.setTabComponentAt(0, resumeLabel);
		filterTabbedPane.addTab("Niveaux", nivPanel);
		filterTabbedPane.setTabComponentAt(1, nivLabel);
		filterTabbedPane.addTab("NPA", npaPanel);
		filterTabbedPane.setTabComponentAt(2, npaLabel);
		filterTabbedPane.addTab("Progression", profilPanel);
		filterTabbedPane.setTabComponentAt(3, profilLabel);
		filterTabbedPane.addTab("Stats psy", psyPanel);
		filterTabbedPane.setTabComponentAt(4, psyLabel);
		filterTabbedPane.addTab("Transferts", transPanel);
		filterTabbedPane.setTabComponentAt(5, transLabel);
		filterTabbedPane.setFocusable(false);
		UIManager.put("TabbedPane.borderHightlightColor", Color.decode(MainFrame.prop.getProperty("lime_green")));
		UIManager.put("TabbedPane.contentBorderInsets", new Insets(0, 0, 0, 0));
		UIManager.put("TabbedPane.darkShadow", Color.decode(MainFrame.prop.getProperty("lime_green")));
		UIManager.put("TabbedPane.selected", Color.decode(MainFrame.prop.getProperty("lime_green")));
		UIManager.put("TabbedPane.unselectedBackground", Color.decode(MainFrame.prop.getProperty("white")));
		filterTabbedPane.setBounds(15, 129, 716, 285);
		myProfilePanel.add(filterTabbedPane);
		SwingUtilities.updateComponentTreeUI(filterTabbedPane);
		
		// Player's resume panel
		JPanel resumeStatusPanel = new JPanel();
		resumeStatusPanel.setLayout(new GridBagLayout());
		resumeStatusPanel.setOpaque(false);
		resumeStatusPanel.setBorder(new LineBorder(Color.decode(MainFrame.prop.getProperty("lime_green"))));
		resumeStatusPanel.setBounds(0, 2, 593, 283);
		resumePanel.add(resumeStatusPanel);
		
		resumeEmptyLabel.setVisible(false);
		resumeEmptyLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/icons/data_empty.png")).getImage()));
		resumeEmptyLabel.setSize(64, 64);
		resumeStatusPanel.add(resumeEmptyLabel, new GridBagConstraints());
		
		resumeLoadingLabel.setVisible(true);
		resumeLoadingLabel.setIcon(new ImageIcon(Login.class.getResource("images/icons/ajax-loader.gif")));
		resumeLoadingLabel.setSize(32, 32);
		resumeStatusPanel.add(resumeLoadingLabel, new GridBagConstraints());
		
		resumeTable.setName("resumeTable");
		resumeTable.setAutoCreateRowSorter(true);
		resumeTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		resumeTable.setCellSelectionEnabled(true);
		resumeTable.setEnabled(false);
		resumeTable.setFocusable(false);
		resumeTable.setFont(MainFrame.mediumFont);
		resumeTable.setIntercellSpacing(new Dimension(10, 10));
		resumeTable.setRowHeight(25);
		resumeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		resumeTable.getTableHeader().setFont(MainFrame.mediumFont);
		
		resumeScrollPane = new JScrollPane( resumeTable );
		resumeScrollPane.setVisible(false);
		resumeScrollPane.setBorder(new LineBorder(Color.decode(MainFrame.prop.getProperty("lime_green"))));
		resumeScrollPane.setOpaque(false);
		resumeScrollPane.setBounds(0, 2, 593, 283);
		UIManager.put("ScrollBar.background", Color.WHITE);
		resumePanel.add(resumeScrollPane);
		
		// Niveaux en match panel
		JPanel nivStatusPanel = new JPanel();
		nivStatusPanel.setLayout(new GridBagLayout());
		nivStatusPanel.setOpaque(false);
		nivStatusPanel.setBorder(new LineBorder(Color.decode(MainFrame.prop.getProperty("lime_green"))));
		nivStatusPanel.setBounds(0, 2, 593, 283);
		nivPanel.add(nivStatusPanel);
		
		nivEmptyLabel.setVisible(false);
		nivEmptyLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/icons/data_empty.png")).getImage()));
		nivEmptyLabel.setSize(64, 64);
		nivStatusPanel.add(nivEmptyLabel, new GridBagConstraints());
		
		nivLoadingLabel.setVisible(false);
		nivLoadingLabel.setIcon(new ImageIcon(Login.class.getResource("images/icons/ajax-loader.gif")));
		nivLoadingLabel.setSize(32, 32);
		nivStatusPanel.add(nivLoadingLabel, new GridBagConstraints());
		
		nivTable.setName("nivTable");
		nivTable.setAutoCreateRowSorter(true);
		nivTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		nivTable.setCellSelectionEnabled(true);
		nivTable.setEnabled(false);
		nivTable.setFocusable(false);
		nivTable.setFont(MainFrame.mediumFont);
		nivTable.setIntercellSpacing(new Dimension(10, 10));
		nivTable.setRowHeight(25);
		nivTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		nivTable.getTableHeader().setFont(MainFrame.mediumFont);
		
		nivScrollPane = new JScrollPane( nivTable );
		nivScrollPane.setVisible(false);
		nivScrollPane.setBorder(new LineBorder(Color.decode(MainFrame.prop.getProperty("lime_green"))));
		nivScrollPane.setOpaque(false);
		nivScrollPane.setBounds(0, 2, 593, 283);
		nivPanel.add(nivScrollPane);
		
		// NPA pour chaque position panel
		JPanel npaStatusPanel = new JPanel();
		npaStatusPanel.setLayout(new GridBagLayout());
		npaStatusPanel.setOpaque(false);
		npaStatusPanel.setBorder(new LineBorder(Color.decode(MainFrame.prop.getProperty("lime_green"))));
		npaStatusPanel.setBounds(0, 2, 593, 283);
		npaPanel.add(npaStatusPanel);
		
		npaEmptyLabel.setVisible(false);
		npaEmptyLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/icons/data_empty.png")).getImage()));
		npaEmptyLabel.setSize(64, 64);
		npaStatusPanel.add(npaEmptyLabel, new GridBagConstraints());
		
		npaLoadingLabel.setVisible(false);
		npaLoadingLabel.setIcon(new ImageIcon(Login.class.getResource("images/icons/ajax-loader.gif")));
		npaLoadingLabel.setSize(32, 32);
		npaStatusPanel.add(npaLoadingLabel, new GridBagConstraints());
		
		npaTable.setName("npaTable");
		npaTable.setAutoCreateRowSorter(true);
		npaTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		npaTable.setCellSelectionEnabled(true);
		npaTable.setEnabled(false);
		npaTable.setFocusable(false);
		npaTable.setFont(MainFrame.mediumFont);
		npaTable.setIntercellSpacing(new Dimension(10, 10));
		npaTable.setRowHeight(25);
		npaTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		npaTable.getTableHeader().setFont(MainFrame.mediumFont);
		
		npaScrollPane = new JScrollPane( npaTable );
		npaScrollPane.setVisible(false);
		npaScrollPane.setBorder(new LineBorder(Color.decode(MainFrame.prop.getProperty("lime_green"))));
		npaScrollPane.setOpaque(false);
		npaScrollPane.setBounds(0, 2, 593, 283);
		npaPanel.add(npaScrollPane);
		
		// Profil de progression panel
		JPanel profilStatusPanel = new JPanel();
		profilStatusPanel.setLayout(new GridBagLayout());
		profilStatusPanel.setOpaque(false);
		profilStatusPanel.setBorder(new LineBorder(Color.decode(MainFrame.prop.getProperty("lime_green"))));
		profilStatusPanel.setBounds(0, 2, 593, 283);
		profilPanel.add(profilStatusPanel);
		
		profilEmptyLabel.setVisible(false);
		profilEmptyLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/icons/data_empty.png")).getImage()));
		profilEmptyLabel.setSize(64, 64);
		profilStatusPanel.add(profilEmptyLabel, new GridBagConstraints());
		
		profilLoadingLabel.setVisible(false);
		profilLoadingLabel.setIcon(new ImageIcon(Login.class.getResource("images/icons/ajax-loader.gif")));
		profilLoadingLabel.setSize(32, 32);
		profilStatusPanel.add(profilLoadingLabel, new GridBagConstraints());
		
		profilTable.setName("profilTable");
		profilTable.setAutoCreateRowSorter(true);
		profilTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		profilTable.setCellSelectionEnabled(true);
		profilTable.setEnabled(false);
		profilTable.setFocusable(false);
		profilTable.setFont(MainFrame.mediumFont);
		profilTable.setIntercellSpacing(new Dimension(10, 10));
		profilTable.setRowHeight(25);
		profilTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		profilTable.getTableHeader().setFont(MainFrame.mediumFont);
		
		profilScrollPane = new JScrollPane( profilTable );
		profilScrollPane.setVisible(false);
		profilScrollPane.setBorder(new LineBorder(Color.decode(MainFrame.prop.getProperty("lime_green"))));
		profilScrollPane.setOpaque(false);
		profilScrollPane.setBounds(0, 2, 593, 283);
		profilPanel.add(profilScrollPane);
		
		// Stats psychologiques et physiques panel
		JPanel psyStatusPanel = new JPanel();
		psyStatusPanel.setLayout(new GridBagLayout());
		psyStatusPanel.setOpaque(false);
		psyStatusPanel.setBorder(new LineBorder(Color.decode(MainFrame.prop.getProperty("lime_green"))));
		psyStatusPanel.setBounds(0, 2, 593, 283);
		psyPanel.add(psyStatusPanel);
		
		psyEmptyLabel.setVisible(false);
		psyEmptyLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/icons/data_empty.png")).getImage()));
		psyEmptyLabel.setSize(64, 64);
		psyStatusPanel.add(psyEmptyLabel, new GridBagConstraints());
		
		psyLoadingLabel.setVisible(false);
		psyLoadingLabel.setIcon(new ImageIcon(Login.class.getResource("images/icons/ajax-loader.gif")));
		psyLoadingLabel.setSize(32, 32);
		psyStatusPanel.add(psyLoadingLabel, new GridBagConstraints());
		
		psyTable.setName("psyTable");
		psyTable.setAutoCreateRowSorter(true);
		psyTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		psyTable.setCellSelectionEnabled(true);
		psyTable.setEnabled(false);
		psyTable.setFocusable(false);
		psyTable.setFont(MainFrame.mediumFont);
		psyTable.setIntercellSpacing(new Dimension(10, 10));
		psyTable.setRowHeight(25);
		psyTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		psyTable.getTableHeader().setFont(MainFrame.mediumFont);
		
		psyScrollPane = new JScrollPane( psyTable );
		psyScrollPane.setVisible(false);
		psyScrollPane.setBorder(new LineBorder(Color.decode(MainFrame.prop.getProperty("lime_green"))));
		psyScrollPane.setOpaque(false);
		psyScrollPane.setBounds(0, 2, 593, 283);
		psyPanel.add(psyScrollPane);
		
		// Transferts et salaires panel
		JPanel transStatusPanel = new JPanel();
		transStatusPanel.setLayout(new GridBagLayout());
		transStatusPanel.setOpaque(false);
		transStatusPanel.setBorder(new LineBorder(Color.decode(MainFrame.prop.getProperty("lime_green"))));
		transStatusPanel.setBounds(0, 2, 593, 283);
		transPanel.add(transStatusPanel);
		
		transEmptyLabel.setVisible(false);
		transEmptyLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/icons/data_empty.png")).getImage()));
		transEmptyLabel.setSize(64, 64);
		transStatusPanel.add(transEmptyLabel, new GridBagConstraints());
		
		transLoadingLabel.setVisible(false);
		transLoadingLabel.setIcon(new ImageIcon(Login.class.getResource("images/icons/ajax-loader.gif")));
		transLoadingLabel.setSize(32, 32);
		transStatusPanel.add(transLoadingLabel, new GridBagConstraints());
		
		transTable.setName("transTable");
		transTable.setAutoCreateRowSorter(true);
		transTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		transTable.setCellSelectionEnabled(true);
		transTable.setEnabled(false);
		transTable.setFocusable(false);
		transTable.setFont(MainFrame.mediumFont);
		transTable.setIntercellSpacing(new Dimension(10, 10));
		transTable.setRowHeight(25);
		transTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		transTable.getTableHeader().setFont(MainFrame.mediumFont);
		
		transScrollPane = new JScrollPane( transTable );
		transScrollPane.setVisible(false);
		transScrollPane.setBorder(new LineBorder(Color.decode(MainFrame.prop.getProperty("lime_green"))));
		transScrollPane.setOpaque(false);
		transScrollPane.setBounds(0, 2, 593, 283);
		transPanel.add(transScrollPane);
		
		filterTabbedPane.addChangeListener(new ChangeListener() {
	        public void stateChanged(ChangeEvent e) { // Tab has changed
	        	resumeLabel.setForeground(Color.decode(MainFrame.prop.getProperty("lime_green")));
	        	npaLabel.setForeground(Color.decode(MainFrame.prop.getProperty("lime_green")));
	        	nivLabel.setForeground(Color.decode(MainFrame.prop.getProperty("lime_green")));
	        	profilLabel.setForeground(Color.decode(MainFrame.prop.getProperty("lime_green")));
	        	psyLabel.setForeground(Color.decode(MainFrame.prop.getProperty("lime_green")));
	        	transLabel.setForeground(Color.decode(MainFrame.prop.getProperty("lime_green")));
	        	resumeLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	        	nivLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	        	npaLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	        	profilLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	        	psyLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	        	transLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    			resumeLoadingLabel.setVisible(false);
    			nivLoadingLabel.setVisible(false);
    			npaLoadingLabel.setVisible(false);
    			profilLoadingLabel.setVisible(false);
    			psyLoadingLabel.setVisible(false);
    			transLoadingLabel.setVisible(false);
    			resumeEmptyLabel.setVisible(false);
    			nivEmptyLabel.setVisible(false);
    			npaEmptyLabel.setVisible(false);
    			profilEmptyLabel.setVisible(false);
    			psyEmptyLabel.setVisible(false);
    			transEmptyLabel.setVisible(false);
    			resumeScrollPane.setVisible(false);
    			nivScrollPane.setVisible(false);
    			npaScrollPane.setVisible(false);
    			profilScrollPane.setVisible(false);
    			psyScrollPane.setVisible(false);
    			transScrollPane.setVisible(false);
	        	
	        	switch(filterTabbedPane.getSelectedIndex()) {
	        		case 0:
	        			resumeLabel.setForeground(Color.decode(MainFrame.prop.getProperty("white")));
	        			resumeLabel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	        			resumeLoadingLabel.setVisible(true);
	        			ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
	        			executor.schedule(new Runnable() {
	        				public void run() {
	        					if(updateTable(resumeTable)) {
	        						resumeScrollPane.setVisible(true);
	        					} else {
	        						resumeEmptyLabel.setVisible(true);
	        					};
	        					resumeLoadingLabel.setVisible(false);
	        				}
	        			}, 1000, TimeUnit.MILLISECONDS);
	        			break;
	        		case 1:
	        			nivLabel.setForeground(Color.decode(MainFrame.prop.getProperty("white")));
	        			nivLabel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	        			nivLoadingLabel.setVisible(true);
	        			executor = Executors.newScheduledThreadPool(2);
	        			executor.schedule(new Runnable() {
	        				public void run() {
	        					if(updateTable(nivTable)) {
	        						nivScrollPane.setVisible(true);
	        					} else {
	        						nivEmptyLabel.setVisible(true);
	        					};
	        					nivLoadingLabel.setVisible(false);
	        				}
	        			}, 1000, TimeUnit.MILLISECONDS);
	        			break;
	        		case 2:
	        			npaLabel.setForeground(Color.decode(MainFrame.prop.getProperty("white")));
	        			npaLabel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	        			npaLoadingLabel.setVisible(true);
	        			executor = Executors.newScheduledThreadPool(2);
	        			executor.schedule(new Runnable() {
	        				public void run() {
	        					if(updateTable(npaTable)) {
	        						npaScrollPane.setVisible(true);
	        					} else {
	        						npaEmptyLabel.setVisible(true);
	        					};
	        					npaLoadingLabel.setVisible(false);
	        				}
	        			}, 1000, TimeUnit.MILLISECONDS);
	        			break;
	        		case 3:
	        			profilLabel.setForeground(Color.decode(MainFrame.prop.getProperty("white")));
	        			profilLabel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	        			profilLoadingLabel.setVisible(true);
	        			executor = Executors.newScheduledThreadPool(2);
	        			executor.schedule(new Runnable() {
	        				public void run() {
	        					if(updateTable(profilTable)) {
	        						profilScrollPane.setVisible(true);
	        					} else {
	        						profilEmptyLabel.setVisible(true);
	        					};
	        					profilLoadingLabel.setVisible(false);
	        				}
	        			}, 1000, TimeUnit.MILLISECONDS);
	        			break;
	        		case 4:
	        			psyLabel.setForeground(Color.decode(MainFrame.prop.getProperty("white")));
	        			psyLabel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	        			psyLoadingLabel.setVisible(true);
	        			executor = Executors.newScheduledThreadPool(2);
	        			executor.schedule(new Runnable() {
	        				public void run() {
	        					if(updateTable(psyTable)) {
	        						psyScrollPane.setVisible(true);
	        					} else {
	        						psyEmptyLabel.setVisible(true);
	        					};
	        					psyLoadingLabel.setVisible(false);
	        				}
	        			}, 1000, TimeUnit.MILLISECONDS);
	        			break;
	        		case 5:
	        			transLabel.setForeground(Color.decode(MainFrame.prop.getProperty("white")));
	        			transLabel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	        			transLoadingLabel.setVisible(true);
	        			executor = Executors.newScheduledThreadPool(2);
	        			executor.schedule(new Runnable() {
	        				public void run() {
	        					if(updateTable(transTable)) {
	        						transScrollPane.setVisible(true);
	        					} else {
	        						transEmptyLabel.setVisible(true);
	        					};
	        					transLoadingLabel.setVisible(false);
	        				}
	        			}, 1000, TimeUnit.MILLISECONDS);
	        			break;
	        	};
	        }
	    });
	}
	
	public static void updateClubData() {
		refreshLabel.setVisible(false);
		clubDataErrorLabel.setVisible(false);
		clubLoadingLabel.setVisible(true);
		resumeLoadingLabel.setVisible(false);
		nivLoadingLabel.setVisible(false);
		npaLoadingLabel.setVisible(false);
		profilLoadingLabel.setVisible(false);
		psyLoadingLabel.setVisible(false);
		transLoadingLabel.setVisible(false);
		resumeEmptyLabel.setVisible(false);
		nivEmptyLabel.setVisible(false);
		npaEmptyLabel.setVisible(false);
		profilEmptyLabel.setVisible(false);
		psyEmptyLabel.setVisible(false);
		transEmptyLabel.setVisible(false);
		resumeScrollPane.setVisible(false);
		nivScrollPane.setVisible(false);
		npaScrollPane.setVisible(false);
		profilScrollPane.setVisible(false);
		psyScrollPane.setVisible(false);
		transScrollPane.setVisible(false);
		switch(filterTabbedPane.getSelectedIndex()) {
			case 0:
				resumeLoadingLabel.setVisible(true);
				break;
			case 1:
				nivLoadingLabel.setVisible(true);
				break;
			case 2:
				npaLoadingLabel.setVisible(true);
				break;
			case 3:
				profilLoadingLabel.setVisible(true);
				break;
			case 4:
				psyLoadingLabel.setVisible(true);
				break;
			case 5:
				transLoadingLabel.setVisible(true);
				break;
		};
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
		executor.schedule(new Runnable() {
			public void run() {
				String maj_equipe = "";
				try {
					Connection connection = DriverManager.getConnection("jdbc:mysql://" + MainFrame.prop.getProperty("mysql.host") + ":" + MainFrame.prop.getProperty("mysql.port") + "/" + MainFrame.prop.getProperty("mysql.database"), 
																		MainFrame.prop.getProperty("mysql.username"),
																		MainFrame.prop.getProperty("mysql.password"));
					Statement statement = connection.createStatement();
					ResultSet result = statement.executeQuery("SELECT maj_equipe FROM mise_a_jour WHERE equipe_id = " +
							  								  "(SELECT id FROM equipe WHERE manager = '" + MainFrame.username + "')");
					if(result.next()) {
						maj_equipe = result.getString("maj_equipe");
					};
					connection.close();
				} catch (SQLException e) {
					clubLoadingLabel.setVisible(false);
					clubDataErrorLabel.setVisible(true);
					switch(filterTabbedPane.getSelectedIndex()) {
						case 0:
							resumeLoadingLabel.setVisible(false);
							resumeEmptyLabel.setVisible(true);
							break;
						case 1:
							nivLoadingLabel.setVisible(false);
							nivEmptyLabel.setVisible(true);
							break;
						case 2:
							npaLoadingLabel.setVisible(false);
							npaEmptyLabel.setVisible(true);
							break;
						case 3:
							profilLoadingLabel.setVisible(false);
							profilEmptyLabel.setVisible(true);
							break;
						case 4:
							psyLoadingLabel.setVisible(false);
							psyEmptyLabel.setVisible(true);
							break;
						case 5:
							transLoadingLabel.setVisible(false);
							transEmptyLabel.setVisible(true);
							break;
					};
					return;
				};
				
				if(lastUpdate.isEmpty()) {
					try {
						Connection connection = DriverManager.getConnection("jdbc:mysql://" + MainFrame.prop.getProperty("mysql.host") + ":" + MainFrame.prop.getProperty("mysql.port") + "/" + MainFrame.prop.getProperty("mysql.database"), 
																			MainFrame.prop.getProperty("mysql.username"),
																			MainFrame.prop.getProperty("mysql.password"));
						Statement statement = connection.createStatement();
						ResultSet result = statement.executeQuery("SELECT * FROM equipe WHERE manager = '" + MainFrame.username + "'");
						
						if(result.next()) {
							managerNameLabel.setText(result.getString("manager"));
							clubNameLabel.setText(result.getString("nom"));
							joueursTextLabel.setText(result.getString("joueurs") + " (" + result.getString("joueurs_pro") + "/" + result.getString("joueurs_centre") + ")");
							npaTextLabel.setText(result.getString("npa"));
							npvTextLabel.setText(result.getString("npv"));
							ageTextLabel.setText(result.getString("age"));
							experienceTextLabel.setText("+" + result.getString("experience"));
							pressionTextLabel.setText("+" + result.getString("pression"));
							flairTextLabel.setText("+" + result.getString("flair"));
						};
						connection.close();
						clubLoadingLabel.setVisible(false);
					} catch (SQLException e) {
						clubLoadingLabel.setVisible(false);
						clubDataErrorLabel.setVisible(true);
						switch(filterTabbedPane.getSelectedIndex()) {
							case 0:
								resumeLoadingLabel.setVisible(false);
								resumeEmptyLabel.setVisible(true);
								break;
							case 1:
								nivLoadingLabel.setVisible(false);
								nivEmptyLabel.setVisible(true);
								break;
							case 2:
								npaLoadingLabel.setVisible(false);
								npaEmptyLabel.setVisible(true);
								break;
							case 3:
								profilLoadingLabel.setVisible(false);
								profilEmptyLabel.setVisible(true);
								break;
							case 4:
								psyLoadingLabel.setVisible(false);
								psyEmptyLabel.setVisible(true);
								break;
							case 5:
								transLoadingLabel.setVisible(false);
								transEmptyLabel.setVisible(true);
								break;
						};
						return;
					}
					lastUpdate = maj_equipe;
				} else if(lastUpdate.equalsIgnoreCase(maj_equipe)) {
					clubLoadingLabel.setVisible(false);
				} else {
					try {
						Connection connection = DriverManager.getConnection("jdbc:mysql://" + MainFrame.prop.getProperty("mysql.host") + ":" + MainFrame.prop.getProperty("mysql.port") + "/" + MainFrame.prop.getProperty("mysql.database"), 
																			MainFrame.prop.getProperty("mysql.username"),
																			MainFrame.prop.getProperty("mysql.password"));
						Statement statement = connection.createStatement();
						ResultSet result = statement.executeQuery("SELECT * FROM equipe WHERE manager = '" + MainFrame.username + "'");
						
						if(result.next()) {
							managerNameLabel.setText(result.getString("manager"));
							clubNameLabel.setText(result.getString("nom"));
							joueursTextLabel.setText(result.getString("joueurs") + " (" + result.getString("joueurs_pro") + "/" + result.getString("joueurs_centre") + ")");
							npaTextLabel.setText(result.getString("npa"));
							npvTextLabel.setText(result.getString("npv"));
							ageTextLabel.setText(result.getString("age"));
							experienceTextLabel.setText("+" + result.getString("experience"));
							pressionTextLabel.setText("+" + result.getString("pression"));
							flairTextLabel.setText("+" + result.getString("flair"));
						};
						connection.close();
						clubLoadingLabel.setVisible(false);
					} catch (SQLException e) {
						clubLoadingLabel.setVisible(false);
						clubDataErrorLabel.setVisible(true);
						switch(filterTabbedPane.getSelectedIndex()) {
							case 0:
								resumeLoadingLabel.setVisible(false);
								resumeEmptyLabel.setVisible(true);
								break;
							case 1:
								nivLoadingLabel.setVisible(false);
								nivEmptyLabel.setVisible(true);
								break;
							case 2:
								npaLoadingLabel.setVisible(false);
								npaEmptyLabel.setVisible(true);
								break;
							case 3:
								profilLoadingLabel.setVisible(false);
								profilEmptyLabel.setVisible(true);
								break;
							case 4:
								psyLoadingLabel.setVisible(false);
								psyEmptyLabel.setVisible(true);
								break;
							case 5:
								transLoadingLabel.setVisible(false);
								transEmptyLabel.setVisible(true);
								break;
						};
						return;
					}
					lastUpdate = maj_equipe;
				};
				switch(filterTabbedPane.getSelectedIndex()) {
	        		case 0:
	        			if(updateTable(resumeTable)) {
	    					resumeScrollPane.setVisible(true);
	    				} else {
	    					resumeEmptyLabel.setVisible(true);
	    				};
	    				resumeLoadingLabel.setVisible(false);
	        			break;
	        		case 1:
	        			if(updateTable(nivTable)) {
	    					nivScrollPane.setVisible(true);
	    				} else {
	    					nivEmptyLabel.setVisible(true);
	    				};
	    				nivLoadingLabel.setVisible(false);
	        			break;
	        		case 2:
	        			if(updateTable(npaTable)) {
	    					npaScrollPane.setVisible(true);
	    				} else {
	    					npaEmptyLabel.setVisible(true);
	    				};
	    				npaLoadingLabel.setVisible(false);
	        			break;
	        		case 3:
	        			if(updateTable(profilTable)) {
	    					profilScrollPane.setVisible(true);
	    				} else {
	    					profilEmptyLabel.setVisible(true);
	    				};
	    				profilLoadingLabel.setVisible(false);
	        			break;
	        		case 4:
	        			if(updateTable(psyTable)) {
	    					psyScrollPane.setVisible(true);
	    				} else {
	    					psyEmptyLabel.setVisible(true);
	    				};
	    				psyLoadingLabel.setVisible(false);
	        			break;
	        		case 5:
	        			if(updateTable(transTable)) {
	    					transScrollPane.setVisible(true);
	    				} else {
	    					transEmptyLabel.setVisible(true);
	    				};
	    				transLoadingLabel.setVisible(false);
	        			break;
	        	};
	        	refreshLabel.setVisible(true);
			}
		}, 1000, TimeUnit.MILLISECONDS);
	}
	
	public static boolean updateTable(JTable table) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
	    while(model.getRowCount() > 0) {
	        model.removeRow(0);
	    }
	    try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://" + MainFrame.prop.getProperty("mysql.host") + ":" + MainFrame.prop.getProperty("mysql.port") + "/" + MainFrame.prop.getProperty("mysql.database"), 
																MainFrame.prop.getProperty("mysql.username"),
																MainFrame.prop.getProperty("mysql.password"));
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM joueur WHERE equipe_id = " +
					  								  "(SELECT id FROM equipe WHERE manager = '" + MainFrame.username + "')");
			while(result.next()) {
				switch(table.getName()) {
					case "resumeTable":
						model.addRow(new Object[]{result.getString("numero"), result.getString("prenom"), result.getString("nom"), result.getString("position_actuelle").equalsIgnoreCase(result.getString("position_origine")) ? result.getString("position_actuelle") : result.getString("position_actuelle") + " (" + result.getString("position_origine") + ")", result.getString("age"), result.getString("npa"), result.getString("npv"), result.getString("position_origine").equalsIgnoreCase("GB") ? result.getString("reflexe") : "-", result.getString("position_origine").equalsIgnoreCase("GB") ? result.getString("prise_balle") : "-", result.getString("position_origine").equalsIgnoreCase("GB") ? result.getString("detente") : "-", result.getString("position_origine").equalsIgnoreCase("GB") ? "-" : result.getString("tete"), result.getString("position_origine").equalsIgnoreCase("GB") ? "-" : result.getString("tacle"), result.getString("passe"), result.getString("tir"), result.getString("dribble"), result.getString("controle"), result.getString("vitesse"), result.getString("endurance"), result.getString("position_origine").equalsIgnoreCase("GB") ? "-" : result.getString("physique")});
						break;
					case "nivTable":
						model.addRow(new Object[]{result.getString("numero"), result.getString("prenom"), result.getString("nom"), result.getString("position_actuelle"), result.getString("age"), result.getString("npa"), result.getString("npv"), result.getString("niveau_tete"), result.getString("niveau_frappe"), result.getString("niveau_controle"), result.getString("niveau_dribble"), result.getString("niveau_recuperation"), result.getString("niveau_passe"), result.getString("niveau_gardien"), result.getString("niveau_endurance"), result.getString("qualite_psychologique")});
						break;
					case "npaTable":
						model.addRow(new Object[]{result.getString("numero"), result.getString("prenom"), result.getString("nom"), result.getString("position_actuelle"), result.getString("age"), result.getString("npa"), result.getString("npv"), result.getString("npa_gb"), result.getString("npa_df"), result.getString("npa_lb"), result.getString("npa_md"), result.getString("npa_mt"), result.getString("npa_mo"), result.getString("npa_at"), result.getString("npa_bc")});
						break;
					case "profilTable":
						model.addRow(new Object[]{result.getString("numero"), result.getString("prenom"), result.getString("nom"), result.getString("position_actuelle"), result.getString("age"), result.getString("npa"), result.getString("npv"), result.getString("progression_potentiel"), result.getString("progression_vitesse"), result.getString("progression_rigueur"), result.getString("progression_rigularite")});
						break;
					case "psyTable":
						model.addRow(new Object[]{result.getString("numero"), result.getString("prenom"), result.getString("nom"), result.getString("position_actuelle"), result.getString("age"), result.getString("npa"), result.getString("npv"), result.getString("pression"), result.getString("physique"), result.getString("forme"), result.getString("mental"), result.getString("flair"), result.getString("endurance"), result.getString("experience"), result.getString("statut").equalsIgnoreCase("") ? "-" : result.getString("statut"), result.getString("capitaine")});
						break;
					case "transTable":
						model.addRow(new Object[]{result.getString("numero"), result.getString("prenom"), result.getString("nom"), result.getString("position_actuelle"), result.getString("age"), result.getString("npa"), result.getString("npv"), result.getString("valeur"), result.getString("salaire"), result.getString("cote").equalsIgnoreCase("0") ? "-" : result.getString("cote"), result.getString("condition_arrivee"), result.getString("date_arrivee")});
						break;
				}
			};
			connection.close();
			return true;
		} catch (SQLException exc) {
			return false;
		}
	}
}