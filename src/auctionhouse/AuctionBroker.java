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
			for (Auction auction : auctions.values()) {
				auction.sendPrice(currentPrice, increment, winner);
			}
		}
		
		public void sendClose() {
			for (Auction auction : auctions.values()) {
				auction.closeAuction();
			}
		}
	}

	AuctionBucket auctionList = new AuctionBucket();
	
	private String winner = "Broker";
	private int currentPrice = 1000;
	private int increment = 50;
	private BrokerListener listener;

	
	public AuctionBroker(BrokerListener listener) {
		this.listener = listener;
	}

	public void sendClose() {
		auctionList.sendClose();
	}
	
	@Override
	public void onJoin(String bidderId, Auction auction) {
		auctionList.add(bidderId, auction);
		auction.sendPrice(currentPrice, increment, winner);
		listener.setStatus("Joined", winner, currentPrice);
		listener.bidderAdded(new BidderSnapshot(bidderId, "Joined"));
	}

	@Override
	public void onBid(String bidderId, int price) {
		Logger.getLogger("AuctionBroker").info(Thread.currentThread().getId() +  ") updateBid: " + price + ", " + bidderId);
		if (currentPrice < price) {
			this.currentPrice = price;
			this.winner = bidderId;
		}

		auctionList.sendPrice(currentPrice, increment, winner);
		listener.setStatus("Bidding", winner, price);
		listener.bidderChanged(new BidderSnapshot(bidderId, Integer.toString(price)));
	}

	public String getWinner(){
		return winner;
	}
}	
