package auctionhouse;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = -1320680914079154752L;
	private final JLabel auctionStatus = statusLabel();
	private final JButton closeButton = closeJButton();
	public static final String CLOSE_BUTTON = "CloseButton";
	public static final String AUCTION_STATUS = "AuctionStatus";
	public static final String AUCTION_HOUSE = "AuctionHouse";

	public MainWindow(final UserActionListener listener) {
		super("Auction House");
		setName(MainWindow.AUCTION_HOUSE);
		fillContentPane();
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				listener.closeAuction();
			}
		});
		setVisible(true);
	}

	private void fillContentPane() {
	    final Container contentPane = getContentPane(); 
	    contentPane.setLayout(new BorderLayout());
	    contentPane.add(closeButton, BorderLayout.CENTER); 
	    contentPane.add(auctionStatus, BorderLayout.NORTH); 
	}

	private static JLabel statusLabel() {
		JLabel label = new JLabel();
		label.setName(MainWindow.AUCTION_STATUS);
		label.setText("Started");
		return label;
	}
	
	private static JButton closeJButton() {
		JButton button = new JButton();
		button.setName(MainWindow.CLOSE_BUTTON);
		button.setText("Close");
		return button;
	}

	public void setStatus(String statusText) {
		auctionStatus.setText(statusText);
	}

}
