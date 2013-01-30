package auctionhouse;

public interface BidderObserver {

	int getPrice();
	String getWinner();
	int getIncrement();

	void bid(int price, String bidderId);
	void add(Bidder bidCounter);
	void notifyClose();
	void join(Auction auction);

}
