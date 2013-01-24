package auctionhouse;

public interface AuctionCommandHandler {

	void onJoin(String bidderId);
	void onBid(String bidderId, int price);
	
	void sendClose();
	void sendPrice(int currentPrice, int increment, String winner);

}
