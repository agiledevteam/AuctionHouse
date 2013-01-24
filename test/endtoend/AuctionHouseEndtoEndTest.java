package endtoend;

import org.junit.After;
import org.junit.Test;

public class AuctionHouseEndtoEndTest {

	ApplicationRunner app = new ApplicationRunner();
	FakeBidder bidder1 = new FakeBidder("bidder-1");
	FakeBidder bidder2 = new FakeBidder("bidder-2");
	
	@Test
	public void clientJoinedButAuctionClosed() throws Exception {
		app.startAuction();
		app.showsStarted();
		bidder1.join();
		bidder1.receivedPriceMessage(1000, 50, "Broker");
		app.showBidderJoined(bidder1.getId());
		app.closeAuction();
		bidder1.receivedClosedMessage();
	}
	
	@Test
	public void clientJoinedAndBidAndClosed() throws Exception {
		app.startAuction();
		app.showsStarted();
		bidder1.join();
		bidder1.receivedPriceMessage(1000, 50, "Broker");
		app.showBidderJoined(bidder1.getId());	// actual "JOIN" command received and send current price
		bidder1.bid();
		bidder1.receivedPriceMessage(1050, 50, "sniper");
		app.showBidderBidding();
		app.closeAuction();
		bidder1.receivedClosedMessage();
	}
	
	@Test
	public void multiClientJoinAndClosed() throws Exception{
		app.startAuction();
		app.showsStarted();
		bidder1.join();
		bidder1.receivedPriceMessage(1000, 50, "Broker");
		app.showBidderJoined(bidder1.getId());
		bidder2.join();
		bidder2.receivedPriceMessage(1000, 50, "Broker");
		app.showBidderJoined(bidder2.getId());
		app.closeAuction();
		bidder1.receivedClosedMessage();
		bidder2.receivedClosedMessage();
	}
	
	@After
	public void stopAuction() {
		bidder1.stop();
	}
	
	@After
	public void stopApplication() {
		app.stop();
	}

}
