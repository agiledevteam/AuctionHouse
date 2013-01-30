package auctionhouse;

public interface AuctionCommandHandler {

	void onJoin(String bidderId, Auction auction);
	void onBid(String bidderId, int price);
}
