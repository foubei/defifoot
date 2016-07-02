import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

import org.json.simple.JSONObject;

/**
 * Filter details
 */
public class Filter {
	public static JPanel filterDetailsPanel = new JPanel(); // The filter details' panel
	JPanel getMyDataPanel = new JPanel();
	static JPanel getMyDataStatusPanel = new JPanel();
	JButton getMyDataButton = new JButton("Obtenir"); // Get club's data button
	int step = 0; // The step completed when getting the club's data
	static java.util.Date lastUpdateTime = new java.util.Date(); // The last update time of my club's data
	JSONObject clubData = new JSONObject(); // Array contains the club's data
	JLabel getMyClubDataIconLabel = new JLabel("");
	JLabel getMyClubDataLabel = new JLabel("Ton equipe");
	JLabel getMyClubDataStatusLabel = new JLabel("");
	public static JProgressBar getMyClubDataProgressBar = new JProgressBar(); // Increase get my club's data progress bar.
	JLabel getMyPlayersDataIconLabel = new JLabel("");
	JLabel getMyPlayersDataLabel = new JLabel("Tes joueurs");
	JLabel getMyPlayersDataStatusLabel = new JLabel("");
	JProgressBar getMyPlayersDataProgressBar = new JProgressBar();
	JLabel getMyTrainingDataIconLabel = new JLabel("");
	JLabel getMyTrainingDataLabel = new JLabel("Tes entrainements");
	JLabel getMyTrainingDataStatusLabel = new JLabel("");
	JProgressBar getMyTrainingDataProgressBar = new JProgressBar();
	static JLabel lastUpdateDate = new JLabel("");
	
	public Filter() {
		main();
	}
	
	private void main() {
		filterDetailsPanel.setOpaque(false);
		filterDetailsPanel.setBounds(0, 0, 746, 429);
		filterDetailsPanel.setLayout(null);
		filterDetailsPanel.setVisible(false);
		MainFrame.bodyPanel.add(filterDetailsPanel);
		
		
		/****************************** Get my data *******************************/
		getMyDataPanel.setOpaque(false);
		getMyDataPanel.setBounds(0, 0, 746, 65);
		getMyDataPanel.setLayout(null);
		filterDetailsPanel.add(getMyDataPanel);
		
		getMyDataButton.setBorderPainted(false);
		getMyDataButton.setFocusPainted(false);
		getMyDataButton.setFont(MainFrame.buttonFont);
		getMyDataButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		getMyDataButton.setBackground(Color.decode(MainFrame.prop.getProperty("bleu_de_france")));
		getMyDataButton.setForeground(Color.decode(MainFrame.prop.getProperty("white")));
		getMyDataButton.setIconTextGap(7);
		getMyDataButton.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/btn/download.png")).getImage()));
		getMyDataButton.setBounds(15, 15, 150, 35);
		getMyDataPanel.add(getMyDataButton);
		
		JLabel filterTitleLabel = new JLabel("Filtrer la meilleure composition");
		filterTitleLabel.setFont(new Font("Trebuchet MS", Font.PLAIN, 18));
		filterTitleLabel.setBounds(247, 389, 252, 25);
		filterDetailsPanel.add(filterTitleLabel);
		
		getMyDataStatusPanel.setOpaque(false);
		getMyDataStatusPanel.setBounds(200, 15, 510, 35);
		getMyDataStatusPanel.setLayout(null);
		getMyDataStatusPanel.setVisible(false);
		getMyDataPanel.add(getMyDataStatusPanel);
		
		lastUpdateDate.setBounds(200, 15, 510, 35);
		lastUpdateDate.setForeground(Color.decode(MainFrame.prop.getProperty("onyx")));
		lastUpdateDate.setFont(MainFrame.mediumFont);
		getMyDataPanel.add(lastUpdateDate);
		
		getMyDataButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent me) { // Hover effects
				if (me.getComponent().isEnabled()) {
					((AbstractButton) me.getComponent()).setBorderPainted(true);
					((JComponent) me.getComponent()).setBorder(BorderFactory.createLineBorder(Color.decode(MainFrame.prop.getProperty("bleu_de_france"))));
					((AbstractButton) me.getComponent()).setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/btn/download_reverse.png")).getImage()));
					me.getComponent().setBackground(Color.decode(MainFrame.prop.getProperty("white")));
					me.getComponent().setForeground(Color.decode(MainFrame.prop.getProperty("bleu_de_france")));
				}
			}

			@Override
			public void mouseExited(MouseEvent me) { // Blur effects
				if (me.getComponent().isEnabled()) {
					((AbstractButton) me.getComponent()).setBorderPainted(false);
					((AbstractButton) me.getComponent()).setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/btn/download.png")).getImage()));
					me.getComponent().setBackground(Color.decode(MainFrame.prop.getProperty("bleu_de_france")));
					me.getComponent().setForeground(Color.decode(MainFrame.prop.getProperty("white")));
				}
			}

