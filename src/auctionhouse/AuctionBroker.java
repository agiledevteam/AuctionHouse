package auctionhouse;

import org.jivesoftware.smack.XMPPException;

public class AuctionBroker implements AuctionCommandHandler {

	Auction auction;
	BrokerListener listener;
	
	public AuctionBroker(Auction auction, BrokerListener listener) {
		this.auction = auction;
		this.listener = listener;
	}
		
	@Override
	public void onJoin() {
		auction.sendPrice(1000, 50, "");
		listener.setStatus("Joined", 1000, "");
	}

	@Override
	public void onBid() {
		auction.sendPrice(1000, 50, "");
		listener.setStatus("Bidding", 1000, "");
	}

}
