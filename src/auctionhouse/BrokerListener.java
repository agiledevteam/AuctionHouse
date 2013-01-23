package auctionhouse;

public interface BrokerListener {

	void setStatus(String statusText, int lastPrice ,String bidder);
}
