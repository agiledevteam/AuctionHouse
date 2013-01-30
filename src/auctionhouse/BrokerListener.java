package auctionhouse;

public interface BrokerListener {

	void setStatus(String statusText, String winner, int price);
	void bidderAdded(BidderSnapshot bidder);
	void bidderChanged(BidderSnapshot bidderSnapshot);
}
