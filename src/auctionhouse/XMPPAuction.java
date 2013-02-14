package auctionhouse;

import java.util.logging.Logger;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPException;

public class XMPPAuction implements Auction {

	private Chat chat;

	public XMPPAuction(Chat chat) {
		this.chat = chat;
	}

	@Override
	public void sendPrice(int currentPrice, int increment, String bidder) {
		Logger.getLogger("han").info(
				String.format("XMPPAuction.sendPrice(%d,%d,%s)", currentPrice,
						increment, bidder));
		sendMessage(String.format(Main.PRICE_EVENT_FORMAT, currentPrice,
				increment, bidder));
	}

	@Override
	public void closeAuction() {
		sendMessage(Main.CLOSE_EVENT_FORMAT);
	}

	private void sendMessage(String message) {
		try {
			chat.sendMessage(message);
		} catch (XMPPException e) {
			e.printStackTrace();
		}
	}

}
