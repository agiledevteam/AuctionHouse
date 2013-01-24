package auctionhouse;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

public class Main implements UserActionListener, AuctionBroker {
	public static final String JOIN_COMMAND_FORMAT = "SOLVersion: 1.1; Command: JOIN;";
	public static final String BID_COMMAND_FORMAT = "SOLVersion: 1.1; Command: BID; Price: %d;";
	public static final String PRICE_EVENT_FORMAT = "SOLVersion: 1.1; Event: PRICE; CurrentPrice: %d; Increment: %d; Bidder: %s;";
	public static final String CLOSE_EVENT_FORMAT = "SOLVersion: 1.1; Event: CLOSE;";

	private MainWindow ui;

	protected ArrayList<AuctionCommandHandler> brokerList = new ArrayList<AuctionCommandHandler>();
	private String winner = "Broker";
	private int currentPrice = 1000;
	private int increment = 50;
	

	
	public Main() throws Exception {

		SwingUtilities.invokeAndWait(new Runnable() {
			@Override
			public void run() {
				ui = new MainWindow(Main.this);
			}
		});

		ConnectionConfiguration config = new ConnectionConfiguration(
				"localhost", 5222);
		XMPPConnection connection = new XMPPConnection(config);
		connection.connect();
		connection.login("auction-item-54321@localhost", "auction");
		connection.getChatManager().addChatListener(new ChatManagerListener() {
			@Override
			public void chatCreated(Chat chat, boolean arg1) {
				XMPPAuction auction = new XMPPAuction(chat);
				AuctionBidderHandler broker = new AuctionBidderHandler(auction, ui, Main.this);
				Main.this.brokerList.add(broker);
				chat.addMessageListener(new AuctionCommandTranslator(
						"item-54321", broker));
			}
		});

		this.disconnectWhenUICloses(connection);
	}

	public static void main(String... args) throws Exception {
		new Main();
	}

	private void disconnectWhenUICloses(final XMPPConnection connection) {
		ui.addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {
				connection.disconnect();
			}
		});
	}

	@Override
	public void closeAuction() {
		for (AuctionCommandHandler broker : brokerList) {
			broker.sendClose();
		}
	}

	@Override
	public int getPrice() {
		return currentPrice;
	}

	@Override
	public String getWinner() {
		return winner;
	}

	@Override
	public int getIncrement() {
		return increment;
	}

	@Override
	public void updateBid(int price, String bidderId) {
		this.currentPrice = price;
		this.winner = bidderId;
		
		notifyPrice();
	}

	private void notifyPrice() {
		for (AuctionCommandHandler broker : brokerList) {
			broker.sendPrice(currentPrice, increment, winner);
		}
	}

}
