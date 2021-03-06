package auctionhouse.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.TableCellRenderer;

import auctionhouse.BidderSnapshot;
import auctionhouse.BrokerListener;
import auctionhouse.UserActionListener;

public class MainWindow extends JFrame implements BrokerListener {

	private static final long serialVersionUID = -1320680914079154752L;

	private final BidderTableModel bidders = new BidderTableModel();
	private final JTable bidderTable = bidderTable();

	public static final String START_BUTTON = "StartButton";
	public static final String CLOSE_BUTTON = "CloseButton";
	private static final String PRICE_EDIT = "AuctionPrice";
	public static final String INCREMENT_EDIT = "FieldIncrement";
	public static final String HOST_EDIT = "FieldHost";

	public static final String AUCTION_PRICE = "AuctionPrice";
	public static final String AUCTION_STATUS = "AuctionStatus";
	public static final String AUCTION_HOUSE = "AuctionHouseMainWindow";
	public static final String AUCTION_LOG = "AuctionLog";
	public static final String WINNER_LABEL = "WinnerLabel";
	public static final String BIDDER_TABLE = "BidderTable";

	public static final String AUCTION_LOG_FORMAT = "%s is %s at %d\n";
	public static final String ADD_BUTTON = "AddButton";
	private JTextField startPriceField;
	private JTextField hostField;
	private JTextField incrementField;
	private JButton startButton;
	private JButton stopButton;
	private JLabel itemImageLabel;
	private JLabel priceLabel;
	private JLabel winnerLabel;
	private JLabel statusLabel;
	private JLabel lblWinner;

	private JLabel winnerPictureLabel;

	private JButton addButton;

	private boolean testMode;

	public MainWindow(final UserActionListener listener, boolean testMode) {
		super("Auction House");
		this.testMode = testMode;
		setName(MainWindow.AUCTION_HOUSE);
		fillContentPane();
		pack();
		setSize(new Dimension(800, 600));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String host = hostField.getText();
				int startPrice = Integer.valueOf(startPriceField.getText());
				int increment = Integer.valueOf(incrementField.getText());
				listener.startAuction(host, 5222,
						"auction-item-54321", "auction", startPrice, increment);
			}
		});
		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				listener.stopAuction();
			}
		});
		addButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				listener.addFakeBidder();
			}
		});
		setVisible(true);
	}

	private JTable bidderTable() {
		final BidderRenderer renderer = new BidderRenderer();
		JTable table = new JTable() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -8695477405858755029L;

			@Override
			public TableCellRenderer getCellRenderer(int row, int column) {
				return renderer;
			}
		};
		table.setName(BIDDER_TABLE);
		table.setModel(bidders);
		table.setTableHeader(null);
		return table;
	}

	private void fillContentPane() {
		getContentPane().setLayout(null);
		JLabel lblHost = new JLabel("Host:");
		lblHost.setBounds(10, 13, 104, 15);
		getContentPane().add(lblHost);

		incrementField = new JTextField();
		incrementField.setName(INCREMENT_EDIT);
		incrementField.setText("100");
		incrementField.setBounds(120, 72, 316, 21);
		getContentPane().add(incrementField);

		startButton = new JButton("Start");
		startButton.setBounds(668, 9, 104, 23);
		startButton.setName(START_BUTTON);
		getContentPane().add(startButton);

		JLabel lblStartPrice = new JLabel("Start Price:");

		lblStartPrice.setBounds(10, 44, 104, 15);
		getContentPane().add(lblStartPrice);

		startPriceField = new JTextField();
		startPriceField.setName(PRICE_EDIT);
		startPriceField.setText("1000");
		startPriceField.setBounds(120, 41, 316, 21);
		getContentPane().add(startPriceField);
		startPriceField.setColumns(10);

		stopButton = new JButton("Stop");
		stopButton.setBounds(668, 40, 104, 23);
		stopButton.setName(CLOSE_BUTTON);
		getContentPane().add(stopButton);

		addButton = new JButton("Add Fake");
		addButton.setBounds(668, 71, 104, 23);
		addButton.setName(ADD_BUTTON);
		if (testMode) {
			getContentPane().add(addButton);
		}
		
		JLabel lblIncrement = new JLabel("Increment:");
		lblIncrement.setBounds(10, 75, 104, 15);
		getContentPane().add(lblIncrement);

		hostField = new JTextField();
		hostField.setName(HOST_EDIT);
		hostField.setText("localhost");
		hostField.setBounds(120, 10, 316, 21);
		getContentPane().add(hostField);

		itemImageLabel = new JLabel(Avatar.getIconLarge("photo"));
		itemImageLabel.setBackground(new Color(255, 0, 0));
		itemImageLabel.setBounds(12, 120, 102, 100);
		getContentPane().add(itemImageLabel);

		JLabel lblNewLabel = new JLabel("임베디드 C를 위한 TDD");
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
		lblNewLabel.setBounds(120, 120, 316, 100);
		getContentPane().add(lblNewLabel);

		winnerPictureLabel = new JLabel("N/A");
		winnerPictureLabel.setBounds(672, 120, 100, 100);
		getContentPane().add(winnerPictureLabel);

		priceLabel = new JLabel("0");
		priceLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		priceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		priceLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 32));
		priceLabel.setBounds(448, 170, 212, 50);
		priceLabel.setName(MainWindow.AUCTION_PRICE);
		getContentPane().add(priceLabel);

		winnerLabel = new JLabel("winnerId");
		winnerLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
		winnerLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		winnerLabel.setBounds(448, 145, 212, 15);
		winnerLabel.setName(WINNER_LABEL);
		getContentPane().add(winnerLabel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 230, 760, 325);
		getContentPane().add(scrollPane);

		scrollPane.setViewportView(bidderTable);

		statusLabel = new JLabel("Not Started");
		statusLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		statusLabel.setBounds(448, 75, 324, 15);
		statusLabel.setName(AUCTION_STATUS);
		getContentPane().add(statusLabel);

		lblWinner = new JLabel("Winner");
		lblWinner.setHorizontalAlignment(SwingConstants.RIGHT);
		lblWinner.setBounds(448, 120, 212, 15);
		getContentPane().add(lblWinner);
	}

	@Override
	public void statusChanged(String status, String winner, int price) {
		winnerPictureLabel.setIcon(Avatar.getIconLarge(winner));
		statusLabel.setText(status);
		winnerLabel.setText(winner);
		priceLabel.setText(String.valueOf(price));
	}

	@Override
	public void bidderChanged(final BidderSnapshot bidderSnapshot) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				bidders.setBidder(bidderSnapshot);
			}
		});
	}
}
