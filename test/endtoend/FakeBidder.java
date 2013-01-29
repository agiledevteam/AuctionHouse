package endtoend;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import auctionhouse.Main;

public class FakeBidder implements MessageListener {

	private Chat chat;
	private BlockingQueue<Message> messages = new LinkedBlockingQueue<Message>();
	private XMPPConnection connection;
	private final String jid;
	private final String password;

	public FakeBidder(String bidderJId, String bidderPassword) {
		this.jid = bidderJId;
		this.password = bidderPassword;
	}

	public void join() throws Exception {
		ConnectionConfiguration config = new ConnectionConfiguration(
				Config.host, 5222);
		connection = new XMPPConnection(config);
		connection.connect();
		connection.login(jid, password);

		chat = connection.getChatManager().createChat(Config.auctionId, this);
		chat.sendMessage(Main.JOIN_COMMAND_FORMAT);
	}

	public void receivedClosedMessage() throws InterruptedException {
		Message message = messages.poll(5, TimeUnit.SECONDS);
		assertNotNull(message);
		assertThat(message.getBody(), containsString("CLOSE"));
	}

	public void receivedPriceMessage(int price, int increment, String bidderId)
			throws InterruptedException {
		Message message = messages.poll(5, TimeUnit.SECONDS);
		assertNotNull(message);
		assertThat(message.getBody(),
				allOf(containsString("PRICE"), containsString(bidderId)));
	}

	@Override
	public void processMessage(Chat chat, Message message) {
		messages.add(message);
	}

	public void bid(int price) throws XMPPException {
		Logger.getLogger("FakeBidder").info(
				Thread.currentThread().getId() + ") bid" + getId() + ", "
						+ price);
		chat.sendMessage(String.format(Main.BID_COMMAND_FORMAT, price));
	}

	public void stop() {
		if (connection != null)
			connection.disconnect();
	}

	public String getId() {
		return jid.split("@")[0];
	}

}
