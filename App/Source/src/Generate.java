import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

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
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;

/**
 * Generate details
 */
public class Generate {
	public static JPanel generateDetailsPanel = new JPanel(); // The generate details' panel
	private String filePath = "/home/me/Documents"; // Default location of saving tactics
	private boolean alreadyFavorite = false; // Check if the strategy is already favorite or no
	
	public Generate() {
		main();
	}
	
	private void main() {
		generateDetailsPanel.setOpaque(false);
		generateDetailsPanel.setBounds(0, 0, 746, 429);
		generateDetailsPanel.setVisible(true);
		generateDetailsPanel.setLayout(null);
		MainFrame.bodyPanel.add(generateDetailsPanel);

		JPanel generateActionsPanel = new JPanel();
		generateActionsPanel.setOpaque(false);
		generateActionsPanel.setBounds(130, 15, 145, 361);
		generateActionsPanel.setLayout(null);
		generateDetailsPanel.add(generateActionsPanel);

		String[] tacticsNames = { "Choisir ta tactique", "4-3-3", "4-4-2", "4-5-1", "3-5-2", "3-4-4", "5-4-1", "5-3-2" };
		JComboBox<Object> strategyComboBox = new JComboBox<Object>(tacticsNames);
		strategyComboBox.setFocusable(false);
		strategyComboBox.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		strategyComboBox.setBackground(Color.decode(MainFrame.prop.getProperty("white")));
		strategyComboBox.setForeground(Color.decode(MainFrame.prop.getProperty("lime_green")));
		strategyComboBox.setFont(MainFrame.buttonFont);
		strategyComboBox.setBounds(0, 42, 145, 35);
		generateActionsPanel.add(strategyComboBox);
		
		strategyComboBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent me) { // Hover effects
				me.getComponent().setBackground(Color.decode(MainFrame.prop.getProperty("white")));
				me.getComponent().setForeground(Color.decode(MainFrame.prop.getProperty("lime_green")));
			}
		});

		JButton generateButton = new JButton("Générer");
		generateButton.setEnabled(false);
		generateButton.setBorderPainted(false);
		generateButton.setFocusPainted(false);
		generateButton.setFont(MainFrame.buttonFont);
		generateButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		generateButton.setBackground(Color.decode(MainFrame.prop.getProperty("bleu_de_france")));
		generateButton.setForeground(Color.decode(MainFrame.prop.getProperty("white")));
		generateButton.setIconTextGap(7);
		generateButton.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/btn/generate.png")).getImage()));
		generateButton.setBounds(0, 163, 145, 35);
		generateActionsPanel.add(generateButton);

		generateButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent me) { // Hover effects
				if (me.getComponent().isEnabled()) {
					generateButton.setBorderPainted(true);
					generateButton.setBorder(BorderFactory.createLineBorder(Color.decode(MainFrame.prop.getProperty("bleu_de_france"))));
					generateButton.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/btn/generate_reverse.png")).getImage()));
					generateButton.setBackground(Color.decode(MainFrame.prop.getProperty("white")));
					generateButton.setForeground(Color.decode(MainFrame.prop.getProperty("bleu_de_france")));
				}
			}

			@Override
			public void mouseExited(MouseEvent me) { // Blur effects
				if (me.getComponent().isEnabled()) {
					generateButton.setBorderPainted(false);
					generateButton.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/btn/generate.png")).getImage()));
					generateButton.setBackground(Color.decode(MainFrame.prop.getProperty("bleu_de_france")));
					generateButton.setForeground(Color.decode(MainFrame.prop.getProperty("white")));
				}
			}
		});

		JButton initButton = new JButton("Initialiser");
		initButton.setEnabled(false);
		initButton.setBorderPainted(false);
		initButton.setFocusPainted(false);
		initButton.setFont(MainFrame.buttonFont);
		initButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		initButton.setBackground(Color.decode(MainFrame.prop.getProperty("bleu_de_france")));
		initButton.setForeground(Color.decode(MainFrame.prop.getProperty("white")));
		initButton.setIconTextGap(7);
		initButton.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/btn/initialize.png")).getImage()));
		initButton.setBounds(0, 283, 145, 35);
		generateActionsPanel.add(initButton);

		initButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent me) { // Hover effects
				if (me.getComponent().isEnabled()) {
					((AbstractButton) me.getComponent()).setBorderPainted(true);
					((JComponent) me.getComponent()).setBorder(BorderFactory.createLineBorder(Color.decode(MainFrame.prop.getProperty("bleu_de_france"))));
					((AbstractButton) me.getComponent()).setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/btn/initialize_reverse.png")).getImage()));
					me.getComponent().setBackground(Color.decode(MainFrame.prop.getProperty("white")));
					me.getComponent().setForeground(Color.decode(MainFrame.prop.getProperty("bleu_de_france")));
				}
			}

			@Override
			public void mouseExited(MouseEvent me) { // Blur effects
				if (me.getComponent().isEnabled()) {
					((AbstractButton) me.getComponent()).setBorderPainted(false);
					((AbstractButton) me.getComponent()).setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/btn/initialize.png")).getImage()));
					me.getComponent().setBackground(Color.decode(MainFrame.prop.getProperty("bleu_de_france")));
					me.getComponent().setForeground(Color.decode(MainFrame.prop.getProperty("white")));
				}
			}

			@Override
			public void mouseClicked(MouseEvent me) { // Active effects
				JFrame newFrame = new JFrame();
				newFrame.setVisible(true);
				strategyComboBox.setSelectedIndex(0);
				((AbstractButton) me.getComponent()).setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/btn/initialize.png")).getImage()));
				me.getComponent().setBackground(Color.decode(MainFrame.prop.getProperty("bleu_de_france")));
				me.getComponent().setForeground(Color.decode(MainFrame.prop.getProperty("white")));
			}
		});

		strategyComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ie) { // Option has been changed
				if (strategyComboBox.getSelectedIndex() == 0) {
					strategyComboBox.setBackground(Color.decode(MainFrame.prop.getProperty("white")));
					strategyComboBox.setForeground(Color.decode(MainFrame.prop.getProperty("lime_green")));
					generateButton.setEnabled(false);
					initButton.setEnabled(false);
				} else {
					strategyComboBox.setBackground(Color.decode(MainFrame.prop.getProperty("lime_green")));
					strategyComboBox.setForeground(Color.decode(MainFrame.prop.getProperty("white")));
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
			public void mouseEntered(MouseEvent e) { // Hover effects
				if (strategyComboBox.getSelectedIndex() != 0) {
					strategyActionsPanel.setVisible(true);
					strategyPanel.setBorder(new MatteBorder(3, 3, 3, 3, Color.decode(MainFrame.prop.getProperty("dark_gray"))));
				}
			}
		});

		generateDetailsPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) { // Hover effects
				strategyActionsPanel.setVisible(false);
				strategyPanel.setBorder(null);
				if (strategyComboBox.getSelectedIndex() == 0) {
					strategyComboBox.setBackground(Color.decode(MainFrame.prop.getProperty("white")));
					strategyComboBox.setForeground(Color.decode(MainFrame.prop.getProperty("lime_green")));
				} else {
					strategyComboBox.setBackground(Color.decode(MainFrame.prop.getProperty("lime_green")));
					strategyComboBox.setForeground(Color.decode(MainFrame.prop.getProperty("white")));
				}
			}
		});

		JLabel favoriteShortcutLabel = new JLabel("");
		favoriteShortcutLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		favoriteShortcutLabel.setHorizontalAlignment(SwingConstants.CENTER);
		favoriteShortcutLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/btn/favorite.png")).getImage()));
		favoriteShortcutLabel.setBounds(0, 0, 25, 25);
		strategyActionsPanel.add(favoriteShortcutLabel);

		JLabel saveShortcutLabel = new JLabel("");
		// saveShortcutLabel.setEnabled(false);
		saveShortcutLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		saveShortcutLabel.setHorizontalAlignment(SwingConstants.CENTER);
		saveShortcutLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/btn/save_2.png")).getImage()));
		saveShortcutLabel.setBounds(37, 0, 25, 25);
		strategyActionsPanel.add(saveShortcutLabel);
		
		saveShortcutLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent me) { // Hover effects
				// if(me.getComponent().isEnabled()) {
				saveShortcutLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/btn/save_reverse_2.png")).getImage()));
				// }
			}

			@Override
			public void mouseExited(MouseEvent me) { // Blur effects
				saveShortcutLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/btn/save_2.png")).getImage()));
			}

			@Override
			public void mouseClicked(MouseEvent me) { // Active effects
				boolean fileCreated = false;
				do {
					UIManager.put("OptionPane.background", Color.decode(MainFrame.prop.getProperty("white")));
					UIManager.put("Panel.background", Color.decode(MainFrame.prop.getProperty("white")));
					JFileChooser chooser = new JFileChooser(new File(filePath));
					chooser.setDialogTitle("Sauvegarder ta tactique");
					chooser.setFileFilter(new FileTypeFilter(".png", "Fichier Image"));
					int result = chooser.showSaveDialog(null);
					if (result == JFileChooser.APPROVE_OPTION) { // Save is confirmed
						BufferedImage img = (BufferedImage) strategyPanel.createImage(strategyPanel.getWidth(),strategyPanel.getHeight());
						strategyPanel.paint(img.getGraphics());
						File file;

						if (chooser.getSelectedFile().getPath().endsWith(".png")) {
							file = new File(chooser.getSelectedFile().getPath());
						} else {
							file = new File(chooser.getSelectedFile().getPath() + ".png");
						}
						;

						if (file.exists()) { // File already exists
							String[] options = { "Oui", "Non" };
							int dialog = JOptionPane.showOptionDialog(MainFrame.frmDefifootManager,
									"Etes-vous sûre de vouloir remplacer le fichier existant?", "Confirmer",
									JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);

							if (dialog == JOptionPane.YES_OPTION) {
								try {
									ImageIO.write((BufferedImage) img, "png", file);
									JOptionPane.showMessageDialog(null, "La tactique a bien été sauvegardée.",
											"Complétée", JOptionPane.INFORMATION_MESSAGE,
											new ImageIcon(new ImageIcon(this.getClass().getResource("images/icons/success.png")).getImage()));
									filePath = file.getParent();
									fileCreated = true;
								} catch (Exception e) {
									JOptionPane.showMessageDialog(null, "La tactique n'a pas été sauvegardée!",
											"Echec",
											JOptionPane.ERROR_MESSAGE,
											new ImageIcon(new ImageIcon(this.getClass().getResource("images/icons/error.png")).getImage()));
								}
							} else {
								filePath = file.getParent();
							}
						} else { // File not existing
							try {
								ImageIO.write((BufferedImage) img, "png", file);
								JOptionPane
										.showMessageDialog(null, "La tactique a bien été sauvegardée.",
												"Complétée",
												JOptionPane.INFORMATION_MESSAGE,
												new ImageIcon(new ImageIcon(this.getClass().getResource("images/icons/success.png")).getImage()));
								filePath = file.getParent();
								fileCreated = true;
							} catch (Exception e) { // Save failed
								JOptionPane
										.showMessageDialog(null, "La tactique n'a pas été sauvegardée!",
												"Echec",
												JOptionPane.ERROR_MESSAGE,
												new ImageIcon(new ImageIcon(this.getClass().getResource("images/icons/error.png")).getImage()));
							}
						}
					} else { // Save is cancelled
						fileCreated = true;
					}
				} while (!fileCreated);
			}
		});

		JLabel initShortcutLabel = new JLabel("");
		initShortcutLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		initShortcutLabel.setHorizontalAlignment(SwingConstants.CENTER);
		initShortcutLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/btn/initialize_2.png")).getImage()));
		initShortcutLabel.setBounds(74, 0, 25, 25);
		strategyActionsPanel.add(initShortcutLabel);
		
		initShortcutLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent me) { // Hover effects
				initShortcutLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/btn/initialize_reverse_2.png")).getImage()));
			}

			@Override
			public void mouseExited(MouseEvent me) { // Blur effects
				initShortcutLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/btn/initialize_2.png")).getImage()));
			}

			@Override
			public void mouseClicked(MouseEvent me) { // Active effects
				strategyComboBox.setSelectedIndex(0);
				strategyActionsPanel.setVisible(false);
				strategyPanel.setBorder(null);
			}
		});

		JPanel AddToFavoritePanel = new JPanel();
		AddToFavoritePanel.setVisible(false);
		AddToFavoritePanel.setBackground(new Color(0, 0, 0, 100));
		AddToFavoritePanel.setBounds(26, 164, 170, 45);
		AddToFavoritePanel.setLayout(null);
		strategyPanel.add(AddToFavoritePanel);

		JLabel AddToFavoriteLabel = new JLabel("Ajoutée aux favoris");
		AddToFavoriteLabel.setHorizontalAlignment(SwingConstants.CENTER);
		AddToFavoriteLabel.setFont(MainFrame.buttonFont);
		AddToFavoriteLabel.setForeground(Color.decode(MainFrame.prop.getProperty("white")));
		AddToFavoriteLabel.setBounds(10, 10, 150, 25);
		AddToFavoritePanel.add(AddToFavoriteLabel);
		
		favoriteShortcutLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent me) { // Hover effects
				if (alreadyFavorite) {
					favoriteShortcutLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/btn/favorite_already_2.png")).getImage()));
				} else {
					favoriteShortcutLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/btn/favorite_reverse.png")).getImage()));
				}
			}

			@Override
			public void mouseExited(MouseEvent me) { // Blur effects
				if (alreadyFavorite) {
					favoriteShortcutLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/btn/favorite_already.png")).getImage()));
				} else {
					favoriteShortcutLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/btn/favorite.png")).getImage()));
				}
			}

			@Override
			public void mouseClicked(MouseEvent e) { // Active effects
				alreadyFavorite = !alreadyFavorite;
				if (alreadyFavorite) {
					favoriteShortcutLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/btn/favorite_already_2.png")).getImage()));
					AddToFavoritePanel.setVisible(true);
					int delay = 500;
					ActionListener taskPerformer = new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							AddToFavoritePanel.setVisible(false);
						}
					};
					Timer timer = new Timer(delay, taskPerformer);
					timer.setRepeats(false);
					timer.start();
				} else {
					favoriteShortcutLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/btn/favorite_reverse.png")).getImage()));
				}
			}
		});

		JLabel stadiumLabel = new JLabel("");
		stadiumLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/stadium.png")).getImage()));
		stadiumLabel.setBounds(6, 6, 210, 361);
		strategyPanel.add(stadiumLabel);

		/*Canvas strategyCanvas = new Canvas();
		strategyCanvas.setBackground(Color.YELLOW);
		strategyCanvas.setBounds(6, 6, 210, 361);
		strategyPanel.add(strategyCanvas);*/

		JLabel generateTitleLabel = new JLabel("Générer et sauvegarder des Tactiques");
		generateTitleLabel.setFont(new Font("Trebuchet MS", Font.PLAIN, 18));
		generateTitleLabel.setBounds(220, 389, 306, 25);
		generateDetailsPanel.add(generateTitleLabel);
	}
}