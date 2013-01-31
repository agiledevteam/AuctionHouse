package auctionhouse;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.TableCellRenderer;

public class MainWindow extends JFrame implements BrokerListener {

	private static final long serialVersionUID = -1320680914079154752L;

	private final BidderTableModel bidders = new BidderTableModel();
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
				String host = hostField.getText();
				listener.openAuction(host, 5222,
						"auction-item-54321", "auction");
				setStatus("Started", "Broker", 1000);
			}
		});
		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				listener.closeAuction();
				setStatus("Closed", "", 0);
			}
		});
		setVisible(true);
	}

	private JTable bidderTable() {
		final BidderRenderer renderer = new BidderRenderer();
		JTable table = new JTable() {
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
		lblHost.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
		lblHost.setBounds(10, 13, 104, 15);
		getContentPane().add(lblHost);
		
		incrementField = new JTextField();
		incrementField.setText("100");
		incrementField.setBounds(120, 72, 316, 21);
		getContentPane().add(incrementField);
		
		startButton = new JButton("Start");
		startButton.setBounds(668, 9, 104, 23);
		startButton.setName(START_BUTTON);
		getContentPane().add(startButton);
		
		JLabel lblStartPrice = new JLabel("Start Price:");
		lblStartPrice.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
		lblStartPrice.setBounds(10, 44, 104, 15);
		getContentPane().add(lblStartPrice);
		
		startPriceField = new JTextField();
		startPriceField.setText("1000");
		startPriceField.setBounds(120, 41, 316, 21);
		getContentPane().add(startPriceField);
		startPriceField.setColumns(10);
		
		stopButton = new JButton("Stop");
		stopButton.setBounds(668, 40, 104, 23);
		stopButton.setName(CLOSE_BUTTON);
		getContentPane().add(stopButton);
		
		JLabel lblIncrement = new JLabel("Increment:");
		lblIncrement.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
		lblIncrement.setBounds(10, 75, 104, 15);
		getContentPane().add(lblIncrement);
		
		hostField = new JTextField();
		hostField.setText("localhost");
		hostField.setBounds(120, 10, 316, 21);
		getContentPane().add(hostField);
		
		BufferedImage myPicture;
		try {
			myPicture = ImageIO.read(new File("photo.jpg"));
			itemImageLabel = new JLabel(new ImageIcon( myPicture ));
		} catch (IOException e) {
			itemImageLabel = new JLabel("Image N/A");
		}
		
		itemImageLabel.setBackground(new Color(255, 0, 0));
		itemImageLabel.setBounds(12, 120, 102, 100);
		getContentPane().add(itemImageLabel);
		
		JLabel lblNewLabel = new JLabel("임베디드 C를 위한 TDD");
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		lblNewLabel.setBounds(120, 120, 316, 100);
		getContentPane().add(lblNewLabel);
		
		BufferedImage winnerPicture;
		JLabel winnerPictureLabel;
		try {
			winnerPicture = ImageIO.read(new File("images/avatars/frog.jpg"));
			winnerPictureLabel = new JLabel(new ImageIcon( winnerPicture ));
		} catch (IOException e) {
			winnerPictureLabel = new JLabel("N/A");
		}
		winnerPictureLabel.setBounds(672, 120, 100, 100);
		getContentPane().add(winnerPictureLabel);
		
		priceLabel = new JLabel("0");
		priceLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		priceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		priceLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 32));
		priceLabel.setBounds(448, 170, 212, 50);
		priceLabel.setName(MainWindow.AUCTION_PRICE);
		getContentPane().add(priceLabel);
		
		winnerLabel = new JLabel("winnerId");
		winnerLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
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
		lblWinner.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
		lblWinner.setBounds(448, 120, 212, 15);
		getContentPane().add(lblWinner);
	}

	@Override
	public void setStatus(String status, String winner, int price) {
		statusLabel.setText(status);
		winnerLabel.setText(winner);
		priceLabel.setText(String.valueOf(price));
	}

	@Override
	public void bidderAdded(final BidderSnapshot bidderSnapshot) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				bidders.addBidder(bidderSnapshot);
			}
		});
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
