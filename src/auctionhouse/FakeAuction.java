package auctionhouse;

import java.util.Timer;
import java.util.TimerTask;

final class FakeAuction implements Auction {
	private final String bidderId;
	static Timer timer;
	static {
		timer = new Timer();
	}
	private AuctionBroker broker;

	FakeAuction(String bidderId, AuctionBroker broker) {
		this.bidderId = bidderId;
		this.broker = broker;
	}

	@Override
	public void currentPrice(int currentPrice, int increment, String bidder) {
		if (bidder.equals(bidderId))
			return;
		final int bid = currentPrice + increment;
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				broker.onBid(bidderId, bid);
			}
		}, 1000);
	}

	@Override
	public void auctionClosed() {
	}
}