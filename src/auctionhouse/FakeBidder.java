package auctionhouse;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.logging.Logger;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;


public class FakeBidder {
	public enum BidderCommand {
	}

	private BlockingQueue<BidderCommand> queue;
	private String bidderId;
	private String host;
	private Chat chat;

	public FakeBidder(String host, String bidderId) {
		this.host = host;
		this.bidderId = bidderId;
		queue = new SynchronousQueue<BidderCommand>();
		new Thread(new Runnable() {

			@Override
			public void run() {
				join();
			}

		}).start();
	}

	protected void join() {
		ConnectionConfiguration config = new ConnectionConfiguration(host, 5222);
		XMPPConnection connection = new XMPPConnection(config);
		try {
			Logger.getLogger("han").info(bidderId + " tries to connect and login to " + host);
			connection.connect();
			connection.login(bidderId + "@localhost", "bidder");
			chat = connection.getChatManager().createChat(
					"auction-item-54321@localhost", null);
			chat.sendMessage(Main.JOIN_COMMAND_FORMAT);
		} catch (XMPPException e) {
			e.printStackTrace();
		}
	}

}
