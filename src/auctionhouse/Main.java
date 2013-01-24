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

public class Main implements UserActionListener, AuctionBrokerManager {
	public static final String JOIN_COMMAND_FORMAT = "SOLVersion: 1.1; Command: JOIN;";
	public static final String BID_COMMAND_FORMAT = "SOLVersion: 1.1; Command: BID; Price: %d;";
	public static final String PRICE_EVENT_FORMAT = "SOLVersion: 1.1; Event: PRICE; CurrentPrice: %d; Increment: %d; Bidder: %s;";
	public static final String CLOSE_EVENT_FORMAT = "SOLVersion: 1.1; Event: CLOSE;";

	private MainWindow ui;

	protected ArrayList<Chat> chatList = new ArrayList<Chat>();

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
				Main.this.chatList.add(chat);
				XMPPAuction auction = new XMPPAuction(chat);
				chat.addMessageListener(new AuctionCommandTranslator(
						"item-54321", new AuctionBroker(auction, ui, Main.this)));
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
		try {
			for (Chat chat : chatList) {
				chat.sendMessage(CLOSE_EVENT_FORMAT);
			}
		} catch (XMPPException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void broadcastPrice(int currentPrice, int increment, String winner) {
		// TODO Auto-generated method stub
		
	}

}
