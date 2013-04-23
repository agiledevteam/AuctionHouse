package auctionhouse;

public interface BrokerListener {
	void statusChanged(String statusText, String winner, int price);
	void bidderChanged(BidderSnapshot bidderSnapshot);
}
