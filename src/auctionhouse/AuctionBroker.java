package auctionhouse;

import java.util.logging.Logger;

public class AuctionBroker implements AuctionCommandHandler {

	AuctionList auctionList = new AuctionList();

	private final BrokerListener listener;
	private boolean isClosed = false;
	private String winner = "Broker";
	private int currentPrice = 1000;
	private int increment = 50;

	public AuctionBroker(BrokerListener listener) {
		this.listener = listener;
	}

	public void setStartPrice(int startPrice, int increment) {
		this.currentPrice = startPrice;
		this.increment = increment;
	}

	public void sendClose() {
		auctionList.sendClose();
		isClosed = true;
		listener.setStatus("Closed", winner, currentPrice);
	}

	@Override
	public void onJoin(String bidderId, Auction auction) {
		auctionList.add(bidderId, auction);
		auction.currentPrice(currentPrice, increment, winner);
		listener.setStatus("Joined", winner, currentPrice);
		listener.bidderAdded(new BidderSnapshot(bidderId, "Joined"));
	}

	@Override
	synchronized public void onBid(String bidderId, int price) {
		Logger.getLogger("AuctionBroker").info(
				Thread.currentThread().getId() + ") onBid: " + price + ", "
						+ bidderId);
		if (isClosed) {
			return;
		}
		if (price > currentPrice) {
			this.currentPrice = price;
			this.winner = bidderId;

			auctionList.sendPrice(currentPrice, increment, winner);
			listener.setStatus("Winner", winner, price);
		}
		listener.bidderChanged(new BidderSnapshot(bidderId, Integer
				.toString(price)));
	}

	public void startAuction(AuctionHouse auctionHouse) {
		try {
			auctionHouse.start(this);
			listener.setStatus("Started", "", 0);
		} catch (AuctionStartError e) {
			listener.setStatus("Server not ready", "", 0);
		}
	}
}
