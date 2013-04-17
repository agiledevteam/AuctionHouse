package auctionhouse;

public interface AuctionHouse {

	void start(AuctionCommandHandler broker) throws AuctionStartError;
	void stop();

}
