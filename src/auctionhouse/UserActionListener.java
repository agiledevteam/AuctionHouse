package auctionhouse;

public interface UserActionListener {

	public void openAuction(String serverAddress, int port, String itemId, String passwd);
	void closeAuction();

}
