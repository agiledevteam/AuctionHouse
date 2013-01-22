package endtoend;

import static junit.framework.Assert.assertNotNull;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Message;

public class FakeBidder implements MessageListener {
	
	private Chat chat;
	private BlockingQueue<Message> messages = new LinkedBlockingQueue<Message>();

	public void join() throws Exception {
		ConnectionConfiguration config = new ConnectionConfiguration("danielkang-01", 5222);
		XMPPConnection connection = new XMPPConnection(config);
		connection.connect();
		connection.login("bidder-1", "bidder");
		
		chat = connection.getChatManager().createChat("auction-item-54321@danielkang-01", this);
		chat.sendMessage("");
	}

	public void receivedClosedMessage() throws InterruptedException {
		Message message = messages.poll(5, TimeUnit.SECONDS);
		assertNotNull(message);
	}

	@Override
	public void processMessage(Chat chat, Message message) {
		messages.add(message);
	}

}
