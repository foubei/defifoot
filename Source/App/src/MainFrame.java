import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

public class MainFrame {

	private JFrame frmDefifootManager;
	private int pressedX = 0,
			    pressedY = 0,
			    functionChosen = 1;
	private String filePath = "/home/me/Documents";
	private boolean alreadyFavorite = false,
					isLoggedIn = false;
	private Color bgMainColor = new Color(46, 204, 64),
			      bgSecondaryColor = Color.WHITE,
			      bgButtonActiveColor = new Color(27,124,229),
			      whiteColor = Color.WHITE,
			      successColor = new Color(46, 204, 64),
			      dangerColor = new Color(255, 65, 54),
			      warningColor = Color.ORANGE,
			      linkColor = new Color(27,124,229),
			      blackColor = new Color(17, 17, 17),
			      grayColor = new Color(170, 170, 170);
	private Font titleFont = new Font("Segoe UI", Font.BOLD, 14),
			     smallFont = new Font("Trebuchet MS", Font.PLAIN, 11),
			     mediumFont = new Font("Trebuchet MS", Font.PLAIN, 14),
			     buttonFont = new Font("Tahoma", Font.PLAIN, 16);
	private JLabel saveShortcutLabel;
	private JTextField usernameTextField;
	private JPasswordField passwordField;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
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

	/**
	 * Create the application.
	 */
	public MainFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDefifootManager = new JFrame();
		frmDefifootManager.setTitle("Defifoot Manager");
		frmDefifootManager.setUndecorated(true);
		frmDefifootManager.setResizable(false);
		frmDefifootManager.setBounds(100, 100, 750, 600);
		frmDefifootManager.getContentPane().setBackground(bgMainColor);
		//frmDefifootManager.setIconImage(new ImageIcon(this.getClass().getResource("logo.png")).getImage());
		frmDefifootManager.setLocationRelativeTo(null);
		frmDefifootManager.getContentPane().setLayout(null);
		
		/**********   Header   **********/
		JPanel headerPanel = new JPanel();
		headerPanel.setOpaque(false);
		headerPanel.setBounds(0, 0, 750, 142);
		headerPanel.setLayout(null);
		frmDefifootManager.getContentPane().add(headerPanel);
		
		JPanel toolBarPanel = new JPanel();
		toolBarPanel.setOpaque(false);
		toolBarPanel.setBounds(0, 0, 750, 92);
		toolBarPanel.setLayout(null);
		headerPanel.add(toolBarPanel);
		
		JPanel toolBarSparatorPanel = new JPanel();
		toolBarSparatorPanel.setBackground(bgSecondaryColor);
		toolBarSparatorPanel.setBounds(0, 9, 697, 2);
		toolBarPanel.add(toolBarSparatorPanel);
		
