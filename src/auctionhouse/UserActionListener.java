package auctionhouse;

public interface UserActionListener {

	public void openAuction(String serverAddress, int port, String itemId, String passwd, int startPrice, int increment);
	void closeAuction();
	public void addFakeBidder();

}
