package auctionhouse;

import javax.swing.SwingUtilities;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

public class Main implements MessageListener, UserActionListener {

	private MainWindow ui;

	protected Chat chat;

	public Main() throws Exception {
		SwingUtilities.invokeAndWait(new Runnable() {
			@Override
			public void run() {
				ui = new MainWindow(Main.this);
			}
		});
		
		ConnectionConfiguration config = new ConnectionConfiguration("danielkang-01", 5222);
		XMPPConnection connection = new XMPPConnection(config);
		connection.connect();
		connection.login("auction-item-54321", "auction");
		connection.getChatManager().addChatListener(new ChatManagerListener() {
			@Override
			public void chatCreated(Chat chat, boolean arg1) {
				Main.this.chat = chat;
				chat.addMessageListener(Main.this);
			}
		});
	}

	public static void main(String... args) throws Exception {
		new Main();

	}

	@Override
	public void processMessage(Chat chat, Message message) {
		ui.setStatus("Joined");
	}

	@Override
	public void closeAuction() {
		try {
			chat.sendMessage("");
		} catch (XMPPException e) {
			e.printStackTrace();
		}
	}

}
