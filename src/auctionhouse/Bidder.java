package auctionhouse;


public class Bidder implements AuctionCommandHandler {

	Auction auction;
	private BidderObserver broker;

	public Bidder(Auction auction, BidderObserver broker) {
		this.auction = auction;
		this.broker = broker;
	}
		
	@Override
	public void onJoin(String bidderId) {
		broker.join(auction);
	}

	@Override
	public void onBid(String bidderId, int price) {
		broker.bid(price, bidderId);
	}

	@Override
	public void sendClose() {
		auction.closeAuction();
	}

	@Override
	public void sendPrice(int currentPrice, int increment, String winner) {
		auction.sendPrice(currentPrice, increment, winner);
	}

}
