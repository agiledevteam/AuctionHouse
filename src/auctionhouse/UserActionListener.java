package auctionhouse;

public interface UserActionListener {

	public void startAuction(String serverAddress, int port, String itemId, String passwd, int startPrice, int increment);
	void stopAuction();
	public void addFakeBidder();

}
