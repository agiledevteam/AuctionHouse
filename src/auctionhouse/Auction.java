package auctionhouse;

public interface Auction {

	void currentPrice(int currentPrice, int increment, String bidder);
	void auctionClosed();
	
}
