package auctionhouse;

import java.util.HashMap;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

public class AuctionCommandTranslator implements MessageListener {

	String itemId;
	AuctionCommandListener listener;

	public AuctionCommandTranslator(String itemId, AuctionCommandListener listener) {
		this.itemId = itemId;
		this.listener = listener;
	}

	@Override
	public void processMessage(Chat chat, Message message) {
		HashMap<String, String> event = unpackEventFrom(message);
		String commandType = event.get("Command");

		if ("JOIN".equals(commandType)) {
			try {
				chat.sendMessage(String
						.format("SOLVersion: 1.1; Event: PRICE; CurrentPrice: %d; Increment: %d; Bidder: %s",
								1000, 50, ""));
			} catch (XMPPException e) {
				e.printStackTrace();
			}
			listener.setStatus("Joined");
		}
		else if ("BID".equals(commandType)) {
			listener.setStatus("Bidded");
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
