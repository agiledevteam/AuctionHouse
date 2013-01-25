package auctionhouse;

import java.util.ArrayList;
import java.util.logging.Logger;

public class AuctionBroker implements BidderObserver {
	
	protected ArrayList<AuctionBidderChannel> channelList = new ArrayList<AuctionBidderChannel>();
	
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
		Logger.getLogger("AuctionBroker").info(Thread.currentThread().getId() +  ") updateBid: " + price + ", " + bidderId);
		if (currentPrice < price) {
			this.currentPrice = price;
			this.winner = bidderId;
		}
		notifyPrice(currentPrice, increment, winner);
	}

	@Override
	public void add(AuctionBidderChannel channel) {
		this.channelList.add(channel);
	}
	
	@Override
	public void notifyClose() {
		for (AuctionBidderChannel channel : channelList) {
			channel.sendClose();
		}
	}
	
	private void notifyPrice(int price, int increment, String bidderId) {
		for (AuctionBidderChannel channel : channelList) {
			channel.sendPrice(price, increment, bidderId);
		}
	}
}	
