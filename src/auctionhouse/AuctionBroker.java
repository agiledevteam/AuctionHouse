package auctionhouse;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class AuctionBroker implements AuctionCommandHandler {

	private static class AuctionBucket {
		private Map<String, Auction> auctions = new HashMap<String, Auction>();

		public void add(String bidderId, Auction newAuction) {
			auctions.put(bidderId, newAuction);
		}

		public void sendPrice(int currentPrice, int increment, String winner) {
			Logger.getLogger("han").info(
					String.format("AuctionBucket.sendPrice(%d,%d,%s)",
							currentPrice, increment, winner));
			for (Auction auction : auctions.values()) {
				auction.currentPrice(currentPrice, increment, winner);
			}
		}

		public void sendClose() {
			for (Auction auction : auctions.values()) {
				auction.auctionClosed();
			}
		}
	}

	AuctionBucket auctionList = new AuctionBucket();

	private String winner = "Broker";
	private int currentPrice = 1000;
	private int increment = 50;
	private BrokerListener listener;

	private boolean isClosed = false;

	public AuctionBroker(BrokerListener listener) {
		this.listener = listener;
	}

	public void setStartPrice(int startPrice, int increment) {
		this.currentPrice = startPrice;
		this.increment = increment;
	}

	public void sendClose() {
		auctionList.sendClose();
		isClosed  = true;
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
	public void onBid(String bidderId, int price) {
		if (isClosed) {
			return;
		}
		if (price <= currentPrice) {
			return;
		}
		Logger.getLogger("AuctionBroker").info(
				Thread.currentThread().getId() + ") updateBid: " + price + ", "
						+ bidderId);
		this.currentPrice = price;
		this.winner = bidderId;

		auctionList.sendPrice(currentPrice, increment, winner);
		listener.setStatus("Bidding", winner, price);
		listener.bidderChanged(new BidderSnapshot(bidderId, Integer
				.toString(price)));
	}

	public String getWinner() {
		return winner;
	}
}
