package auctionhouse;

import java.util.ArrayList;
import java.util.logging.Logger;

public class AuctionBroker implements AuctionCommandHandler {
	
	protected ArrayList<Auction> auctionList = new ArrayList<Auction>();
	
	private String winner = "Broker";
	private int currentPrice = 1000;
	private int increment = 50;
	private BrokerListener listener;

	
	public AuctionBroker(BrokerListener listener) {
		this.listener = listener;
	}

	public void sendClose() {
		for (Auction auction : auctionList) {
			auction.closeAuction();
		}
	}
	
	@Override
	public void onJoin(String bidderId, Auction auction) {
		this.auctionList.add(auction);
		auction.sendPrice(currentPrice, increment, winner);
		listener.setStatus("Joined", winner, currentPrice);				
	}

	@Override
	public void onBid(String bidderId, int price) {
		Logger.getLogger("AuctionBroker").info(Thread.currentThread().getId() +  ") updateBid: " + price + ", " + bidderId);
		if (currentPrice < price) {
			this.currentPrice = price;
			this.winner = bidderId;
		}
		for (Auction auction : auctionList) {
			auction.sendPrice(currentPrice, increment, winner);
		}
		listener.setStatus("Bidding", winner, price);
	}


	public String getWinner(){
		return winner;
	}
}	
