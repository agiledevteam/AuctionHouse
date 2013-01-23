package auctionhouse;

public interface AuctionCommandHandler {

	void onJoin(String bidderId);
	void onBid(String bidderId, int price);

}
