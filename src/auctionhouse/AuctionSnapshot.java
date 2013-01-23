package auctionhouse;

public class AuctionSnapshot {
	public final String bidder;
	public final int lastPrice;
	public final String state;
	
	public AuctionSnapshot(String bidder, int lastPrice, String state){
		this.bidder = bidder;
		this.lastPrice = lastPrice;
		this.state = state;
	}
}
