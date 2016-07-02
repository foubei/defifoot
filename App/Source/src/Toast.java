import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Common toast of the app
 */
public class Toast {
	public static JLabel toastLabel = new JLabel(""); // Label for displaying as notification in the top
	
	public Toast() {
		main();
	}
	
	private void main() {
		JPanel toastPanel = new JPanel();
		toastPanel.setBounds(281, 10, 455, 50);
		toastPanel.setLayout(null);
		toastPanel.setOpaque(false);;
		toastPanel.setVisible(false);
		MainFrame.bodyPanel.add(toastPanel);

		JPanel toastMessagePanel = new JPanel();
		toastMessagePanel.setBackground(new Color(0, 0, 0, 100));
		toastMessagePanel.setBounds(0, 0, 455, 50);
		toastMessagePanel.setLayout(null);
		toastPanel.add(toastMessagePanel);
		
		toastLabel.setSize(425, 20);
		toastLabel.setLocation(15, 15);
		toastLabel.setHorizontalAlignment(SwingConstants.CENTER);
		toastLabel.setFont(MainFrame.buttonFont);
		toastLabel.setForeground(Color.decode(MainFrame.prop.getProperty("white")));
		toastMessagePanel.add(toastLabel);
	}
}