			@Override
			public void mouseClicked(MouseEvent me) { // Active effects
				if (me.getComponent().isEnabled()) {
					getMyData();
				}
			}
		});
		/**************************** End Get my data *****************************/
		
		JPanel formationPanel = new JPanel();
		formationPanel.setLayout(null);
		formationPanel.setOpaque(false);
		formationPanel.setBounds(15, 65, 716, 311);
		filterDetailsPanel.add(formationPanel);
		
		String[] tacticsNames = { "---", "4-3-3", "4-4-2", "4-5-1", "3-5-2", "3-4-4", "5-4-1", "5-3-2" };
		JComboBox<Object> strategyComboBox = new JComboBox<Object>(tacticsNames);
		strategyComboBox.setOpaque(true);
		strategyComboBox.setFocusable(false);
		strategyComboBox.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		strategyComboBox.setBackground(Color.decode(MainFrame.prop.getProperty("white")));
		strategyComboBox.setForeground(Color.decode(MainFrame.prop.getProperty("onyx")));
		strategyComboBox.setFont(MainFrame.buttonFont);
		strategyComboBox.setBounds(308, 0, 100, 25);
		formationPanel.add(strategyComboBox);
		
		JLabel stadiumLabel = new JLabel("");
		stadiumLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/lineup.png")).getImage()));
		stadiumLabel.setBounds(145, 40, 426, 271);
		formationPanel.add(stadiumLabel);
	}
	
	public static void updateStatus() { // Get latest update of my club's data if there is
		getMyDataStatusPanel.setVisible(false);
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://" + MainFrame.prop.getProperty("mysql.host") + ":" + MainFrame.prop.getProperty("mysql.port") + "/" + MainFrame.prop.getProperty("mysql.database"), 
																MainFrame.prop.getProperty("mysql.username"),
																MainFrame.prop.getProperty("mysql.password"));
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery("SELECT maj_tout FROM mise_a_jour WHERE equipe_id = " +
					  						"(SELECT id FROM equipe WHERE manager = '" + MainFrame.username + "')");
			if(result.next()) {
				result.getString("maj_tout");
				if (!result.wasNull()) {
					lastUpdateDate.setText("Derniere mise a jour le: " + result.getString("maj_tout"));
					lastUpdateDate.setVisible(true);
				}
			} else {
				lastUpdateDate.setVisible(false);
			};
			connection.close();
		} catch (SQLException e) {
			lastUpdateDate.setText("Echec du MySQL");
			lastUpdateDate.setVisible(true);
		}
	}
	
	private void getMyData() { // Get my club's data (Club + players + training)
		Header.navBarPanel.setEnabled(false);
		getMyDataStatusPanel.removeAll();
		getMyDataStatusPanel.updateUI();
		lastUpdateDate.setVisible(false);
		
		getMyDataButton.setEnabled(false);
		
		getMyClubDataIconLabel.setBounds(0, 0, 24, 24);
		getMyDataStatusPanel.add(getMyClubDataIconLabel);

		getMyClubDataLabel.setBounds(34, 0, 126, 24);
		getMyClubDataLabel.setFont(MainFrame.mediumFont);
		getMyDataStatusPanel.add(getMyClubDataLabel);

		getMyClubDataStatusLabel.setBounds(0, 23, 160, 12);
		getMyClubDataStatusLabel.setHorizontalAlignment(SwingConstants.CENTER);
		getMyClubDataStatusLabel.setFont(MainFrame.smallFont);
		getMyDataStatusPanel.add(getMyClubDataStatusLabel);
		
		getMyClubDataProgressBar.setBounds(0, 30, 160, 5);
		getMyClubDataProgressBar.setFont(MainFrame.smallFont);
		getMyDataStatusPanel.add(getMyClubDataProgressBar);
		
		getMyPlayersDataIconLabel.setBounds(175, 0, 24, 24);
		getMyDataStatusPanel.add(getMyPlayersDataIconLabel);
		
		getMyPlayersDataLabel.setBounds(209, 0, 126, 24);
		getMyPlayersDataLabel.setFont(MainFrame.mediumFont);
		getMyDataStatusPanel.add(getMyPlayersDataLabel);
		
		getMyPlayersDataStatusLabel.setBounds(175, 23, 160, 12);
		getMyPlayersDataStatusLabel.setHorizontalAlignment(SwingConstants.CENTER);
		getMyPlayersDataStatusLabel.setFont(MainFrame.smallFont);
		getMyDataStatusPanel.add(getMyPlayersDataStatusLabel);
		
		getMyPlayersDataProgressBar.setBounds(175, 30, 160, 5);
		getMyPlayersDataProgressBar.setFont(MainFrame.smallFont);
		getMyDataStatusPanel.add(getMyPlayersDataProgressBar);
		
		getMyTrainingDataIconLabel.setBounds(350, 0, 24, 24);
		getMyDataStatusPanel.add(getMyTrainingDataIconLabel);
		
		getMyTrainingDataLabel.setBounds(384, 0, 126, 24);
		getMyTrainingDataLabel.setFont(MainFrame.mediumFont);
		getMyDataStatusPanel.add(getMyTrainingDataLabel);
		
		getMyTrainingDataStatusLabel.setBounds(350, 23, 160, 12);
		getMyTrainingDataStatusLabel.setHorizontalAlignment(SwingConstants.CENTER);
		getMyTrainingDataStatusLabel.setFont(MainFrame.smallFont);
		getMyDataStatusPanel.add(getMyTrainingDataStatusLabel);

		getMyTrainingDataProgressBar.setBounds(350, 30, 160, 5);
		getMyTrainingDataProgressBar.setFont(MainFrame.smallFont);
		getMyDataStatusPanel.add(getMyTrainingDataProgressBar);
		
		switch(step) {
			case 0:
				getMyClubDataIconLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/icons/club.png")).getImage()));
				getMyClubDataLabel.setForeground(Color.decode(MainFrame.prop.getProperty("dark_gray")));
				getMyClubDataStatusLabel.setText("Waiting...");
				getMyClubDataStatusLabel.setForeground(Color.decode(MainFrame.prop.getProperty("dark_gray")));
				getMyClubDataStatusLabel.setVisible(true);
				getMyClubDataProgressBar.setForeground(Color.decode(MainFrame.prop.getProperty("onyx")));
				getMyClubDataProgressBar.setBackground(Color.decode(MainFrame.prop.getProperty("dark_gray")));
				getMyClubDataProgressBar.setBorder(new LineBorder(Color.decode(MainFrame.prop.getProperty("onyx"))));
				getMyClubDataProgressBar.setValue(0);
				getMyClubDataProgressBar.setVisible(false);
				getMyPlayersDataIconLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/icons/players.png")).getImage()));
				getMyPlayersDataLabel.setForeground(Color.decode(MainFrame.prop.getProperty("dark_gray")));
				getMyPlayersDataStatusLabel.setText("Waiting...");
				getMyPlayersDataStatusLabel.setForeground(Color.decode(MainFrame.prop.getProperty("dark_gray")));
				getMyPlayersDataStatusLabel.setVisible(true);
				getMyPlayersDataProgressBar.setForeground(Color.decode(MainFrame.prop.getProperty("onyx")));
				getMyPlayersDataProgressBar.setBackground(Color.decode(MainFrame.prop.getProperty("dark_gray")));
				getMyPlayersDataProgressBar.setBorder(new LineBorder(Color.decode(MainFrame.prop.getProperty("onyx"))));
				getMyPlayersDataProgressBar.setVisible(false);
				getMyTrainingDataIconLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/icons/training.png")).getImage()));
				getMyTrainingDataLabel.setForeground(Color.decode(MainFrame.prop.getProperty("dark_gray")));
				getMyTrainingDataStatusLabel.setText("Waiting...");
				getMyTrainingDataStatusLabel.setForeground(Color.decode(MainFrame.prop.getProperty("dark_gray")));
				getMyTrainingDataStatusLabel.setVisible(true);
				getMyTrainingDataProgressBar.setForeground(Color.decode(MainFrame.prop.getProperty("onyx")));
				getMyTrainingDataProgressBar.setBackground(Color.decode(MainFrame.prop.getProperty("dark_gray")));
				getMyTrainingDataProgressBar.setBorder(new LineBorder(Color.decode(MainFrame.prop.getProperty("onyx"))));
				getMyTrainingDataProgressBar.setVisible(false);
				break;
			case 1:
				getMyPlayersDataIconLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/icons/players.png")).getImage()));
				getMyPlayersDataLabel.setForeground(Color.decode(MainFrame.prop.getProperty("dark_gray")));
				getMyPlayersDataStatusLabel.setText("Waiting...");
				getMyPlayersDataStatusLabel.setForeground(Color.decode(MainFrame.prop.getProperty("dark_gray")));
				getMyPlayersDataStatusLabel.setVisible(true);
				getMyPlayersDataProgressBar.setForeground(Color.decode(MainFrame.prop.getProperty("onyx")));
				getMyPlayersDataProgressBar.setBackground(Color.decode(MainFrame.prop.getProperty("dark_gray")));
				getMyPlayersDataProgressBar.setBorder(new LineBorder(Color.decode(MainFrame.prop.getProperty("onyx"))));
				getMyPlayersDataProgressBar.setValue(0);
				getMyPlayersDataProgressBar.setVisible(false);
				getMyTrainingDataStatusLabel.setText("Waiting...");
				getMyTrainingDataIconLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/icons/training.png")).getImage()));
				getMyTrainingDataLabel.setForeground(Color.decode(MainFrame.prop.getProperty("dark_gray")));
				getMyTrainingDataStatusLabel.setText("Waiting...");
				getMyTrainingDataStatusLabel.setForeground(Color.decode(MainFrame.prop.getProperty("dark_gray")));
				getMyTrainingDataProgressBar.setForeground(Color.decode(MainFrame.prop.getProperty("onyx")));
				getMyTrainingDataProgressBar.setVisible(false);
				break;
			case 2:
				getMyTrainingDataIconLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/icons/training.png")).getImage()));
				getMyTrainingDataLabel.setForeground(Color.decode(MainFrame.prop.getProperty("dark_gray")));
				getMyTrainingDataStatusLabel.setText("Waiting...");
				getMyTrainingDataStatusLabel.setForeground(Color.decode(MainFrame.prop.getProperty("dark_gray")));
				getMyTrainingDataStatusLabel.setVisible(true);
				getMyTrainingDataProgressBar.setForeground(Color.decode(MainFrame.prop.getProperty("onyx")));
				getMyTrainingDataProgressBar.setBackground(Color.decode(MainFrame.prop.getProperty("dark_gray")));
				getMyTrainingDataProgressBar.setBorder(new LineBorder(Color.decode(MainFrame.prop.getProperty("onyx"))));
				getMyTrainingDataProgressBar.setValue(0);
				getMyTrainingDataProgressBar.setVisible(false);
				break;
		}
		
		getMyDataStatusPanel.setVisible(true);
		getMyDataStatusPanel.repaint();
		
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
		executor.schedule(new Runnable() {
			public void run() {
				switch(step) {
					case 0:
						// 1. Get the club's data
						getClub();
						break;
					case 1:
						// 2. Get the players' data
						getPlayers();
						break;
					case 2:
						// 3. Get the training's data
						getTraining();
						break;
				};
				
				getMyDataButton.setBackground(Color.decode(MainFrame.prop.getProperty("bleu_de_france")));
				getMyDataButton.setForeground(Color.decode(MainFrame.prop.getProperty("white")));
				getMyDataButton.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/btn/download.png")).getImage()));
				getMyDataButton.setEnabled(true);
				Header.navBarPanel.setEnabled(true);
			}
		}, 1000, TimeUnit.MILLISECONDS);
	}
	
	public void getClub() {
		new Club();
		getMyClubDataIconLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/icons/club_progress.png")).getImage()));
		getMyClubDataLabel.setForeground(Color.decode(MainFrame.prop.getProperty("onyx")));
		getMyClubDataStatusLabel.setVisible(false);
		getMyClubDataProgressBar.setVisible(true);
		clubData = Club.getMine();
		if(clubData.get("Error").equals(false)) { // Successfully got the club's data
			getMyClubDataProgressBar.setValue(100);
			
			try {
				Connection connection = DriverManager.getConnection("jdbc:mysql://" + MainFrame.prop.getProperty("mysql.host") + ":" + MainFrame.prop.getProperty("mysql.port") + "/" + MainFrame.prop.getProperty("mysql.database"),
										MainFrame.prop.getProperty("mysql.username"),
										MainFrame.prop.getProperty("mysql.password"));
				Statement statement = connection.createStatement();
				ResultSet result = statement.executeQuery("SELECT id FROM equipe WHERE vrai_id = " +
														  clubData.get("vrai_id").toString() + "");
				if(result.next()) {
					java.util.Date currentTime = new java.util.Date();
					String sql = "UPDATE equipe SET " +
							 	 "nom = '" + clubData.get("nom").toString() + "', " +
							 	 "manager = '" + clubData.get("manager").toString() + "', " +
							 	 "joueurs = " + clubData.get("joueurs") + ", " +
							 	 "joueurs_pro = " + clubData.get("joueurs_pro") + ", " +
							 	 "joueurs_centre = " + clubData.get("joueurs_centre") + ", " +
							 	 "age = " + clubData.get("age") + ", " +
							 	 "npa = " + clubData.get("npa") + ", " +
							 	 "npv = " + clubData.get("npv") + ", " +
							 	 "experience = " + clubData.get("experience") + ", " +
							 	 "pression = " + clubData.get("pression") + ", " +
							 	 "flair = " + clubData.get("flair") + ", " +
							 	 "date = '" + new java.sql.Timestamp(currentTime.getTime()) + "' " +
							 	 "WHERE vrai_id = " + clubData.get("vrai_id");
					statement.executeUpdate(sql);
				} else {
					String sql = "INSERT INTO equipe " +
						 	  	 "(vrai_id, nom, manager, joueurs, joueurs_pro, joueurs_centre, age, npa, npv, experience, pression, flair)" +
						 	  	 "VALUES (" + clubData.get("vrai_id") + ", '" + clubData.get("nom").toString() + "', '" + clubData.get("manager").toString() + "', " + clubData.get("joueurs") + ", " + clubData.get("joueurs_pro") + ", " + clubData.get("joueurs_centre") + ", " + clubData.get("age") + ", " + clubData.get("npa") + ", " + clubData.get("npv") + ", " + clubData.get("experience") + ", " + clubData.get("pression") + ", " + clubData.get("flair") + ")";
					statement.executeUpdate(sql);
				}
				getMyClubDataIconLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/icons/club_complete.png")).getImage()));
				getMyClubDataLabel.setForeground(Color.decode(MainFrame.prop.getProperty("lime_green")));
				getMyClubDataProgressBar.setForeground(Color.decode(MainFrame.prop.getProperty("lime_green")));
				getMyClubDataProgressBar.setBackground(Color.decode(MainFrame.prop.getProperty("lime_green")));
				getMyClubDataProgressBar.setBorder(new LineBorder(Color.decode(MainFrame.prop.getProperty("lime_green"))));
				step++;
				
				// Update the mise_a_jour table
				java.util.Date currentTime = new java.util.Date();
				result = statement.executeQuery("SELECT id FROM mise_a_jour WHERE equipe_id = " +
						  						"(SELECT id FROM equipe WHERE vrai_id = " + clubData.get("vrai_id") + ")");
				if(result.next()) {
					String sql = "UPDATE mise_a_jour SET " +
							 	 "maj_equipe = '" + new java.sql.Timestamp(currentTime.getTime()) + "' " +
							 	 "WHERE equipe_id = (SELECT id FROM equipe WHERE vrai_id = " + clubData.get("vrai_id") + ")";
					statement.executeUpdate(sql);
				} else {
					String sql = "INSERT INTO mise_a_jour " +
						 	  	 "(equipe_id, maj_equipe)" +
						 	  	 "VALUES ((SELECT id FROM equipe WHERE vrai_id = " + clubData.get("vrai_id") + "), '" + new java.sql.Timestamp(currentTime.getTime()) + "')";
					statement.executeUpdate(sql);
				};
				connection.close();
				getPlayers();
			} catch (SQLException e) {
				getMyClubDataIconLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/icons/club_error.png")).getImage()));
				getMyClubDataLabel.setForeground(Color.decode(MainFrame.prop.getProperty("coral_red")));
				getMyClubDataStatusLabel.setForeground(Color.decode(MainFrame.prop.getProperty("coral_red")));
				getMyClubDataStatusLabel.setText("Echec du MySQL");
				getMyClubDataStatusLabel.setVisible(true);
				getMyClubDataProgressBar.setVisible(false);
				getMyPlayersDataStatusLabel.setText("Annulee");
				getMyTrainingDataStatusLabel.setText("Annulee");
			}
		} else { // Failed to get the club's data
			getMyClubDataIconLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/icons/club_error.png")).getImage()));
			getMyClubDataLabel.setForeground(Color.decode(MainFrame.prop.getProperty("coral_red")));
			getMyClubDataStatusLabel.setForeground(Color.decode(MainFrame.prop.getProperty("coral_red")));
			getMyClubDataStatusLabel.setText(clubData.get("ErrorMsg").toString());
			getMyClubDataStatusLabel.setVisible(true);
			getMyClubDataProgressBar.setVisible(false);
			getMyPlayersDataStatusLabel.setText("Annulee");
			getMyTrainingDataStatusLabel.setText("Annulee");
		}
	};
	
	public void getPlayers() {
		getMyPlayersDataIconLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/icons/players_progress.png")).getImage()));
		getMyPlayersDataLabel.setForeground(Color.decode(MainFrame.prop.getProperty("onyx")));
		getMyPlayersDataStatusLabel.setVisible(false);
		getMyPlayersDataProgressBar.setVisible(true);
		for(int i = 0; i < Club.playersLinks.length; i++) {
			JSONObject playerData = Player.get(Club.playersLinks[i]);
			System.out.println(playerData);
			if(playerData.get("Error").equals(false)) {
				try {
					Connection connection = DriverManager.getConnection("jdbc:mysql://" + MainFrame.prop.getProperty("mysql.host") + ":" + MainFrame.prop.getProperty("mysql.port") + "/" + MainFrame.prop.getProperty("mysql.database"), 
							 MainFrame.prop.getProperty("mysql.username"),
							 MainFrame.prop.getProperty("mysql.password"));
					Statement statement = connection.createStatement();
					ResultSet result = statement.executeQuery("SELECT id FROM joueur WHERE vrai_id = " +
										 					  playerData.get("vrai_id").toString() + "");
					if(result.next()) {
						java.util.Date currentTime = new java.util.Date();
						String sql = "UPDATE joueur SET " +
									 "prenom = '" +playerData.get("prenom") + "', " +
									 "nom = '" + playerData.get("nom") + "', " +
									 "age = " + playerData.get("age") + ", " +
									 "numero = " + playerData.get("numero") + ", " +
									 "npa = " + playerData.get("npa") + ", " +
									 "npv = " + playerData.get("npv") + ", " +
									 "position_actuelle = '" + playerData.get("position_actuelle") + "', " +
									 "position_origine = '" + playerData.get("position_origine") + "', " +
									 "equipe_id = " + playerData.get("equipe_id") + ", " +
									 "condition_arrivee = '" + playerData.get("condition_arrivee") + "', " +
									 "date_arrivee = '" + playerData.get("date_arrivee") + "', " +
									 "reflexe = " + playerData.get("reflexe") + ", " +
									 "prise_balle = " + playerData.get("prise_balle") + ", " +
									 "detente = " + playerData.get("detente") + ", " +
									 "tete = " + playerData.get("tete") + ", " +
									 "tacle = " + playerData.get("tacle") + ", " +
									 "passe = " + playerData.get("passe") + ", " +
									 "tir = " + playerData.get("tir") + ", " +
									 "dribble = " + playerData.get("dribble") + ", " +
									 "controle = " + playerData.get("controle") + ", " +
									 "vitesse = " + playerData.get("vitesse") + ", " +
									 "endurance = " + playerData.get("endurance") + ", " +
									 "physique = " + playerData.get("physique") + ", " +
									 "forme = " + playerData.get("forme") + ", " +
									 "flair = " + playerData.get("flair") + ", " +
									 "pression = " + playerData.get("pression") + ", " +
									 "mental = " + playerData.get("mental") + ", " +
									 "experience = " + playerData.get("experience") + ", " +
									 "qualite_psychologique = " + playerData.get("qualite_psychologique") + ", " +
									 "niveau_tete = " + playerData.get("niveau_tete") + ", " +
									 "niveau_frappe = " + playerData.get("niveau_frappe") + ", " +
									 "niveau_controle = " + playerData.get("niveau_controle") + ", " +
									 "niveau_dribble = " + playerData.get("niveau_dribble") + ", " +
									 "niveau_recuperation = " + playerData.get("niveau_recuperation") + ", " +
									 "niveau_passe = " + playerData.get("niveau_passe") + ", " +
									 "niveau_endurance = " + playerData.get("niveau_endurance") + ", " +
									 "niveau_gardien = " + playerData.get("niveau_gardien") + ", " +
									 "progression_potentiel = " + playerData.get("progression_potentiel") + ", " +
									 "progression_vitesse = " + playerData.get("progression_vitesse") + ", " +
									 "progression_rigueur = " + playerData.get("progression_rigueur") + ", " +
									 "progression_rigularite = " + playerData.get("progression_rigularite") + ", " +
									 "npa_gb = " + playerData.get("npa_gb") + ", " +
									 "npa_df = " + playerData.get("npa_df") + ", " +
									 "npa_lb = " + playerData.get("npa_lb") + ", " +
									 "npa_md = " + playerData.get("npa_md") + ", " +
									 "npa_mt = " + playerData.get("npa_mt") + ", " +
									 "npa_mo = " + playerData.get("npa_mo") + ", " +
									 "npa_at = " + playerData.get("npa_at") + ", " +
									 "npa_bc = " + playerData.get("npa_bc") + ", " +
									 "valeur = '" + playerData.get("valeur") + "', " +
									 "salaire = '" + playerData.get("salaire") + "', " +
									 "cote = " + playerData.get("cote") + ", " +
									 "statut = '" + playerData.get("statut") + "', " +
									 "capitaine = " + playerData.get("capitaine") + ", " +
									 "avatar = '" + playerData.get("avatar") + "', " +
									 "lien = '" + playerData.get("lien") + "', " +
									 "date_modification = '" + new java.sql.Timestamp(currentTime.getTime()) + "' " +
									 "WHERE vrai_id = " + playerData.get("vrai_id");
						statement.executeUpdate(sql);
					} else {
						java.util.Date currentTime = new java.util.Date();
						String sql = "INSERT INTO joueur " +
									 "(vrai_id, prenom, nom, age, numero, npa, npv, position_actuelle, position_origine, equipe_id, condition_arrivee, date_arrivee, " +
									 "reflexe, prise_balle, detente, tete, tacle, passe, tir, dribble, controle, vitesse, endurance, physique, forme, flair, pression, mental, experience, " +
									 "qualite_psychologique, niveau_tete, niveau_frappe, niveau_controle, niveau_dribble, niveau_recuperation, niveau_passe, niveau_endurance, niveau_gardien, " +
									 "progression_potentiel, progression_vitesse, progression_rigueur, progression_rigularite, " +
									 "npa_gb, npa_df, npa_lb, npa_md, npa_mt, npa_mo, npa_at, npa_bc, valeur, salaire, cote, statut, capitaine, avatar, lien, date_creation) " +
									 "VALUES (" + playerData.get("vrai_id") + ", '" + playerData.get("prenom").toString()+ "', '" + playerData.get("nom").toString() + "', " + playerData.get("age") + ", " + playerData.get("numero") + ", " + playerData.get("npa") + ", " + playerData.get("npv") + ", '" + playerData.get("position_actuelle") + "', '" + playerData.get("position_origine") + "', " + playerData.get("equipe_id") + ", '" + playerData.get("condition_arrivee") + "', '" + playerData.get("date_arrivee") + "', " +
									 playerData.get("reflexe") + ", " + playerData.get("prise_balle") + ", " + playerData.get("detente") + ", " + playerData.get("tete") + ", " + playerData.get("tacle") + ", " + playerData.get("passe") + ", " + playerData.get("tir") + ", " + playerData.get("dribble") + ", " + playerData.get("controle") + ", " + playerData.get("vitesse") + ", " + playerData.get("endurance") + ", " + playerData.get("physique") + ", " + playerData.get("forme") + ", " + playerData.get("flair") + ", " + playerData.get("pression") + ", " + playerData.get("mental") + ", " + playerData.get("experience") + ", " +
									 playerData.get("qualite_psychologique") + ", " + playerData.get("niveau_tete") + ", " + playerData.get("niveau_frappe") + ", " + playerData.get("niveau_controle") + ", " + playerData.get("niveau_dribble") + ", " + playerData.get("niveau_recuperation") + ", " + playerData.get("niveau_passe") + ", " + playerData.get("niveau_endurance") + ", " + playerData.get("niveau_gardien") + ", " +
									 playerData.get("progression_potentiel") + ", " + playerData.get("progression_vitesse") + ", " + playerData.get("progression_rigueur") + ", " + playerData.get("progression_rigularite") + ", " +
									 playerData.get("npa_gb") + ", " + playerData.get("npa_df") + ", " + playerData.get("npa_lb") + ", " + playerData.get("npa_md") + ", " + playerData.get("npa_mt") + ", " + playerData.get("npa_mo") + ", " + playerData.get("npa_at") + ", " + playerData.get("npa_bc") + ", '" + playerData.get("valeur") + "', '" + playerData.get("salaire") + "', " + playerData.get("cote") + ", '" + playerData.get("statut") + "', " + playerData.get("capitaine") + ", '" + playerData.get("avatar") + "', '" + playerData.get("lien") + "', '" + new java.sql.Timestamp(currentTime.getTime()) + "')";
						statement.executeUpdate(sql);
					};
					getMyPlayersDataProgressBar.setValue((i+1) * 100 / Club.playersLinks.length);
					if(i == Club.playersLinks.length - 1) {
						getMyPlayersDataIconLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/icons/players_complete.png")).getImage()));
						getMyPlayersDataLabel.setForeground(Color.decode(MainFrame.prop.getProperty("lime_green")));
						getMyPlayersDataProgressBar.setForeground(Color.decode(MainFrame.prop.getProperty("lime_green")));
						getMyPlayersDataProgressBar.setBackground(Color.decode(MainFrame.prop.getProperty("lime_green")));
						getMyPlayersDataProgressBar.setBorder(new LineBorder(Color.decode(MainFrame.prop.getProperty("lime_green"))));
						step++;
						
						// Update the mise_a_jour table
						java.util.Date currentTime = new java.util.Date();
						result = statement.executeQuery("SELECT id FROM mise_a_jour WHERE equipe_id = " +
								  						"(SELECT id FROM equipe WHERE vrai_id = " + clubData.get("vrai_id") + ")");
						if(result.next()) {
							String sql = "UPDATE mise_a_jour SET " +
									 	 "maj_joueur = '" + new java.sql.Timestamp(currentTime.getTime()) + "' " +
									 	 "WHERE equipe_id = (SELECT id FROM equipe WHERE vrai_id = " + clubData.get("vrai_id") + ")";
							statement.executeUpdate(sql);
						} else {
							String sql = "INSERT INTO mise_a_jour " +
								 	  	 "(equipe_id, maj_joueur)" +
								 	  	 "VALUES ((SELECT id FROM equipe WHERE vrai_id = " + clubData.get("vrai_id") + "), '" + new java.sql.Timestamp(currentTime.getTime()) + "')";
							statement.executeUpdate(sql);
						};
						getTraining();
					};
					connection.close();
				} catch (SQLException e) {
					getMyPlayersDataIconLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/icons/players_error.png")).getImage()));
					getMyPlayersDataLabel.setForeground(Color.decode(MainFrame.prop.getProperty("coral_red")));
					getMyPlayersDataStatusLabel.setForeground(Color.decode(MainFrame.prop.getProperty("coral_red")));
					getMyPlayersDataStatusLabel.setText("Echec du MySQL");
					getMyPlayersDataStatusLabel.setVisible(true);
					getMyPlayersDataProgressBar.setVisible(false);
					getMyTrainingDataStatusLabel.setText("Annulee");
					break;
				}
			} else {
				getMyPlayersDataIconLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/icons/players_error.png")).getImage()));
				getMyPlayersDataLabel.setForeground(Color.decode(MainFrame.prop.getProperty("coral_red")));
				getMyPlayersDataStatusLabel.setForeground(Color.decode(MainFrame.prop.getProperty("coral_red")));
				getMyPlayersDataStatusLabel.setText(playerData.get("ErrorMsg").toString());
				getMyPlayersDataStatusLabel.setVisible(true);
				getMyPlayersDataProgressBar.setVisible(false);
				getMyTrainingDataStatusLabel.setText("Annulee");
				break;
			}
		}
	}
	
	public void getTraining() {
		getMyTrainingDataLabel.setForeground(Color.decode(MainFrame.prop.getProperty("onyx")));
		getMyTrainingDataIconLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/icons/training_progress.png")).getImage()));
		getMyTrainingDataStatusLabel.setVisible(false);
		getMyTrainingDataProgressBar.setVisible(true);
		
		for(int i = 0; i < Integer.parseInt(clubData.get("joueurs").toString()); i++) {
			JSONObject trainingData = Training.get(Club.playersLinks[i]);
			if(trainingData.get("Error").equals(false)) { // Successfully got the training' data
				getMyTrainingDataProgressBar.setValue((i+1) * 100 / Club.playersLinks.length);
				if(i == Integer.parseInt(clubData.get("joueurs").toString()) - 1) {
					getMyTrainingDataIconLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/icons/training_complete.png")).getImage()));
					getMyTrainingDataLabel.setForeground(Color.decode(MainFrame.prop.getProperty("lime_green")));
					getMyTrainingDataProgressBar.setForeground(Color.decode(MainFrame.prop.getProperty("lime_green")));
					getMyTrainingDataProgressBar.setBackground(Color.decode(MainFrame.prop.getProperty("lime_green")));
					getMyTrainingDataProgressBar.setBorder(new LineBorder(Color.decode(MainFrame.prop.getProperty("lime_green"))));
					// Update the mise_a_jour table
					try {
						Connection connection = DriverManager.getConnection("jdbc:mysql://" + MainFrame.prop.getProperty("mysql.host") + ":" + MainFrame.prop.getProperty("mysql.port") + "/" + MainFrame.prop.getProperty("mysql.database"), 
																			MainFrame.prop.getProperty("mysql.username"),
																			MainFrame.prop.getProperty("mysql.password"));
						Statement statement = connection.createStatement();
						ResultSet result = statement.executeQuery("SELECT id FROM mise_a_jour WHERE equipe_id = " +
								  						"(SELECT id FROM equipe WHERE vrai_id = " + clubData.get("vrai_id") + ")");
						java.util.Date currentTime = new java.util.Date();
						lastUpdateTime = currentTime;
						if(result.next()) {
							String sql = "UPDATE mise_a_jour SET " +
									 	 "maj_entrainement = '" + new java.sql.Timestamp(currentTime.getTime()) + "', " +
									 	 "maj_tout = '" + new java.sql.Timestamp(currentTime.getTime()) + "' " +
									 	 "WHERE equipe_id = (SELECT id FROM equipe WHERE vrai_id = " + clubData.get("vrai_id") + ")";
							statement.executeUpdate(sql);
						} else {
							String sql = "INSERT INTO mise_a_jour " +
								 	  	 "(equipe_id, maj_entrainement, maj_tout)" +
								 	  	 "VALUES ((SELECT id FROM equipe WHERE vrai_id = " + clubData.get("vrai_id") + "), '" + new java.sql.Timestamp(currentTime.getTime()) + "', '" + new java.sql.Timestamp(currentTime.getTime()) + "')";
							statement.executeUpdate(sql);
						};
						connection.close();
						step = 0;
						int delay = 2000;
						Timer timer = new Timer(delay, taskPerformer);
						timer.setRepeats(false);
						timer.start();
					} catch (SQLException e) {
						getMyTrainingDataIconLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/icons/training_error.png")).getImage()));
						getMyTrainingDataLabel.setForeground(Color.decode(MainFrame.prop.getProperty("coral_red")));
						getMyTrainingDataStatusLabel.setForeground(Color.decode(MainFrame.prop.getProperty("coral_red")));
						getMyTrainingDataStatusLabel.setText("Echec du MySQL");
						getMyTrainingDataStatusLabel.setVisible(true);
						getMyTrainingDataProgressBar.setVisible(false);
					}
				}
			} else {
				getMyTrainingDataIconLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/icons/training_error.png")).getImage()));
				getMyTrainingDataLabel.setForeground(Color.decode(MainFrame.prop.getProperty("coral_red")));
				getMyTrainingDataStatusLabel.setForeground(Color.decode(MainFrame.prop.getProperty("coral_red")));
				getMyTrainingDataStatusLabel.setVisible(true);
				getMyTrainingDataProgressBar.setVisible(false);
				getMyTrainingDataStatusLabel.setText(trainingData.get("ErrorMsg").toString());
				break;
			}
		}
	};
	
	public static ActionListener taskPerformer = new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
			getMyDataStatusPanel.setVisible(false);
			lastUpdateDate.setText("Derniere mise a jour le: " + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(lastUpdateTime));
			lastUpdateDate.setVisible(true);
		}
	};
}