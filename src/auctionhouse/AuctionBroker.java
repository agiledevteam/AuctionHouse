package auctionhouse;

import java.util.ArrayList;
import java.util.logging.Logger;

public class AuctionBroker implements BidderObserver {
	
	protected ArrayList<Bidder> channelList = new ArrayList<Bidder>();
	
	private String winner = "Broker";
	private int currentPrice = 1000;
	private int increment = 50;
	private BrokerListener listener;

	
	public AuctionBroker(BrokerListener listener) {
		this.listener = listener;
	}

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
	public void join(Auction auction) {
		auction.sendPrice(currentPrice, increment, winner);
		listener.setStatus("Joined", winner, currentPrice);		
	}
	
	@Override
	public void bid(int price, String bidderId) {
		Logger.getLogger("AuctionBroker").info(Thread.currentThread().getId() +  ") updateBid: " + price + ", " + bidderId);
		if (currentPrice < price) {
			this.currentPrice = price;
			this.winner = bidderId;
		}
		notifyPrice(currentPrice, increment, winner);
		listener.setStatus("Bidding", winner, price);
	}

	@Override
	public void add(Bidder channel) {
		this.channelList.add(channel);
		listener.bidderAdded(channel);
	}
	
	@Override
	public void notifyClose() {
		for (Bidder channel : channelList) {
			channel.sendClose();
		}
	}
	
	private void notifyPrice(int price, int increment, String bidderId) {
		for (Bidder channel : channelList) {
			channel.sendPrice(price, increment, bidderId);
		}
	}
}	
