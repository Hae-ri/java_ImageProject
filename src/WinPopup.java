import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class WinPopup extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JLabel lblPic;

	/**
	 * Create the dialog.
	 */
	
	public WinPopup(ImageIcon img) {
		setTitle("원본이미지");
		setBounds(100, 100, img.getIconWidth(), img.getIconHeight());
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		lblPic = new JLabel("");
		lblPic.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
			}
		});
		contentPanel.add(lblPic);
		
		lblPic.setBounds(0,0, img.getIconWidth()+40, img.getIconHeight()+40);;
		lblPic.setIcon(img);
			
	}


}
