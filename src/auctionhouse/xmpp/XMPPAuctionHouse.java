package auctionhouse.xmpp;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import auctionhouse.AuctionCommandHandler;
import auctionhouse.AuctionHouse;
import auctionhouse.AuctionStartError;

public class XMPPAuctionHouse implements AuctionHouse {

	private String host;
	private String itemId;
	private String password;
	private XMPPConnection connection;
	private AuctionCommandTranslator auctionCommandTranslator;

	public XMPPAuctionHouse(String host, String itemId, String password) {
		this.host = host;
		this.itemId = itemId;
		this.password = password;
	}

	@Override
	public void start(final AuctionCommandHandler broker)
			throws AuctionStartError {
		auctionCommandTranslator = new AuctionCommandTranslator(broker);
		connection = connect();
		connection.getChatManager().addChatListener(new ChatManagerListener() {
			@Override
			public void chatCreated(Chat chat, boolean locallyCreated) {
				chat.addMessageListener(auctionCommandTranslator);
			}
		});
	}

	private XMPPConnection connect() throws AuctionStartError {
		ConnectionConfiguration config = new ConnectionConfiguration(host, 5222);
		XMPPConnection connection = new XMPPConnection(config);
		try {
			connection.connect();
			connection.login(itemId, password);
			return connection;
		} catch (XMPPException e) {
			throw new AuctionStartError(e);
		}
	}

	@Override
	public void stop() {
		if (connection != null) {
			connection.disconnect();
		}
	}

}
