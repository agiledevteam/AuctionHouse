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
	private final JButton actionButton = makeActionButton();
	private final JTextArea logArea = logJTextArea(); 
	public static final String ACTION_BUTTON = "ActionButton";

	public static final String AUCTION_STATUS = "AuctionStatus";
	public static final String AUCTION_HOUSE = "AuctionHouse";
	public static final String AUCTION_LOG = "AuctionLog";

	public static final String ACTION_BUTTON_START = "Start";
	public static final String ACTION_BUTTON_CLOSE = "Close";
	
	public static final String AUCTION_LOG_FORMAT = "%s is %s at %d\n";
	
	public MainWindow(final UserActionListener listener) {
		super("Auction House");
		setName(MainWindow.AUCTION_HOUSE);
		fillContentPane();
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		actionButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (ACTION_BUTTON_START.equals(actionButton.getText())) {
					listener.openAuction("localhost", 5222, "auction-item-54321@localhost", "auction");
					actionButton.setText(ACTION_BUTTON_CLOSE);
					setStatus("Started", 0, "");
				}
				else {
					listener.closeAuction();
					actionButton.setText(ACTION_BUTTON_START);
					setStatus("Closed", 0, "");
				}
			}
		});
		setVisible(true);
	}

	private void fillContentPane() {
	    final Container contentPane = getContentPane(); 
	    
	    JPanel panel = new JPanel(new FlowLayout());
	    panel.add(auctionStatus, BorderLayout.WEST);
	    panel.add(actionButton, BorderLayout.EAST); 
	    
	    contentPane.setLayout(new BorderLayout());
	    contentPane.add(panel, BorderLayout.NORTH);
	    contentPane.add(logArea, BorderLayout.SOUTH);
	}

	private static JLabel statusLabel() {
		JLabel label = new JLabel();
		label.setName(MainWindow.AUCTION_STATUS);
		label.setText("Not Started");
		return label;
	}
	
	private static JButton makeActionButton() {
		JButton button = new JButton();
		button.setName(MainWindow.ACTION_BUTTON);
		button.setText(ACTION_BUTTON_START);
		return button;
	}

	private JTextArea logJTextArea() {
		JTextArea textArea = new JTextArea(10, 20);
		textArea.setName(MainWindow.AUCTION_LOG);
		textArea.setText("-------\n");
		return textArea;
	}

	public void setStatus(String statusText, int lastPrice ,String bidder) {
		auctionStatus.setText(statusText);
		logArea.append(String.format(AUCTION_LOG_FORMAT, bidder, statusText, lastPrice));
	}
}
