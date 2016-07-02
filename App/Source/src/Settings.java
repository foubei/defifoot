import java.awt.Color;
import java.awt.ComponentOrientation;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 * Settings' details
 */
public class Settings {
	public Settings() {
		main();
	}
	public void main() {
		JPanel currencyPanel = new JPanel();
		currencyPanel.setOpaque(false);
		currencyPanel.setLayout(null);
		currencyPanel.setBounds(0, 0, 210, 24);
		
		JLabel currencyLabel = new JLabel("Monnaie par default:");
		currencyLabel.setSize(150, 24);
		currencyPanel.add(currencyLabel);
		currencyLabel.setForeground(Color.decode(MainFrame.prop.getProperty("onyx")));
		currencyLabel.setFont(MainFrame.mediumFont);
		
		JComboBox<Object> currencyComboBox = new JComboBox<Object>(new Object[]{"Euro", "Tune"});
		currencyComboBox.setSelectedIndex(MainFrame.prop.getProperty("currency").equalsIgnoreCase("Euro") ? 0 : 1);
		currencyComboBox.setFocusable(false);
		currencyComboBox.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		currencyComboBox.setBackground(Color.decode(MainFrame.prop.getProperty("white")));
		currencyComboBox.setForeground(Color.decode(MainFrame.prop.getProperty("onyx")));
		currencyComboBox.setFont(MainFrame.mediumFont);
		currencyComboBox.setBounds(150, 2, 60, 20);
		currencyPanel.add(currencyComboBox);
		
		final JComponent[] inputs = new JComponent[] {
				currencyPanel
		};
		int dialog = JOptionPane.showOptionDialog(MainFrame.frmDefifootManager, inputs, "Mes parametres", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{"Appliquer", "Annuler"}, "Annuler");
		
		UIManager.put("OptionPane.background", Color.decode(MainFrame.prop.getProperty("white")));
		UIManager.put("Panel.background", Color.decode(MainFrame.prop.getProperty("white")));
		
		if(dialog == JOptionPane.YES_OPTION) {
			// Save succeeded
			String oldFilePath = Settings.class.getClassLoader().getResource("properties/").getPath() + "config.properties";
			String tmpFilePath = Settings.class.getClassLoader().getResource("properties/").getPath() + "tmp_config.properties";
			
			BufferedReader br = null;
			BufferedWriter bw = null;
			File file = new File(tmpFilePath);
			
			Boolean failed = false;
			try {
				br = new BufferedReader(new FileReader(oldFilePath));
				bw = new BufferedWriter(new FileWriter(file.getPath()));
				String line;
				while ((line = br.readLine()) != null) {
					if (line.contains("currency")) {
						line = line.replace("euro", currencyComboBox.getSelectedItem().toString().toLowerCase());
						line = line.replace("tune", currencyComboBox.getSelectedItem().toString().toLowerCase());
					}
					bw.write(line+"\n");
				}
			} catch (Exception e) {
				// Save failed
				JOptionPane.showMessageDialog(null, "Tes changements n'ont pas été sauvegardée!",
						"Echec",
						JOptionPane.ERROR_MESSAGE,
						new ImageIcon(new ImageIcon(this.getClass().getResource("images/icons/error.png")).getImage()));
				failed = true;
			} finally {
				if(!failed) {
					// Changes done.
					try {
						if(br != null) {
							br.close();
						}
						if(bw != null) {
							bw.close();
						}
						File oldFile = new File(oldFilePath);
						oldFile.delete();
						
						File newFile = new File(tmpFilePath);
						newFile.renameTo(oldFile);
						
						MainFrame.prop.setProperty("currency", currencyComboBox.getSelectedItem().toString().toLowerCase());
						JOptionPane.showMessageDialog(null, "Tes changements ont bien été sauvegardée.",
								"Complétée", JOptionPane.INFORMATION_MESSAGE,
								new ImageIcon(new ImageIcon(this.getClass().getResource("images/icons/success.png")).getImage()));
					} catch (IOException e) {
						// Save failed
						JOptionPane.showMessageDialog(null, "Tes changements n'ont pas été sauvegardée!",
								"Echec",
								JOptionPane.ERROR_MESSAGE,
								new ImageIcon(new ImageIcon(this.getClass().getResource("images/icons/error.png")).getImage()));
					}
				}
			}
		}
	}
}
