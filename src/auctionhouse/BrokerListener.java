package auctionhouse;

public interface BrokerListener {

	void setStatus(String statusText, String winner, int price);

	void bidderAdded(Bidder bidder);
}
