package auctionhouse;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;

public class MainWindow extends JFrame implements BrokerListener {

	private static final long serialVersionUID = -1320680914079154752L;
	private final JLabel priceLabel = priceLabel();
	private final JLabel statusLabel = statusLabel();
	private final JEditorPane priceEditorPane = priceEditorPane(1000);
	private final JEditorPane incrementEditorPane = incrementEditorPane(100);
	private final JButton startButton = startButton();
	private final JButton closeButton = closeButton();
	private final JLabel winnerLabel = winnerLabel();
	private final JTable bidderTable = bidderTable();

	public static final String START_BUTTON = "StartButton";
	public static final String CLOSE_BUTTON = "CloseButton";
	private static final String PRICE_EDIT = "AuctionPrice";
	private static final String INCREMENT_EDIT = "AuctionIncrement";

	public static final String AUCTION_PRICE = "AuctionPrice";
	public static final String AUCTION_STATUS = "AuctionStatus";
	public static final String AUCTION_HOUSE = "AuctionHouse";
	public static final String AUCTION_LOG = "AuctionLog";
	public static final String WINNER_LABEL = "WinnerLabel";
	public static final String BIDDER_TABLE = "BidderTable";

	public static final String AUCTION_LOG_FORMAT = "%s is %s at %d\n";

	public MainWindow(final UserActionListener listener) {
		super("Auction House");
		setName(MainWindow.AUCTION_HOUSE);
		fillContentPane();
		setMinimumSize(new Dimension(800, 600));
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				listener.openAuction("localhost", 5222,
						"auction-item-54321", "auction");
				setStatus("Started", "", Integer.valueOf(priceEditorPane.getText()));
			}
		});
		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				listener.closeAuction();
				setStatus("Closed", winnerLabel.getText(), Integer.valueOf(priceLabel.getText()));
			}
		});
		setVisible(true);
	}

	private JTable bidderTable() {
		JTable table = new JTable();
		table.setName(BIDDER_TABLE);
		table.setModel(new BidderTableModel());
		return table;
	}

	private void fillContentPane() {
		final Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
		
		panel.add(new JLabel("Start Price"));
		panel.add(priceEditorPane);

		panel.add(new JLabel("Increment"));
		panel.add(incrementEditorPane);

		panel.add(startButton);
		panel.add(closeButton);

		panel.add(new JLabel("Winner is "));
		panel.add(winnerLabel);

		panel.add(new JLabel("Price is "));
		panel.add(priceLabel);

		panel.add(new JLabel("Status "));
		panel.add(statusLabel);
		
		contentPane.add(panel, BorderLayout.NORTH);
		contentPane.add(bidderTable, BorderLayout.CENTER);
	}

	private static JLabel priceLabel() {
		JLabel label = new JLabel();
		label.setName(MainWindow.AUCTION_PRICE);
		label.setText("0");
		return label;
	}

	private static JLabel statusLabel() {
		JLabel label = new JLabel();
		label.setName(MainWindow.AUCTION_STATUS);
		label.setText("Not Started");
		return label;
	}

	private static JEditorPane priceEditorPane(int defaultPrice) {
		JEditorPane editorPane = new JEditorPane();
		editorPane.setName(MainWindow.PRICE_EDIT);
		editorPane.setText(String.valueOf(defaultPrice));
		return editorPane;
	}

	private static JEditorPane incrementEditorPane(int defaultIncrement) {
		JEditorPane editorPane = new JEditorPane();
		editorPane.setName(MainWindow.INCREMENT_EDIT);
		editorPane.setText(String.valueOf(defaultIncrement));
		return editorPane;
	}

	private static JButton startButton() {
		JButton button = new JButton();
		button.setName(MainWindow.START_BUTTON);
		button.setText("Start");
		return button;
	}

	private static JButton closeButton() {
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
	public void setStatus(String status, String winner, int price) {
		statusLabel.setText(status);
		winnerLabel.setText(winner);
		priceLabel.setText(String.valueOf(price));
	}
}
