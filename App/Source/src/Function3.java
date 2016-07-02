import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * Function 4
 */
public class Function3 {
	public static JPanel function_3Panel = new JPanel(); // The 3rd function's panel
	
	public Function3() {
		main();
	}
	
	private void main() {
		function_3Panel.setOpaque(false);
		function_3Panel.setBounds(0, 0, 746, 429);
		function_3Panel.setLayout(new GridBagLayout());
		function_3Panel.setVisible(false);
		MainFrame.bodyPanel.add(function_3Panel);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/icons/under_maintenance.png")).getImage()));
		lblNewLabel.setSize(64, 64);
		lblNewLabel.setSize(128, 128);
		function_3Panel.add(lblNewLabel, new GridBagConstraints());
	}
}