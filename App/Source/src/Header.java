import java.awt.Color;
import java.awt.Cursor;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

/**
 * Common header of the app
 */
public class Header {
	public static JPanel navBarPanel = new JPanel(); // Navigation panel
	private int pressedX = 0, pressedY = 0; // (x,y) coordinations when user is moving the frame
	public static int functionChosen = 1; // The navbar menu chosen
	
	public Header() {
		main();
	}
	
	private void main() {
		JPanel headerPanel = new JPanel();
		headerPanel.setOpaque(false);
		headerPanel.setBounds(0, 0, 750, 142);
		headerPanel.setLayout(null);
		MainFrame.frmDefifootManager.getContentPane().add(headerPanel);

		JPanel toolBarPanel = new JPanel();
		toolBarPanel.setOpaque(false);
		toolBarPanel.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
		toolBarPanel.setBounds(0, 0, 750, 92);
		toolBarPanel.setLayout(null);
		headerPanel.add(toolBarPanel);

		JPanel toolBarSparatorPanel = new JPanel();
		toolBarSparatorPanel.setBackground(Color.decode(MainFrame.prop.getProperty("white")));
		toolBarSparatorPanel.setBounds(0, 9, 697, 2);
		toolBarPanel.add(toolBarSparatorPanel);

		JButton minimizeButton = new JButton("");
		minimizeButton.setBorderPainted(false);
		minimizeButton.setFocusPainted(false);
		minimizeButton.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		minimizeButton.setBackground(Color.decode(MainFrame.prop.getProperty("lime_green")));
		minimizeButton.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/btn/minimize.png")).getImage()));
		minimizeButton.setBounds(702, 0, 24, 20);
		toolBarPanel.add(minimizeButton);

		minimizeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent me) {
				me.getComponent().setBackground(Color.decode(MainFrame.prop.getProperty("tangerine_yellow")));
			}

			@Override
			public void mouseExited(MouseEvent me) {
				me.getComponent().setBackground(Color.decode(MainFrame.prop.getProperty("lime_green")));
			}

