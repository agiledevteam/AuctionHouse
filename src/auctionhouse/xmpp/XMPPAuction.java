package auctionhouse.xmpp;

import java.util.logging.Logger;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPException;

import auctionhouse.Auction;

public class XMPPAuction implements Auction {

	private Chat chat;
	public static final String PRICE_EVENT_FORMAT = "SOLVersion: 1.1; Event: PRICE; CurrentPrice: %d; Increment: %d; Bidder: %s;";
	public static final String CLOSE_EVENT_FORMAT = "SOLVersion: 1.1; Event: CLOSE;";
	public static final String BID_COMMAND_FORMAT = "SOLVersion: 1.1; Command: BID; Price: %d;";
	public static final String JOIN_COMMAND_FORMAT = "SOLVersion: 1.1; Command: JOIN;";

	public XMPPAuction(Chat chat) {
		this.chat = chat;
	}

	@Override
	public void currentPrice(int currentPrice, int increment, String bidder) {
		Logger.getLogger("han").info(
				String.format("XMPPAuction.sendPrice(%d,%d,%s)", currentPrice,
						increment, bidder));
		sendMessage(String.format(XMPPAuction.PRICE_EVENT_FORMAT, currentPrice,
				increment, bidder));
	}

	@Override
	public void auctionClosed() {
		sendMessage(XMPPAuction.CLOSE_EVENT_FORMAT);
	}

	private void sendMessage(String message) {
		try {
			chat.sendMessage(message);
		} catch (XMPPException e) {
			e.printStackTrace();
		}
	}

}
