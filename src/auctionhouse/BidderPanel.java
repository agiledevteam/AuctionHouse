package auctionhouse;

import java.awt.FlowLayout;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BidderPanel extends JPanel {

	private JLabel idLabel = new JLabel();
	private JLabel detailLabel = new JLabel();

	public BidderPanel(BidderSnapshot value) {
		super();
		idLabel.setText(value.bidderId);
		detailLabel.setText(value.status);
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup()
				.addComponent(idLabel).addComponent(detailLabel));
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(idLabel).addComponent(detailLabel));
		setPreferredSize(getPreferredSize());
	}

	public boolean matches(String bidderId, String detail) {
		return idLabel.getText().equals(bidderId)
				&& detailLabel.getText().equals(detail);
	}

}
