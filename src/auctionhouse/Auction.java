package auctionhouse;

public interface Auction {

	void sendPrice(int currentPrice, int increment, String bidder);
	void closeAuction();
	
}
