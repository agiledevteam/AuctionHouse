package endtoend;

import static org.junit.Assert.*;

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
	
}
