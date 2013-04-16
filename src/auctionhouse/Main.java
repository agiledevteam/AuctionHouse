package auctionhouse;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.SwingUtilities;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

public class Main implements UserActionListener {
	public static final String JOIN_COMMAND_FORMAT = "SOLVersion: 1.1; Command: JOIN;";
	public static final String BID_COMMAND_FORMAT = "SOLVersion: 1.1; Command: BID; Price: %d;";
	public static final String PRICE_EVENT_FORMAT = "SOLVersion: 1.1; Event: PRICE; CurrentPrice: %d; Increment: %d; Bidder: %s;";
	public static final String CLOSE_EVENT_FORMAT = "SOLVersion: 1.1; Event: CLOSE;";

	private MainWindow ui;
	private AuctionBroker broker;
	private int bidderCount;

	public Main(final boolean testMode) throws Exception {

		SwingUtilities.invokeAndWait(new Runnable() {
			@Override
			public void run() {
				ui = new MainWindow(Main.this, testMode);
				broker = new AuctionBroker(ui);
			}
		});
	}

	public static void main(String... args) throws Exception {
		boolean testMode = false;
		for (String arg : args) {
			if (arg.equals("-test")) {
				testMode = true;
			}
		}
		new Main(testMode);
	}

	private void disconnectWhenUICloses(final XMPPConnection connection) {
		ui.addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {
				connection.disconnect();
			}
		});
	}

	@Override
	public void openAuction(String serverAddress, int port, String itemId,
			String passwd, int startPrice, int increment) {
		broker.setStartPrice(startPrice, increment);
		ConnectionConfiguration config = new ConnectionConfiguration(
				serverAddress, port);
		XMPPConnection connection = new XMPPConnection(config);
		try {
			connection.connect();
			connection.login(itemId, passwd);
		} catch (XMPPException e) {
			e.printStackTrace();
		}
		connection.getChatManager().addChatListener(new ChatManagerListener() {
			@Override
			public void chatCreated(Chat chat, boolean arg1) {
				String bidderId = idFrom(chat.getParticipant());
				XMPPAuction auction = new XMPPAuction(chat);
				chat.addMessageListener(new AuctionCommandTranslator(broker,
						auction));
			}
		});

		this.disconnectWhenUICloses(connection);
	}

	public static String idFrom(String jid) {
		return jid.split("@")[0];
	}

	@Override
	public void closeAuction() {
		broker.sendClose();
	}

	@Override
	public void addFakeBidder() {
		final String bidderId = nextFakeBidderId();
		broker.onJoin(bidderId, new Auction() {
			Timer timer = new Timer();
			TimerTask task;

			@Override
			public void currentPrice(int currentPrice, int increment,
					String bidder) {
				final int bid = currentPrice + increment;
				if (task != null) {
					task.cancel();
				}
				if (bidder.equals(bidderId))
					return;
				task = new TimerTask() {
					@Override
					public void run() {
						broker.onBid(bidderId, bid);
					}
				};
				timer.schedule(task, 1000);
			}

			@Override
			public void auctionClosed() {
				if (task != null) {
					task.cancel();
				}
			}
		});
	}

	private String nextFakeBidderId() {
		bidderCount++;
		return String.format("bidder-%d", bidderCount);
	}

}