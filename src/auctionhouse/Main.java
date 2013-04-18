package auctionhouse;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.SwingUtilities;

import auctionhouse.ui.MainWindow;
import auctionhouse.xmpp.XMPPAuctionHouse;

public class Main implements UserActionListener {
	private MainWindow ui;
	private int bidderCount;
	private AuctionBroker broker;

	public Main(final boolean testMode) throws Exception {

		SwingUtilities.invokeAndWait(new Runnable() {
			@Override
			public void run() {
				ui = new MainWindow(Main.this, testMode);
				broker = new AuctionBroker(ui);
			}
		});
	}

	public static void main(String... args) throws Exception {
		boolean testMode = false;
		for (String arg : args) {
			if (arg.equals("-test")) {
				testMode = true;
			}
		}
		new Main(testMode);
	}

	private void disconnectWhenUICloses(final AuctionHouse connection) {
		ui.addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {
				connection.stop();
			}
		});
	}

	@Override
	public void startAuction(String serverAddress, int port, String itemId,
			String password, int startPrice, int increment) {
		XMPPAuctionHouse auctionHouse = new XMPPAuctionHouse(serverAddress,
				itemId, password);
		disconnectWhenUICloses(auctionHouse);

		broker.setStartPrice(startPrice, increment);
		broker.startAuction(auctionHouse);
	}

	@Override
	public void stopAuction() {
		broker.sendClose();
	}

	@Override
	public void addFakeBidder() {
		final String bidderId = nextFakeBidderId();
		broker.onJoin(bidderId, new FakeAuction(bidderId, broker));
	}

	private String nextFakeBidderId() {
		bidderCount++;
		return String.format("bidder-%d", bidderCount);
	}

}