package auctionhouse;


public class AuctionBroker implements AuctionCommandHandler {

	Auction auction;
	BrokerListener listener;
	private String winner = "Broker";
	private int currentPrice = 1000;
	private int increment = 50;

	public AuctionBroker(Auction auction, BrokerListener listener) {
		this.auction = auction;
		this.listener = listener;
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
		auction.sendPrice(currentPrice, increment, winner);
		listener.setStatus("Bidding", currentPrice, bidderId);
	}

}