		JButton minimizeButton = new JButton("");
		minimizeButton.setBorderPainted(false);
		minimizeButton.setFocusPainted(false);
		minimizeButton.setBackground(bgMainColor);
		minimizeButton.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("btn/minimize.png")).getImage()));
		minimizeButton.setBounds(702, 0, 24, 20);
		toolBarPanel.add(minimizeButton);
		
		minimizeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent me) {
				me.getComponent().setBackground(warningColor);
			}
			@Override
			public void mouseExited(MouseEvent me) {
				me.getComponent().setBackground(bgMainColor);
			}
			@Override
			public void mouseClicked(MouseEvent me) {
				frmDefifootManager.setState(Frame.ICONIFIED);
			}
		});
		
		JButton closeButton = new JButton("");
		closeButton.setBorderPainted(false);
		closeButton.setFocusPainted(false);
		closeButton.setBackground(bgMainColor);
		closeButton.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("btn/close.png")).getImage()));
		closeButton.setBounds(726, 0, 24, 20);
		toolBarPanel.add(closeButton);
		
		closeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent me) {
				me.getComponent().setBackground(dangerColor);
			}
			@Override
			public void mouseExited(MouseEvent me) {
				me.getComponent().setBackground(bgMainColor);
			}
			@Override
			public void mouseClicked(MouseEvent me) {
				UIManager.put("OptionPane.background", bgSecondaryColor);
				UIManager.put("Panel.background", bgSecondaryColor);
				String[] options = {"Oui", "Non"};
				int dialog = JOptionPane.showOptionDialog(frmDefifootManager,
			            "Êtes-vous sûre de vouloir quitter?",
			            "Quitter?",
			            JOptionPane.YES_NO_OPTION,
			            JOptionPane.WARNING_MESSAGE,
		    		    new ImageIcon(new ImageIcon(this.getClass().getResource("icons/warning.png")).getImage()),
			            options,
			            options[1]);

			    if(dialog == JOptionPane.YES_OPTION) {
			        System.exit(1);
			    }
			}
		});
		
		JLabel logoLabel = new JLabel("");
		logoLabel.setBounds(0, 12, 335, 80);
		logoLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("logo-complete.png")).getImage()));
		toolBarPanel.add(logoLabel);
		
		toolBarPanel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
            	pressedX = me.getX();
                pressedY = me.getY();
            }
        });
		
		toolBarPanel.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent me) {
            	frmDefifootManager.setLocation(frmDefifootManager.getLocation().x + me.getX() - pressedX, frmDefifootManager.getLocation().y + me.getY() - pressedY);
            }
        });
		
		JPanel navBarPanel = new JPanel();
		navBarPanel.setOpaque(false);
		navBarPanel.setBounds(0, 92, 750, 50);
		navBarPanel.setLayout(null);
		headerPanel.add(navBarPanel);
		
		JPanel generatePanel = new JPanel();
		generatePanel.setBackground(bgSecondaryColor);
		generatePanel.setBounds(130, 0, 120, 50);
		generatePanel.setLayout(null);
		navBarPanel.add(generatePanel);
		
		JLabel generateLabel = new JLabel("GENERER");
		generateLabel.setHorizontalAlignment(SwingConstants.CENTER);
		generateLabel.setFont(titleFont);
		generateLabel.setBackground(whiteColor);
		generateLabel.setForeground(bgMainColor);
		generateLabel.setBounds(0, 0, 120, 50);
		generatePanel.add(generateLabel);
		
		JPanel filterPanel = new JPanel();
		filterPanel.setBackground(bgMainColor);
		filterPanel.setBounds(253, 0, 120, 50);
		filterPanel.setLayout(null);
		navBarPanel.add(filterPanel);
		
		JLabel filterLabel = new JLabel("FILTRER");
		filterLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		filterLabel.setHorizontalAlignment(SwingConstants.CENTER);
		filterLabel.setFont(titleFont);
		filterLabel.setBackground(whiteColor);
		filterLabel.setForeground(whiteColor);
		filterLabel.setBounds(0, 0, 120, 50);
		filterPanel.add(filterLabel);
		
		JPanel fonction3Panel = new JPanel();
		fonction3Panel.setBackground(bgMainColor);
		fonction3Panel.setBounds(377, 0, 120, 50);
		fonction3Panel.setLayout(null);
		navBarPanel.add(fonction3Panel);
		
		JLabel fonction3Label = new JLabel("FONCTION 3");
		fonction3Label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		fonction3Label.setHorizontalAlignment(SwingConstants.CENTER);
		fonction3Label.setFont(titleFont);
		fonction3Label.setBackground(whiteColor);
		fonction3Label.setForeground(whiteColor);
		fonction3Label.setBounds(0, 0, 120, 50);
		fonction3Panel.add(fonction3Label);
		
		JPanel fonction4Panel = new JPanel();
		fonction4Panel.setBackground(bgMainColor);
		fonction4Panel.setBounds(500, 0, 120, 50);
		fonction4Panel.setLayout(null);
		navBarPanel.add(fonction4Panel);
		
		JLabel fonction4Label = new JLabel("FONCTION 4");
		fonction4Label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		fonction4Label.setHorizontalAlignment(SwingConstants.CENTER);
		fonction4Label.setFont(titleFont);
		fonction4Label.setBackground(whiteColor);
		fonction4Label.setForeground(whiteColor);
		fonction4Label.setBounds(0, 0, 120, 50);
		fonction4Panel.add(fonction4Label);
		/********** End Header **********/
		
		/**********   Body   **********/
		JPanel bodyPanel = new JPanel();
		bodyPanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		bodyPanel.setBackground(bgSecondaryColor);
		bodyPanel.setBounds(2, 142, 746, 429);
		bodyPanel.setLayout(null);
		frmDefifootManager.getContentPane().add(bodyPanel);
		
		
		//////////////////////////////////////////////////////////////
		JPanel generateDetailsPanel = new JPanel();
		generateDetailsPanel.setOpaque(false);
		generateDetailsPanel.setBounds(0, 0, 746, 429);
		generateDetailsPanel.setVisible(true);
		generateDetailsPanel.setLayout(null);
		bodyPanel.add(generateDetailsPanel);

		JPanel generateActionsPanel = new JPanel();
		generateActionsPanel.setOpaque(false);
		generateActionsPanel.setBounds(130, 15, 145, 361);
		generateActionsPanel.setLayout(null);
		generateDetailsPanel.add(generateActionsPanel);

		String[] petStrings = { "Choisir ta tactique", "4-3-3", "4-4-2", "4-5-1", "3-5-2", "3-4-4", "5-4-1", "5-3-2" };
		JComboBox strategyComboBox = new JComboBox(petStrings);
		strategyComboBox.setFocusable(false);
		strategyComboBox.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		strategyComboBox.setBackground(whiteColor);
		strategyComboBox.setForeground(bgMainColor);
		strategyComboBox.setFont(new Font("Tahoma", Font.PLAIN, 16));
		strategyComboBox.setBounds(0, 42, 145, 35);
		generateActionsPanel.add(strategyComboBox);
		
		strategyComboBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent me) {
				me.getComponent().setBackground(bgSecondaryColor);
				me.getComponent().setForeground(successColor);
			}
		});
		
		JButton generateButton = new JButton("Générer");
		generateButton.setEnabled(false);
		generateButton.setBorderPainted(false);
		generateButton.setFocusPainted(false);
		generateButton.setFont(buttonFont);
		generateButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		generateButton.setBackground(bgButtonActiveColor);
		generateButton.setForeground(whiteColor);
		generateButton.setIconTextGap(7);
		generateButton.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("btn/generate.png")).getImage()));
		generateButton.setBounds(0, 163, 145, 35);
		generateActionsPanel.add(generateButton);
		
		generateButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent me) {
				if(me.getComponent().isEnabled()) {
					generateButton.setBorderPainted(true);
					generateButton.setBorder(BorderFactory.createLineBorder(linkColor));
					generateButton.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("btn/generate_reverse.png")).getImage()));
					generateButton.setBackground(whiteColor);
					generateButton.setForeground(bgButtonActiveColor);
				}
			}
			@Override
			public void mouseExited(MouseEvent me) {
				if(me.getComponent().isEnabled()) {
					generateButton.setBorderPainted(false);
					generateButton.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("btn/generate.png")).getImage()));
					generateButton.setBackground(bgButtonActiveColor);
					generateButton.setForeground(whiteColor);
				}
			}
		});
		
		JButton initButton = new JButton("Initialiser");
		initButton.setEnabled(false);
		initButton.setBorderPainted(false);
		initButton.setFocusPainted(false);
		initButton.setFont(buttonFont);
		initButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		initButton.setBackground(bgButtonActiveColor);
		initButton.setForeground(whiteColor);
		initButton.setIconTextGap(7);
		initButton.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("btn/initialize.png")).getImage()));
		initButton.setBounds(0, 283, 145, 35);
		generateActionsPanel.add(initButton);
		
		initButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent me) {
				if(me.getComponent().isEnabled()) {
					((AbstractButton) me.getComponent()).setBorderPainted(true);
					((JComponent) me.getComponent()).setBorder(BorderFactory.createLineBorder(linkColor));
					((AbstractButton) me.getComponent()).setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("btn/initialize_reverse.png")).getImage()));
					me.getComponent().setBackground(whiteColor);
					me.getComponent().setForeground(bgButtonActiveColor);
				}
			}
			@Override
			public void mouseExited(MouseEvent me) {
				if(me.getComponent().isEnabled()) {
					((AbstractButton) me.getComponent()).setBorderPainted(false);
					((AbstractButton) me.getComponent()).setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("btn/initialize.png")).getImage()));
					me.getComponent().setBackground(bgButtonActiveColor);
					me.getComponent().setForeground(whiteColor);
				}
			}
			@Override
			public void mouseClicked(MouseEvent me) {
				strategyComboBox.setSelectedIndex(0);
				((AbstractButton) me.getComponent()).setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("btn/initialize.png")).getImage()));
				me.getComponent().setBackground(bgButtonActiveColor);
				me.getComponent().setForeground(whiteColor);
			}
		});
		


		strategyComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ie) {
				if(strategyComboBox.getSelectedIndex() == 0) {
					strategyComboBox.setBackground(whiteColor);
					strategyComboBox.setForeground(bgMainColor);
					generateButton.setEnabled(false);
					initButton.setEnabled(false);
				} else {
					strategyComboBox.setBackground(bgMainColor);
					strategyComboBox.setForeground(whiteColor);
					generateButton.setEnabled(true);
					initButton.setEnabled(true);
				}
			}
		});
		
		JPanel strategyPanel = new JPanel();
		strategyPanel.setOpaque(false);
		strategyPanel.setBounds(401, 9, 222, 374);
		strategyPanel.setLayout(null);
		generateDetailsPanel.add(strategyPanel);
		
		JPanel strategyActionsPanel = new JPanel();
		strategyActionsPanel.setVisible(false);
		strategyActionsPanel.setOpaque(false);
		strategyActionsPanel.setBounds(61, 317, 100, 25);
		strategyActionsPanel.setLayout(null);
		strategyPanel.add(strategyActionsPanel);
		
		strategyPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if(strategyComboBox.getSelectedIndex() != 0) {
					strategyActionsPanel.setVisible(true);
					strategyPanel.setBorder(new MatteBorder(3, 3, 3, 3, grayColor));
				}
			}
		});

		generateDetailsPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				strategyActionsPanel.setVisible(false);
				strategyPanel.setBorder(null);
				if(strategyComboBox.getSelectedIndex() == 0) {
					strategyComboBox.setBackground(bgSecondaryColor);
					strategyComboBox.setForeground(successColor);
				} else {
					strategyComboBox.setBackground(bgMainColor);
					strategyComboBox.setForeground(whiteColor);
				}
			}
		});
		
		JLabel favoriteShortcutLabel = new JLabel("");
		favoriteShortcutLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		favoriteShortcutLabel.setHorizontalAlignment(SwingConstants.CENTER);
		favoriteShortcutLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("btn/favorite.png")).getImage()));
		favoriteShortcutLabel.setBounds(0, 0, 25, 25);
		strategyActionsPanel.add(favoriteShortcutLabel);
		
		saveShortcutLabel = new JLabel("");
		//saveShortcutLabel.setEnabled(false);
		saveShortcutLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		saveShortcutLabel.setHorizontalAlignment(SwingConstants.CENTER);
		saveShortcutLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("btn/save_2.png")).getImage()));
		saveShortcutLabel.setBounds(37, 0, 25, 25);
		strategyActionsPanel.add(saveShortcutLabel);
		
		JLabel initShortcutLabel = new JLabel("");
		initShortcutLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		initShortcutLabel.setHorizontalAlignment(SwingConstants.CENTER);
		initShortcutLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("btn/initialize_2.png")).getImage()));
		initShortcutLabel.setBounds(74, 0, 25, 25);
		strategyActionsPanel.add(initShortcutLabel);
		
		JPanel AddToFavoritePanel = new JPanel();
		AddToFavoritePanel.setVisible(false);
		AddToFavoritePanel.setBackground(new Color(0, 0, 0, 100));
		AddToFavoritePanel.setBounds(26, 164, 170, 45);
		AddToFavoritePanel.setLayout(null);
		strategyPanel.add(AddToFavoritePanel);
		
		JLabel AddToFavoriteLabel = new JLabel("Ajoutée aux favoris");
		AddToFavoriteLabel.setHorizontalAlignment(SwingConstants.CENTER);
		AddToFavoriteLabel.setFont(buttonFont);
		AddToFavoriteLabel.setForeground(whiteColor);
		AddToFavoriteLabel.setBounds(10, 10, 150, 25);
		AddToFavoritePanel.add(AddToFavoriteLabel);
		
		JLabel stadiumLabel = new JLabel("");
		stadiumLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("stadium.png")).getImage()));
		stadiumLabel.setBounds(6, 6, 210, 361);
		strategyPanel.add(stadiumLabel);
		
		initShortcutLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent me) {
				initShortcutLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("btn/initialize_reverse_2.png")).getImage()));
			}
			@Override
			public void mouseExited(MouseEvent me) {
				initShortcutLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("btn/initialize_2.png")).getImage()));
			}
			@Override
			public void mouseClicked(MouseEvent me) {
				strategyComboBox.setSelectedIndex(0);
				strategyActionsPanel.setVisible(false);
				strategyPanel.setBorder(null);
			}
		});
		
		saveShortcutLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent me) {
				//if(me.getComponent().isEnabled()) {
					saveShortcutLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("btn/save_reverse_2.png")).getImage()));
				//}
			}
			@Override
			public void mouseExited(MouseEvent me) {
				saveShortcutLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("btn/save_2.png")).getImage()));
			}
			@Override
			public void mouseClicked(MouseEvent me) {
				boolean fileCreated = false;
				do {
		    		UIManager.put("OptionPane.background", bgSecondaryColor);
					UIManager.put("Panel.background", bgSecondaryColor);
					JFileChooser chooser = new JFileChooser(new File(filePath));
					chooser.setDialogTitle("Sauvegarder ta tactique");
					chooser.setFileFilter(new FileTypeFilter(".png", "Fichier Image"));
				    int result = chooser.showSaveDialog(null);
				    if(result == JFileChooser.APPROVE_OPTION) { // Save is confirmed
				    	BufferedImage img = (BufferedImage) strategyPanel.createImage(strategyPanel.getWidth(), strategyPanel.getHeight());
			    		strategyPanel.paint(img.getGraphics());
			    		File file;
			    		
			    		if(chooser.getSelectedFile().getPath().endsWith(".png")) {
			    			file = new File(chooser.getSelectedFile().getPath());
			    		} else {
			    			file = new File(chooser.getSelectedFile().getPath() + ".png");
			    		};
			    		
				    	if(file.exists()) {
							String[] options = {"Oui", "Non"};
							int dialog = JOptionPane.showOptionDialog(frmDefifootManager,
						            "Êtes-vous sûre de vouloir remplacer le fichier existant?",
						            "Confirmer",
						            JOptionPane.YES_NO_OPTION,
						            JOptionPane.QUESTION_MESSAGE,
						            null,
						            options,
						            options[1]);

						    if(dialog == JOptionPane.YES_OPTION) {
						    	try{
						    		ImageIO.write((BufferedImage)img, "png", file);
						    		JOptionPane.showMessageDialog(null,
						    			"La tactique a bien été sauvegardée.",
						    			"Complétée",
						    		    JOptionPane.INFORMATION_MESSAGE,
						    		    new ImageIcon(new ImageIcon(this.getClass().getResource("icons/success.png")).getImage()));
						    		filePath = file.getParent();
						    		fileCreated = true;
						    	} catch (Exception e) {
						    		JOptionPane.showMessageDialog(null,
						    			"La tactique n'a pas été sauvegardée!",
						    			"Echec",
						    		    JOptionPane.ERROR_MESSAGE,
						    		    new ImageIcon(new ImageIcon(this.getClass().getResource("icons/error.png")).getImage()));
						    	}
						    } else {
						    	filePath = file.getParent();
						    }
				    	} else {
				    		try {
					    		ImageIO.write((BufferedImage)img, "png", file);
						    	JOptionPane.showMessageDialog(null,
					    			"La tactique a bien été sauvegardée.",
					    			"Complétée",
					    		    JOptionPane.INFORMATION_MESSAGE,
					    		    new ImageIcon(new ImageIcon(this.getClass().getResource("icons/success.png")).getImage()));
						    	filePath = file.getParent();
						    	fileCreated = true;
					    	} catch (Exception e) {
					    		JOptionPane.showMessageDialog(null,
					    			"La tactique n'a pas été sauvegardée!",
					    			"Echec",
					    		    JOptionPane.ERROR_MESSAGE,
					    		    new ImageIcon(new ImageIcon(this.getClass().getResource("icons/error.png")).getImage()));
					    	}
				    	}
				    } else { // Save is cancelled
				    	fileCreated = true;
				    }
				} while(!fileCreated);
			}
		});
		
		favoriteShortcutLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent me) {
				if(alreadyFavorite) {
					favoriteShortcutLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("btn/favorite_already_2.png")).getImage()));
				} else {
					favoriteShortcutLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("btn/favorite_reverse.png")).getImage()));
				}
			}
			@Override
			public void mouseExited(MouseEvent me) {
				if(alreadyFavorite) {
					favoriteShortcutLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("btn/favorite_already.png")).getImage()));
				} else {
					favoriteShortcutLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("btn/favorite.png")).getImage()));
				}
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				alreadyFavorite = !alreadyFavorite;
				if(alreadyFavorite) {
					favoriteShortcutLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("btn/favorite_already_2.png")).getImage()));
					AddToFavoritePanel.setVisible(true);
					int delay = 1000;
					ActionListener taskPerformer = new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							AddToFavoritePanel.setVisible(false);
						}
					};
					new Timer(delay, taskPerformer).start();
				} else {
					favoriteShortcutLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("btn/favorite_reverse.png")).getImage()));
				}
			}
		});
		
		/*Canvas strategyCanvas = new Canvas();
		strategyCanvas.setBackground(Color.YELLOW);
		strategyCanvas.setBounds(0, 0, 210, 361);
		strategyPanel.add(strategyCanvas);*/
		
		JLabel generateTitleLabel = new JLabel("Générer et sauvegarder des Tactiques");
		generateTitleLabel.setFont(new Font("Trebuchet MS", Font.PLAIN, 18));
		generateTitleLabel.setBounds(220, 389, 306, 25);
		generateDetailsPanel.add(generateTitleLabel);
		
		
		//////////////////////////////////////////////////////////////
		JPanel filterDetailsPanel = new JPanel();
		filterDetailsPanel.setOpaque(false);
		filterDetailsPanel.setBounds(0, 0, 746, 429);
		filterDetailsPanel.setLayout(null);
		filterDetailsPanel.setVisible(false);
		filterDetailsPanel.setBackground(dangerColor);
		bodyPanel.add(filterDetailsPanel);
		
		JLabel filterTitleLabel = new JLabel("Filtrer la meilleure composition");
		filterTitleLabel.setFont(new Font("Trebuchet MS", Font.PLAIN, 18));
		filterTitleLabel.setBounds(247, 389, 252, 25);
		filterDetailsPanel.add(filterTitleLabel);
		
		
		//////////////////////////////////////////////////////////////
		JPanel fonction_3Panel = new JPanel();
		fonction_3Panel.setOpaque(false);
		fonction_3Panel.setBounds(0, 0, 746, 429);
		fonction_3Panel.setLayout(null);
		fonction_3Panel.setVisible(false);
		bodyPanel.add(fonction_3Panel);
		
		
		//////////////////////////////////////////////////////////////
		JPanel fonction_4Panel = new JPanel();
		fonction_4Panel.setOpaque(false);
		fonction_4Panel.setBounds(0, 0, 746, 429);
		fonction_4Panel.setLayout(null);
		fonction_4Panel.setVisible(false);
		bodyPanel.add(fonction_4Panel);
		
		
		////////////////////////////////////////////////////////////
		JPanel connexionPanel = new JPanel();
		connexionPanel.setVisible(false);
		connexionPanel.setBorder(new TitledBorder(new MatteBorder(3, 3, 3, 3, grayColor), "Se connecter à Defifoot", TitledBorder.LEADING, TitledBorder.TOP, null, grayColor));
		connexionPanel.setOpaque(false);
		connexionPanel.setBounds(173, 89, 400, 250);
		connexionPanel.setLayout(null);
		bodyPanel.add(connexionPanel);
		
		JPanel usernamePanel = new JPanel();
		usernamePanel.setBorder(new MatteBorder(1, 1, 1, 1, grayColor));
		usernamePanel.setBounds(119, 57, 163, 35);
		usernamePanel.setLayout(null);
		connexionPanel.add(usernamePanel);
		
		JLabel usernameLabel = new JLabel("");
		usernameLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("icons/user.png")).getImage()));
		usernameLabel.setBounds(9, 9, 16, 16);
		usernamePanel.add(usernameLabel);
		
		usernameTextField = new JTextField();
		usernameTextField.setText("Pseudo");
		usernameTextField.setOpaque(false);
		usernameTextField.setBorder(null);
		usernameTextField.setFont(mediumFont);
		usernameTextField.setForeground(grayColor);
		usernameTextField.setBounds(34, 0, 120, 35);
		usernamePanel.add(usernameTextField);
		
		JPanel passwordPanel = new JPanel();
		passwordPanel.setBorder(new MatteBorder(1, 1, 1, 1, grayColor));
		passwordPanel.setBounds(119, 107, 163, 35);
		passwordPanel.setLayout(null);
		connexionPanel.add(passwordPanel);
		
		JLabel passwordLabel = new JLabel("");
		passwordLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("icons/password.png")).getImage()));
		passwordLabel.setBounds(9, 9, 16, 16);
		passwordPanel.add(passwordLabel);
		
		passwordField = new JPasswordField();
		passwordField.setText("MotDePasse");
		passwordField.setOpaque(false);
		passwordField.setBorder(null);
		passwordField.setFont(mediumFont);
		passwordField.setForeground(grayColor);
		passwordField.setBounds(34, 0, 120, 35);
		passwordPanel.add(passwordField);
		
		JButton loginButton = new JButton("Se connecter");
		loginButton.setHorizontalTextPosition(SwingConstants.LEFT);
		loginButton.setEnabled(false);
		loginButton.setBorderPainted(false);
		loginButton.setFocusPainted(false);
		loginButton.setFont(buttonFont);
		loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		loginButton.setBackground(bgButtonActiveColor);
		loginButton.setForeground(whiteColor);
		loginButton.setBounds(119, 157, 163, 35);
		connexionPanel.add(loginButton);
		
		JLabel loginLabel = new JLabel("");
		loginLabel.setVisible(false);
		loginLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("icons/ajax-loader.gif")).getImage()));
		loginLabel.setBounds(184, 202, 32, 32);
		connexionPanel.add(loginLabel);
		
		JLabel loginFailedLabel = new JLabel("*Login ou mot de passe incorrect");
		loginFailedLabel.setVisible(false);
		loginFailedLabel.setFont(mediumFont);
		loginFailedLabel.setForeground(dangerColor);
		loginFailedLabel.setBounds(95, 202, 210, 32);
		connexionPanel.add(loginFailedLabel);
		
		usernameTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent fe) {
				if(Objects.equals(usernameTextField.getText(), "Pseudo")) {
					usernameTextField.setText("");
					usernameTextField.setForeground(blackColor);
				}
				if(Objects.equals(passwordField.getForeground(), grayColor)) {
					loginButton.setEnabled(false);
				}
			}
			@Override
			public void focusLost(FocusEvent fe) {
				if(Objects.equals(usernameTextField.getText(), "") || Objects.equals(usernameTextField.getText(), "Pseudo")) {
					usernameTextField.setText("Pseudo");
					usernameTextField.setForeground(grayColor);
				}
			}
		});

		usernameTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent ky) {
				if(Objects.equals(usernameTextField.getText(), "")) {
					loginButton.setEnabled(false);
				} else {
					if(Objects.equals(passwordField.getPassword(), "")  || Objects.equals(passwordField.getForeground(), grayColor)) {
						loginButton.setEnabled(false);
					} else {
						loginButton.setEnabled(true);
					}
				}
				loginFailedLabel.setVisible(false);
			}
		});
		
		passwordField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent fe) {
				if(Objects.equals(passwordField.getText(), "MotDePasse")) {
					passwordField.setText("");
					passwordField.setForeground(blackColor);
				}
				if(Objects.equals(usernameTextField.getForeground(), grayColor)) {
					loginButton.setEnabled(false);
				}
			}
			@Override
			public void focusLost(FocusEvent fe) {
				if(Objects.equals(passwordField.getText(), "") || Objects.equals(passwordField.getText(), "MotDePasse")) {
					passwordField.setText("MotDePasse");
					passwordField.setForeground(grayColor);
				}
			}
		});
		
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent ky) {
				if(Objects.equals(passwordField.getText(), "")) {
					loginButton.setEnabled(false);
				} else {
					if(Objects.equals(usernameTextField.getText(), "")  || Objects.equals(usernameTextField.getForeground(), grayColor)) {
						loginButton.setEnabled(false);
					} else {
						loginButton.setEnabled(true);
					}
				}
				loginFailedLabel.setVisible(false);
			}
		});
		
		loginButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent me) {
				if(me.getComponent().isEnabled()) {
					((AbstractButton) me.getComponent()).setBorderPainted(true);
					((JComponent) me.getComponent()).setBorder(BorderFactory.createLineBorder(linkColor));
					me.getComponent().setBackground(whiteColor);
					me.getComponent().setForeground(bgButtonActiveColor);
				}
			}
			@Override
			public void mouseExited(MouseEvent me) {
				if(me.getComponent().isEnabled()) {
					((AbstractButton) me.getComponent()).setBorderPainted(false);
					me.getComponent().setBackground(bgButtonActiveColor);
					me.getComponent().setForeground(whiteColor);
				}
			}
			@Override
			public void mouseClicked(MouseEvent me) {
				if(loginButton.isEnabled()) {
					loginLabel.setVisible(true);
					loginFailedLabel.setVisible(false);
					usernameTextField.setEditable(false);
					passwordField.setEditable(false);
					loginButton.setEnabled(false);
					ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
					executor.schedule(new Runnable(){
						public void run(){
							boolean result = new FilterClass(usernameTextField.getText(), passwordField.getText()).getPlayers("http://defifoot.com/modules/equipe/effectif.php?dataview=niv");
							if(!result) {
								loginLabel.setVisible(false);
								loginFailedLabel.setVisible(true);
							} else {
								loginLabel.setVisible(false);
								connexionPanel.setVisible(false);
								isLoggedIn = true;
								switch (functionChosen) {
									case 2:
										filterDetailsPanel.setVisible(true);
										return;
								}
							}
							usernameTextField.setEditable(true);
							passwordField.setEditable(true);
							loginButton.setEnabled(true);
						}
					}, 1000, TimeUnit.MILLISECONDS);
				}
			}
		});
		
		
		generatePanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent me) {
				me.getComponent().setBackground(whiteColor);
				generateLabel.setForeground(bgMainColor);
			}
			@Override
			public void mouseExited(MouseEvent me) {
				if(functionChosen != 1) {
					me.getComponent().setBackground(bgMainColor);
					generateLabel.setForeground(whiteColor);
					generateLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				} else {
					generateLabel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				}
			}
			@Override
			public void mouseClicked(MouseEvent me) {
				switch (functionChosen) {
					case 1:
						return;
					case 2:
						filterPanel.setBackground(bgMainColor);
						filterLabel.setForeground(whiteColor);
						filterLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
						filterDetailsPanel.setVisible(false);
					case 3:
						fonction3Panel.setBackground(bgMainColor);
						fonction3Label.setForeground(whiteColor);
						fonction3Label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
						fonction_3Panel.setVisible(false);
					case 4:
						fonction4Panel.setBackground(bgMainColor);
						fonction4Label.setForeground(whiteColor);
						fonction4Label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
						fonction_4Panel.setVisible(false);
				}
				functionChosen = 1;
				me.getComponent().setBackground(whiteColor);
				generateLabel.setForeground(bgMainColor);
				generateDetailsPanel.setVisible(true);
				connexionPanel.setVisible(false);
			}
		});
		
		filterPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent me) {
				me.getComponent().setBackground(whiteColor);
				filterLabel.setForeground(bgMainColor);
			}
			@Override
			public void mouseExited(MouseEvent me) {
				if(functionChosen != 2) {
					me.getComponent().setBackground(bgMainColor);
					filterLabel.setForeground(whiteColor);
					filterLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				} else {
					filterLabel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				}
			}
			@Override
			public void mouseClicked(MouseEvent me) {
				switch (functionChosen) {
					case 2:
						return;
					case 1:
						generatePanel.setBackground(bgMainColor);
						generateLabel.setForeground(whiteColor);
						generateLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
						generateDetailsPanel.setVisible(false);
					case 3:
						fonction3Panel.setBackground(bgMainColor);
						fonction3Label.setForeground(whiteColor);
						fonction3Label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
						fonction_3Panel.setVisible(false);
					case 4:
						fonction4Panel.setBackground(bgMainColor);
						fonction4Label.setForeground(whiteColor);
						fonction4Label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
						fonction_4Panel.setVisible(false);
				}
				functionChosen = 2;
				if(!isLoggedIn) {
					connexionPanel.setVisible(true);
					usernameTextField.requestFocusInWindow();
				} else {
					me.getComponent().setBackground(whiteColor);
					me.getComponent().setForeground(bgMainColor);
					filterLabel.setForeground(bgMainColor);
					filterDetailsPanel.setVisible(true);
				}
			}
		});
		
		fonction3Panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent me) {
				me.getComponent().setBackground(whiteColor);
				fonction3Label.setForeground(bgMainColor);
			}
			@Override
			public void mouseExited(MouseEvent me) {
				if(functionChosen != 3) {
					me.getComponent().setBackground(bgMainColor);
					fonction3Label.setForeground(whiteColor);
					fonction3Label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				} else {
					fonction3Label.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				}
			}
			@Override
			public void mouseClicked(MouseEvent me) {
				switch (functionChosen) {
					case 3:
						return;
					case 1:
						generatePanel.setBackground(bgMainColor);
						generateLabel.setForeground(whiteColor);
						generateLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
						generateDetailsPanel.setVisible(false);
					case 2:
						filterPanel.setBackground(bgMainColor);
						filterLabel.setForeground(whiteColor);
						filterLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
						filterDetailsPanel.setVisible(false);
					case 4:
						fonction4Panel.setBackground(bgMainColor);
						fonction4Label.setForeground(whiteColor);
						fonction4Label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
						fonction_4Panel.setVisible(false);
				}
				functionChosen = 3;
				me.getComponent().setBackground(whiteColor);
				fonction3Label.setForeground(bgMainColor);
				fonction_3Panel.setVisible(true);
				connexionPanel.setVisible(false);
			}
		});
		
		fonction4Panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent me) {
				me.getComponent().setBackground(whiteColor);
				fonction4Label.setForeground(bgMainColor);
			}
			@Override
			public void mouseExited(MouseEvent me) {
				if(functionChosen != 4) {
					me.getComponent().setBackground(bgMainColor);
					fonction4Label.setForeground(whiteColor);
					fonction4Label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				} else {
					fonction4Label.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				}
			}
			@Override
			public void mouseClicked(MouseEvent me) {
				switch (functionChosen) {
					case 4:
						return;
					case 1:
						generatePanel.setBackground(bgMainColor);
						generateLabel.setForeground(whiteColor);
						generateLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
						generateDetailsPanel.setVisible(false);
					case 2:
						filterPanel.setBackground(bgMainColor);
						filterLabel.setForeground(whiteColor);
						filterLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
						filterDetailsPanel.setVisible(false);
					case 3:
						fonction3Panel.setBackground(bgMainColor);
						fonction3Label.setForeground(whiteColor);
						fonction3Label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
						fonction_3Panel.setVisible(false);
				}
				functionChosen = 4;
				me.getComponent().setBackground(whiteColor);
				fonction4Label.setForeground(bgMainColor);
				fonction_4Panel.setVisible(true);
				connexionPanel.setVisible(false);
			}
		});
		/********** End Body **********/
		
		/**********   Footer   **********/
		JPanel footerPanel = new JPanel();
		footerPanel.setBackground(bgMainColor);
		footerPanel.setBounds(0, 571, 750, 29);
		footerPanel.setLayout(null);
		frmDefifootManager.getContentPane().add(footerPanel);
		
		JLabel yearLabel = new JLabel("\u00A9 Copyright 2016");
		yearLabel.setForeground(whiteColor);
		yearLabel.setFont(smallFont);
		yearLabel.setBounds(501, 7, 88, 15);
		footerPanel.add(yearLabel);
		
		JLabel authorLabel = new JLabel("FouBei");
		authorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		authorLabel.setFont(smallFont);
		authorLabel.setForeground(whiteColor);
		authorLabel.setBounds(596, 7, 33, 15);
		footerPanel.add(authorLabel);
		
		Map attributes = authorLabel.getFont().getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		authorLabel.setFont(authorLabel.getFont().deriveFont(attributes));
		authorLabel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				try {
					java.awt.Desktop.getDesktop().browse(new java.net.URI("https://www.facebook.com/tonton.foggia"));
				} catch (IOException e) {
					e.printStackTrace();
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
			}
			@Override
			public void mouseEntered(MouseEvent me) {
				me.getComponent().setForeground(linkColor);
			}
			@Override
			public void mouseExited(MouseEvent me) {
				me.getComponent().setForeground(whiteColor);
			}
		});
		
		JLabel copyrightLabel = new JLabel("Tous droits r\u00E9serv\u00E9s.");
		copyrightLabel.setForeground(whiteColor);
		copyrightLabel.setFont(smallFont);
		copyrightLabel.setBounds(636, 7, 107, 15);
		footerPanel.add(copyrightLabel);
		/********** End Footer **********/
	}

}
