package auctionhouse;

public interface AuctionBroker {

	int getPrice();

	String getWinner();

	int getIncrement();

	void updateBid(int price, String bidderId);

}
