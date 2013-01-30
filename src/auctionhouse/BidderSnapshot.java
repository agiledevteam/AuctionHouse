package auctionhouse;

public class BidderSnapshot {

	public final String bidderId;
	public final String status;

	public BidderSnapshot(String id, String status) {
		this.bidderId = id;
		this.status = status;
	}

}
