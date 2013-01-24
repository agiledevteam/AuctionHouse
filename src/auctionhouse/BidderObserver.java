package auctionhouse;

public interface BidderObserver {

	int getPrice();

	String getWinner();

	int getIncrement();

	void updateBid(int price, String bidderId);

	void add(AuctionBidderCount bidCounter);

	void notifyClose();

}
