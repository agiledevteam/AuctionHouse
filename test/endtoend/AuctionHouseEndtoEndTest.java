package endtoend;

import org.junit.After;
import org.junit.Test;

public class AuctionHouseEndtoEndTest {

	ApplicationRunner app = new ApplicationRunner();
	FakeBidder bidder = new FakeBidder();
	
	@Test
	public void clientJoinedButAuctionClosed() throws Exception {
		app.startAuction();
		app.showsStarted();
		bidder.join();
		bidder.receivedPriceMessage(1000, 50, "Broker");
		app.showBidderJoined();
		app.closeAuction();
		bidder.receivedClosedMessage();
	}
	
	@Test
	public void clientJoinedAndBidAndClosed() throws Exception {
		app.startAuction();
		app.showsStarted();
		bidder.join();
		bidder.receivedPriceMessage(1000, 50, "Broker");
		app.showBidderJoined();	// actual "JOIN" command received and send current price
		bidder.bid();
		bidder.receivedPriceMessage(1050, 50, "sniper");
		app.showBidderBidding();
		app.closeAuction();
		bidder.receivedClosedMessage();
	}

	public void stopAuction() {
		bidder.stop();
	}
	
	@After
	public void stopApplication() {
		app.stop();
	}

}
