import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Common footer of the app
 */
public class Footer {
	public Footer() {
		main();
	}
	
	@SuppressWarnings("unchecked")
	private void main() {
		JPanel footerPanel = new JPanel();
		footerPanel.setBackground(Color.decode(MainFrame.prop.getProperty("lime_green")));
		footerPanel.setBounds(0, 571, 750, 29);
		footerPanel.setLayout(null);
		MainFrame.frmDefifootManager.getContentPane().add(footerPanel);

		JLabel yearLabel = new JLabel("\u00A9 Copyright 2016");
		yearLabel.setForeground(Color.decode(MainFrame.prop.getProperty("white")));
		yearLabel.setFont(MainFrame.smallFont);
		yearLabel.setBounds(501, 7, 88, 15);
		footerPanel.add(yearLabel);

		JLabel authorLabel = new JLabel("FouBei");
		authorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		authorLabel.setFont(MainFrame.smallFont);
		authorLabel.setForeground(Color.decode(MainFrame.prop.getProperty("white")));
		authorLabel.setBounds(596, 7, 33, 15);
		footerPanel.add(authorLabel);

		@SuppressWarnings("rawtypes")
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
				me.getComponent().setForeground(Color.decode(MainFrame.prop.getProperty("bleu_de_france")));
			}

			@Override
			public void mouseExited(MouseEvent me) {
				me.getComponent().setForeground(Color.decode(MainFrame.prop.getProperty("white")));
			}
		});

		JLabel copyrightLabel = new JLabel("Tous droits r\u00E9serv\u00E9s.");
		copyrightLabel.setForeground(Color.decode(MainFrame.prop.getProperty("white")));
		copyrightLabel.setFont(MainFrame.smallFont);
		copyrightLabel.setBounds(636, 7, 107, 15);
		footerPanel.add(copyrightLabel);
	}
}