package endtoend;

import static org.junit.Assert.*;

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
		app.receivedJoinMessage();
		app.closeAuction();
		bidder.receivedClosedMessage();
	}
	
	@Test
	public void clientJoinedAndBidButClosed() throws Exception {
		app.startAuction();
		app.showsStarted();
		bidder.join();
		app.receivedJoinMessage();	// actual "JOIN" command received and send current price
		bidder.receivedPriceMessage();
		bidder.bid();
		app.receivedBidMessage();
		app.closeAuction();
		bidder.receivedClosedMessage();
	}

	@After
	public void stopAuction() {
		bidder.stop();
	}
	
	@After
	public void stopApplication() {
		app.stop();
	}

}