			@Override
			public void mouseClicked(MouseEvent me) {
				MainFrame.frmDefifootManager.setState(Frame.ICONIFIED);
			}
		});

		JButton closeButton = new JButton("");
		closeButton.setBorderPainted(false);
		closeButton.setFocusPainted(false);
		closeButton.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		closeButton.setBackground(Color.decode(MainFrame.prop.getProperty("lime_green")));
		closeButton.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/btn/close.png")).getImage()));
		closeButton.setBounds(726, 0, 24, 20);
		toolBarPanel.add(closeButton);

		closeButton.addMouseListener(new MouseAdapter() {  
			@Override
			public void mouseEntered(MouseEvent me) {
				me.getComponent().setBackground(Color.decode(MainFrame.prop.getProperty("coral_red")));
			}

			@Override
			public void mouseExited(MouseEvent me) {
				me.getComponent().setBackground(Color.decode(MainFrame.prop.getProperty("lime_green")));
			}

			@Override
			public void mouseClicked(MouseEvent me) {
				UIManager.put("OptionPane.background", Color.decode(MainFrame.prop.getProperty("white")));
				UIManager.put("Panel.background", Color.decode(MainFrame.prop.getProperty("white")));
				String[] options = { "Oui", "Non" };
				int dialog = JOptionPane.showOptionDialog(MainFrame.frmDefifootManager, "Etes vous sûre de vouloir quitter?",
						"Quitter?", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE,
						new ImageIcon(new ImageIcon(this.getClass().getResource("images/icons/warning.png")).getImage()),
						options, options[1]);

				if (dialog == JOptionPane.YES_OPTION) {
					System.exit(1);
				}
			}
		});

		JLabel logoLabel = new JLabel("");
		logoLabel.setBounds(0, 12, 335, 80);
		logoLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/logo-complete.png")).getImage()));
		toolBarPanel.add(logoLabel);

		toolBarPanel.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				pressedX = me.getX();
				pressedY = me.getY();
			}
		});

		toolBarPanel.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent me) {
				MainFrame.frmDefifootManager.setLocation(MainFrame.frmDefifootManager.getLocation().x + me.getX() - pressedX, MainFrame.frmDefifootManager.getLocation().y + me.getY() - pressedY);
			}
		});

		navBarPanel.setOpaque(false);
		navBarPanel.setBounds(0, 92, 750, 50);
		navBarPanel.setLayout(null);
		headerPanel.add(navBarPanel);

		JPanel generatePanel = new JPanel();
		generatePanel.setBackground(Color.decode(MainFrame.prop.getProperty("white")));
		generatePanel.setBounds(129, 0, 120, 50);
		generatePanel.setLayout(null);
		navBarPanel.add(generatePanel);

		JLabel generateLabel = new JLabel("GENERER");
		generateLabel.setHorizontalAlignment(SwingConstants.CENTER);
		generateLabel.setFont(MainFrame.titleFont);
		generateLabel.setBackground(Color.decode(MainFrame.prop.getProperty("white")));
		generateLabel.setForeground(Color.decode(MainFrame.prop.getProperty("lime_green")));
		generateLabel.setBounds(0, 0, 120, 50);
		generatePanel.add(generateLabel);

		JPanel filterPanel = new JPanel();
		filterPanel.setBackground(Color.decode(MainFrame.prop.getProperty("lime_green")));
		filterPanel.setBounds(253, 0, 120, 50);
		filterPanel.setLayout(null);
		navBarPanel.add(filterPanel);

		JLabel filterLabel = new JLabel("FILTRER");
		filterLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		filterLabel.setHorizontalAlignment(SwingConstants.CENTER);
		filterLabel.setFont(MainFrame.titleFont);
		filterLabel.setBackground(Color.decode(MainFrame.prop.getProperty("white")));
		filterLabel.setForeground(Color.decode(MainFrame.prop.getProperty("white")));
		filterLabel.setBounds(0, 0, 120, 50);
		filterPanel.add(filterLabel);

		JPanel fonction3Panel = new JPanel();
		fonction3Panel.setBackground(Color.decode(MainFrame.prop.getProperty("lime_green")));
		fonction3Panel.setBounds(377, 0, 120, 50);
		fonction3Panel.setLayout(null);
		navBarPanel.add(fonction3Panel);

		JLabel fonction3Label = new JLabel("FONCTION 3");
		fonction3Label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		fonction3Label.setHorizontalAlignment(SwingConstants.CENTER);
		fonction3Label.setFont(MainFrame.titleFont);
		fonction3Label.setBackground(Color.decode(MainFrame.prop.getProperty("white")));
		fonction3Label.setForeground(Color.decode(MainFrame.prop.getProperty("white")));
		fonction3Label.setBounds(0, 0, 120, 50);
		fonction3Panel.add(fonction3Label);

		JPanel myProfilePanel = new JPanel();
		myProfilePanel.setBackground(Color.decode(MainFrame.prop.getProperty("lime_green")));
		myProfilePanel.setBounds(501, 0, 120, 50);
		myProfilePanel.setLayout(null);
		navBarPanel.add(myProfilePanel);

		JLabel myProfileLabel = new JLabel("MON PROFIL");
		myProfileLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		myProfileLabel.setHorizontalAlignment(SwingConstants.CENTER);
		myProfileLabel.setFont(MainFrame.titleFont);
		myProfileLabel.setBackground(Color.decode(MainFrame.prop.getProperty("white")));
		myProfileLabel.setForeground(Color.decode(MainFrame.prop.getProperty("white")));
		myProfileLabel.setBounds(0, 0, 120, 50);
		myProfilePanel.add(myProfileLabel);
		
		generatePanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent me) {
				if(navBarPanel.isEnabled()) {
					me.getComponent().setBackground(Color.decode(MainFrame.prop.getProperty("white")));
					generateLabel.setForeground(Color.decode(MainFrame.prop.getProperty("lime_green")));
				}
			}

			@Override
			public void mouseExited(MouseEvent me) {
				if(navBarPanel.isEnabled()) {
					if (functionChosen != 1) {
						me.getComponent().setBackground(Color.decode(MainFrame.prop.getProperty("lime_green")));
						generateLabel.setForeground(Color.decode(MainFrame.prop.getProperty("white")));
						generateLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					} else {
						generateLabel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					}
				}
			}

			@Override
			public void mouseClicked(MouseEvent me) {
				if(navBarPanel.isEnabled()) {
					switch (functionChosen) {
						case 1:
							return;
						case 2:
							filterPanel.setBackground(Color.decode(MainFrame.prop.getProperty("lime_green")));
							filterLabel.setForeground(Color.decode(MainFrame.prop.getProperty("white")));
							filterLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
							if (!MainFrame.isLoggedIn) {
								Login.connectionPanel.removeAll();
								Login.connectionPanel.updateUI();
								MainFrame.bodyPanel.remove(Login.connectionPanel);
							};
							Filter.filterDetailsPanel.setVisible(false);
							break;
						case 3:
							fonction3Panel.setBackground(Color.decode(MainFrame.prop.getProperty("lime_green")));
							fonction3Label.setForeground(Color.decode(MainFrame.prop.getProperty("white")));
							fonction3Label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
							Function3.function_3Panel.setVisible(false);
							break;
						case 4:
							myProfilePanel.setBackground(Color.decode(MainFrame.prop.getProperty("lime_green")));
							myProfileLabel.setForeground(Color.decode(MainFrame.prop.getProperty("white")));
							myProfileLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
							if (!MainFrame.isLoggedIn) {
								Login.connectionPanel.removeAll();
								Login.connectionPanel.updateUI();
								MainFrame.bodyPanel.remove(Login.connectionPanel);
							};
							MyProfile.myProfilePanel.setVisible(false);
							break;
					}
					me.getComponent().setBackground(Color.decode(MainFrame.prop.getProperty("white")));
					generateLabel.setForeground(Color.decode(MainFrame.prop.getProperty("lime_green")));
					Generate.generateDetailsPanel.setVisible(true);
					functionChosen = 1;
				}
			}
		});

		filterPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent me) {
				if(navBarPanel.isEnabled()) {
					me.getComponent().setBackground(Color.decode(MainFrame.prop.getProperty("white")));
					filterLabel.setForeground(Color.decode(MainFrame.prop.getProperty("lime_green")));
				}
			}

			@Override
			public void mouseExited(MouseEvent me) {
				if(navBarPanel.isEnabled()) {
					if (functionChosen != 2) {
						me.getComponent().setBackground(Color.decode(MainFrame.prop.getProperty("lime_green")));
						filterLabel.setForeground(Color.decode(MainFrame.prop.getProperty("white")));
						filterLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					} else {
						filterLabel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					}
				}
			}

			@Override
			public void mouseClicked(MouseEvent me) {
				if(navBarPanel.isEnabled()) {
					switch (functionChosen) {
						case 2:
							return;
						case 1:
							generatePanel.setBackground(Color.decode(MainFrame.prop.getProperty("lime_green")));
							generateLabel.setForeground(Color.decode(MainFrame.prop.getProperty("white")));
							generateLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
							Generate.generateDetailsPanel.setVisible(false);
							break;
						case 3:
							fonction3Panel.setBackground(Color.decode(MainFrame.prop.getProperty("lime_green")));
							fonction3Label.setForeground(Color.decode(MainFrame.prop.getProperty("white")));
							fonction3Label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
							Function3.function_3Panel.setVisible(false);
							break;
						case 4:
							myProfilePanel.setBackground(Color.decode(MainFrame.prop.getProperty("lime_green")));
							myProfileLabel.setForeground(Color.decode(MainFrame.prop.getProperty("white")));
							myProfileLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
							if (!MainFrame.isLoggedIn) {
								Login.connectionPanel.removeAll();
								Login.connectionPanel.updateUI();
								MainFrame.bodyPanel.remove(Login.connectionPanel);
							};
							MyProfile.myProfilePanel.setVisible(false);
							break;
					}
					if (!MainFrame.isLoggedIn) {
						new Login();
						Login.main();
						Filter.filterDetailsPanel.setVisible(false);
						MyProfile.myProfilePanel.setVisible(false);
					} else {
						me.getComponent().setBackground(Color.decode(MainFrame.prop.getProperty("white")));
						me.getComponent().setForeground(Color.decode(MainFrame.prop.getProperty("lime_green")));
						filterLabel.setForeground(Color.decode(MainFrame.prop.getProperty("lime_green")));
						MainFrame.bodyPanel.remove(Login.connectionPanel);
						Filter.filterDetailsPanel.setVisible(true);
						Filter.updateStatus();
					}
					functionChosen = 2;
				}
			}
		});

		fonction3Panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent me) {
				if(navBarPanel.isEnabled()) {
					me.getComponent().setBackground(Color.decode(MainFrame.prop.getProperty("white")));
					fonction3Label.setForeground(Color.decode(MainFrame.prop.getProperty("lime_green")));
				}
			}

			@Override
			public void mouseExited(MouseEvent me) {
				if(navBarPanel.isEnabled()) {
					if (functionChosen != 3) {
						me.getComponent().setBackground(Color.decode(MainFrame.prop.getProperty("lime_green")));
						fonction3Label.setForeground(Color.decode(MainFrame.prop.getProperty("white")));
						fonction3Label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					} else {
						fonction3Label.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					}
				}
			}

			@Override
			public void mouseClicked(MouseEvent me) {
				if(navBarPanel.isEnabled()) {
					switch (functionChosen) {
						case 3:
							return;
						case 1:
							generatePanel.setBackground(Color.decode(MainFrame.prop.getProperty("lime_green")));
							generateLabel.setForeground(Color.decode(MainFrame.prop.getProperty("white")));
							generateLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
							Generate.generateDetailsPanel.setVisible(false);
							break;
						case 2:
							filterPanel.setBackground(Color.decode(MainFrame.prop.getProperty("lime_green")));
							filterLabel.setForeground(Color.decode(MainFrame.prop.getProperty("white")));
							filterLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
							if (!MainFrame.isLoggedIn) {
								Login.connectionPanel.removeAll();
								Login.connectionPanel.updateUI();
								MainFrame.bodyPanel.remove(Login.connectionPanel);
							};
							Filter.filterDetailsPanel.setVisible(false);
							break;
						case 4:
							myProfilePanel.setBackground(Color.decode(MainFrame.prop.getProperty("lime_green")));
							myProfileLabel.setForeground(Color.decode(MainFrame.prop.getProperty("white")));
							myProfileLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
							if (!MainFrame.isLoggedIn) {
								Login.connectionPanel.removeAll();
								Login.connectionPanel.updateUI();
								MainFrame.bodyPanel.remove(Login.connectionPanel);
							};
							MyProfile.myProfilePanel.setVisible(false);
							break;
					}
					me.getComponent().setBackground(Color.decode(MainFrame.prop.getProperty("white")));
					fonction3Label.setForeground(Color.decode(MainFrame.prop.getProperty("lime_green")));
					Function3.function_3Panel.setVisible(true);
					functionChosen = 3;
				}
			}
		});

		myProfilePanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent me) {
				if(navBarPanel.isEnabled()) {
					me.getComponent().setBackground(Color.decode(MainFrame.prop.getProperty("white")));
					myProfileLabel.setForeground(Color.decode(MainFrame.prop.getProperty("lime_green")));
				}
			}

			@Override
			public void mouseExited(MouseEvent me) {
				if(navBarPanel.isEnabled()) {
					if (functionChosen != 4) {
						me.getComponent().setBackground(Color.decode(MainFrame.prop.getProperty("lime_green")));
						myProfileLabel.setForeground(Color.decode(MainFrame.prop.getProperty("white")));
						myProfileLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					} else {
						myProfileLabel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					}
				}
			}

			@Override
			public void mouseClicked(MouseEvent me) {
				if(navBarPanel.isEnabled()) {
					switch (functionChosen) {
						case 4:
							return;
						case 1:
							generatePanel.setBackground(Color.decode(MainFrame.prop.getProperty("lime_green")));
							generateLabel.setForeground(Color.decode(MainFrame.prop.getProperty("white")));
							generateLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
							Generate.generateDetailsPanel.setVisible(false);
							break;
						case 2:
							filterPanel.setBackground(Color.decode(MainFrame.prop.getProperty("lime_green")));
							filterLabel.setForeground(Color.decode(MainFrame.prop.getProperty("white")));
							filterLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
							if (!MainFrame.isLoggedIn) {
								Login.connectionPanel.removeAll();
								Login.connectionPanel.updateUI();
								MainFrame.bodyPanel.remove(Login.connectionPanel);
							};
							Filter.filterDetailsPanel.setVisible(false);
							break;
						case 3:
							fonction3Panel.setBackground(Color.decode(MainFrame.prop.getProperty("lime_green")));
							fonction3Label.setForeground(Color.decode(MainFrame.prop.getProperty("white")));
							fonction3Label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
							Function3.function_3Panel.setVisible(false);
							break;
					}
					if(!MainFrame.isLoggedIn) {
						new Login();
						Login.main();
						Filter.filterDetailsPanel.setVisible(false);
						MyProfile.myProfilePanel.setVisible(false);
					} else {
						me.getComponent().setBackground(Color.decode(MainFrame.prop.getProperty("white")));
						myProfileLabel.setForeground(Color.decode(MainFrame.prop.getProperty("lime_green")));
						MainFrame.bodyPanel.remove(Login.connectionPanel);
						MyProfile.myProfilePanel.setVisible(true);
						MyProfile.updateClubData();
					}
					functionChosen = 4;
				}
			}
		});
	}
}