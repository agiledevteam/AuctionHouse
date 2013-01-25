package auctionhouse;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class MainWindow extends JFrame implements BrokerListener {

	private static final long serialVersionUID = -1320680914079154752L;
	private final JLabel auctionStatus = statusLabel();
	private final JButton closeButton = closeJButton();
	private final JTextArea logArea = logJTextArea(); 
	private final JLabel winnerLabel = winnerLabel();
	public static final String CLOSE_BUTTON = "CloseButton";
	public static final String AUCTION_STATUS = "AuctionStatus";
	public static final String AUCTION_HOUSE = "AuctionHouse";
	public static final String AUCTION_LOG = "AuctionLog";
	public static final String WINNER_LABEL = "WinnerLabel";

	public static final String AUCTION_LOG_FORMAT = "%s is %s at %d\n";
	
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
	    
	    JPanel panel = new JPanel(new FlowLayout());
	    panel.add(auctionStatus);
	    panel.add(closeButton);
	    panel.add(new JLabel("Winner is "));
	    panel.add(winnerLabel);
	    
	    contentPane.setLayout(new BorderLayout());
	    contentPane.add(panel, BorderLayout.NORTH);
	    contentPane.add(logArea, BorderLayout.SOUTH);
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

	private JTextArea logJTextArea() {
		JTextArea textArea = new JTextArea(10, 20);
		textArea.setName(MainWindow.AUCTION_LOG);
		textArea.setText("-------\n");
		return textArea;
	}

	private JLabel winnerLabel() {
		JLabel label = new JLabel();
		label.setName(MainWindow.WINNER_LABEL);
		label.setText("");
		return label;
	}

	@Override
	public void setStatus(String statusText, int lastPrice, String bidder) {
		auctionStatus.setText(statusText);
		logArea.append(String.format(AUCTION_LOG_FORMAT, bidder, statusText, lastPrice));
	}
}
