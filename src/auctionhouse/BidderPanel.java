package auctionhouse;

import java.awt.Component;
import java.awt.FlowLayout;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpringLayout;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.SwingConstants;

public class BidderPanel extends JPanel {

	public static final BidderPanel EMPTY = new BidderPanel();
	private JLabel idLabel = new JLabel();
	private JLabel detailLabel = new JLabel();

	private BidderPanel() {
		setPreferredSize(new Dimension(250, 80));
	}

	public BidderPanel(BidderSnapshot value) {
		super();
		idLabel.setBounds(92, 10, 146, 15);
		idLabel.setText(value.bidderId);
		detailLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		detailLabel.setFont(new Font("굴림", Font.BOLD, 16));
		detailLabel.setBounds(92, 35, 146, 19);
		detailLabel.setText(value.status);
		
		JLabel lblNewLabel;
		try {
			BufferedImage image = ImageIO.read(new File("images/avatars/deer.jpg"));
			lblNewLabel = new JLabel(new ImageIcon(image));
		} catch (IOException e) {
			lblNewLabel = new JLabel("N/A");
		}
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel.setBounds(0, 0, 80, 80);
		setPreferredSize(new Dimension(250, 80));
		setLayout(null);
		add(lblNewLabel);
		add(idLabel);
		add(detailLabel);
	}

	public boolean matches(String bidderId, String detail) {
		return idLabel.getText().equals(bidderId)
				&& detailLabel.getText().equals(detail);
	}

}
