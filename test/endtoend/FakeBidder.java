package endtoend;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

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
	
	public void join() throws Exception {
		ConnectionConfiguration config = new ConnectionConfiguration("localhost", 5222);
		connection = new XMPPConnection(config);
		connection.connect();
		connection.login("bidder-1", "bidder");
		
		chat = connection.getChatManager().createChat("auction-item-54321@localhost", this);
		chat.sendMessage(Main.JOIN_COMMAND_FORMAT);
	}

	public void receivedClosedMessage() throws InterruptedException {
		Message message = messages.poll(5, TimeUnit.SECONDS);
		assertNotNull(message);
	}

	@Override
	public void processMessage(Chat chat, Message message) {
		messages.add(message);
	}

	public void receivedPriceMessage() throws InterruptedException {
		Message message = messages.poll(5, TimeUnit.SECONDS);
		assertNotNull(message);
	}

	public void bid() throws XMPPException {
		chat.sendMessage(String.format(Main.BID_COMMAND_FORMAT, 1050));
	}

	public void stop() {
		connection.disconnect();
	}

}
