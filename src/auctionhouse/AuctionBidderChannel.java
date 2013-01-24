package auctionhouse;


public class AuctionBidderChannel implements AuctionCommandHandler {

	Auction auction;
	BrokerListener listener;
	private BidderObserver broker;

	public AuctionBidderChannel(Auction auction, BrokerListener listener, BidderObserver broker) {
		this.auction = auction;
		this.listener = listener;
		this.broker = broker;
	}
		
	@Override
	public void onJoin(String bidderId) {
		auction.sendPrice(broker.getPrice(), broker.getIncrement(), broker.getWinner());
		listener.setStatus("Joined", broker.getPrice(), bidderId);
	}

	@Override
	public void onBid(String bidderId, int price) {
		broker.updateBid(price, bidderId);
		listener.setStatus("Bidding", broker.getPrice(), bidderId);
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
