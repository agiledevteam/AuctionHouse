package auctionhouse;

public interface AuctionBrokerManager {

	void broadcastPrice(int currentPrice, int increment, String winner);

}
