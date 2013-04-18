package auctionhouse.xmpp;

import java.util.HashMap;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

import auctionhouse.AuctionCommandHandler;

public class AuctionCommandTranslator implements MessageListener {

	private AuctionCommandHandler handler;

	public AuctionCommandTranslator(AuctionCommandHandler handler) {
		this.handler = handler;
	}

	@Override
	public void processMessage(Chat chat, Message message) {
		HashMap<String, String> event = unpackEventFrom(message);
		String commandType = event.get("Command");

		if ("JOIN".equals(commandType)) {
			handler.onJoin(bidder(chat), new XMPPAuction(chat));
		} else if ("BID".equals(commandType)) {
			handler.onBid(bidder(chat), Integer.parseInt(event.get("Price")));
		}
	}

	private String bidder(Chat chat) {
		return chat.getParticipant().split("@")[0];
	}

	private HashMap<String, String> unpackEventFrom(Message message) {
		HashMap<String, String> event = new HashMap<String, String>();
		for (String element : message.getBody().split(";")) {
			String[] pair = element.split(":");
			event.put(pair[0].trim(), pair[1].trim());
		}
		return event;
	}

}
