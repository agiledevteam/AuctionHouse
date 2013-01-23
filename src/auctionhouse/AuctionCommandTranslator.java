package auctionhouse;

import java.util.HashMap;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

public class AuctionCommandTranslator implements MessageListener {

	String itemId;
	AuctionCommandHandler handler;

	public AuctionCommandTranslator(String itemId, AuctionCommandHandler handler) {
		this.itemId = itemId;
		this.handler = handler;
	}

	@Override
	public void processMessage(Chat chat, Message message) {
		HashMap<String, String> event = unpackEventFrom(message);
		String commandType = event.get("Command");

		if ("JOIN".equals(commandType)) {
			handler.onJoin();
		}
		else if ("BID".equals(commandType)) {
			handler.onBid();
		}
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
