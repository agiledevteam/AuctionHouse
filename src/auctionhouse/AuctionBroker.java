package auctionhouse;


public class AuctionBroker implements AuctionCommandHandler {

	Auction auction;
	BrokerListener listener;
	private String winner = "Broker";
	private int currentPrice = 1000;
	private int increment = 50;
	private AuctionBrokerManager manager;

	public AuctionBroker(Auction auction, BrokerListener listener, AuctionBrokerManager manager) {
		this.auction = auction;
		this.listener = listener;
		this.manager = manager;
	}
		
	@Override
	public void onJoin(String bidderId) {
		auction.sendPrice(currentPrice, increment, winner);
		listener.setStatus("Joined", currentPrice, bidderId);
	}

	@Override
	public void onBid(String bidderId, int price) {
		currentPrice = price;
		winner = bidderId;
		auction.sendPrice(currentPrice, increment, bidderId);
		manager.broadcastPrice(currentPrice, increment, winner);
		listener.setStatus("Bidding", currentPrice, bidderId);
	}

}
