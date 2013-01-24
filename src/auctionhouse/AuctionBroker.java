package auctionhouse;

import java.util.ArrayList;

public class AuctionBroker implements BidderObserver {
	
	protected ArrayList<AuctionCommandHandler> brokerList = new ArrayList<AuctionCommandHandler>();
	
	private String winner = "Broker";
	private int currentPrice = 1000;
	private int increment = 50;
	
	@Override
	public int getPrice() {
		return currentPrice;
	}
	
	@Override
	public String getWinner() {
		return winner;
	}
	
	@Override
	public int getIncrement() {
		return increment;
	}
	
	@Override
	public void updateBid(int price, String bidderId) {
		this.currentPrice = price;
		this.winner = bidderId;
		
		notifyPrice(currentPrice, increment, bidderId);
	}
	
	@Override
	public void add(AuctionBidderCount bidCounter) {
		this.brokerList.add(bidCounter);
	}
	
	@Override
	public void notifyClose() {
		for (AuctionCommandHandler broker : brokerList) {
			broker.sendClose();
		}
	}
	
	private void notifyPrice(int price, int increment, String bidderId) {
		for (AuctionCommandHandler broker : brokerList) {
			broker.sendPrice(price, increment, bidderId);
		}
	}
}	
