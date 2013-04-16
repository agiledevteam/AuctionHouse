package auctionhouse;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class BidderPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9129639135661686148L;
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
		detailLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
		detailLabel.setBounds(92, 35, 146, 19);
		detailLabel.setText(value.status);
		
		JLabel imageLabel = Avatar.getLabel(value.bidderId);
		imageLabel.setVerticalAlignment(SwingConstants.TOP);
		imageLabel.setBounds(0, 0, 80, 80);
		setPreferredSize(new Dimension(250, 80));
		setLayout(null);
		add(imageLabel);
		add(idLabel);
		add(detailLabel);
	}

	public boolean matches(String bidderId, String detail) {
		return idLabel.getText().equals(bidderId)
				&& detailLabel.getText().equals(detail);
	}

}
