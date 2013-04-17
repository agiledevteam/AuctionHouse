package endtoend;

import org.junit.After;
import org.junit.Test;

public class AuctionHouseEndtoEndTest {
	private static final String BIDDER_1 = "bidder-1";

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
		bidder1.bid(1050);
		bidder1.receivedPriceMessage(1050, 50, bidder1.getId());
		app.showBidderBidding(bidder1.getId(), 1050);
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
	
	@Test
	public void multiClientJoinAndBidAndClosed() throws Exception {
		app.startAuction();
		app.showsStarted();
		bidder1.join();
		bidder1.receivedPriceMessage(1000, 50, "Broker");
		app.showBidderJoined(bidder1.getId());
		bidder2.join();
		bidder2.receivedPriceMessage(1000, 50, "Broker");
		app.showBidderJoined(bidder2.getId());
		
		bidder1.bid(1050);
		bidder1.receivedPriceMessage(1050, 50, bidder1.getId());
		bidder2.receivedPriceMessage(1050, 50, bidder1.getId());
		app.showBidderBidding(bidder1.getId(), 1050);
		
		bidder2.bid(1100);
		bidder2.receivedPriceMessage(1100, 50, bidder2.getId());
		bidder1.receivedPriceMessage(1100, 50, bidder2.getId());
		app.showBidderBidding(bidder2.getId(), 1100);
		
		bidder1.bid(1150);
		bidder1.receivedPriceMessage(1150, 50, bidder1.getId());
		bidder2.receivedPriceMessage(1150, 50, bidder1.getId());
		app.showBidderBidding(bidder1.getId(), 1150);
		
		app.closeAuction();
		bidder1.receivedClosedMessage();
		bidder2.receivedClosedMessage();
	}

	@Test
	public void twoBidderRacesWithSamePrice() throws Exception {
		app.startAuction();
		app.showsStarted();
		bidder1.join();
		bidder1.receivedPriceMessage(1000, 50, "Broker");
		app.showBidderJoined(bidder1.getId());
		bidder2.join();
		bidder2.receivedPriceMessage(1000, 50, "Broker");
		app.showBidderJoined(bidder2.getId());
		
		bidder1.bid(1050);
		Thread.sleep(10);
		bidder2.bid(1050);
		app.showBidderBidding(bidder1.getId(), 1050);
		app.showBidderBidding(bidder2.getId(), 1050);

		bidder1.receivedPriceMessage(1100, 50, bidder1.getId());
		bidder2.receivedPriceMessage(1100, 50, bidder1.getId());
		
		app.showsWinnerIs(bidder1.getId());

		app.closeAuction();
		bidder1.receivedClosedMessage();
		bidder2.receivedClosedMessage();
	}
	
	@Test
	public void duplicatedJoinIgnoreFirstJoin() throws Exception {
		app.startAuction();
		app.showsStarted();

		FakeBidder dupBidder1 = new FakeBidder("bidder-1");
		FakeBidder dupBidder2 = new FakeBidder("bidder-1");

		dupBidder1.join();
		dupBidder1.receivedPriceMessage(1000, 50, "Broker");
		dupBidder2.join();
		dupBidder2.receivedPriceMessage(1000, 50, "Broker");

		app.closeAuction();
//		dupBidder1.receivedClosedMessage();
		dupBidder2.receivedClosedMessage();
	}
	
	@Test
	public void addFakeBidderAndItJoins() throws Exception {
		app.startTestMode();
		app.showsStarted();
		app.addFakeBidder();
		app.showBidderJoined("bidder-1");
	}

	@Test
	public void testModeShowsFakeBidderController() throws Exception {
		app.startTestMode();
		app.showsStarted();
	}

	
	@After
	public void stopAuction() {
		bidder1.stop();
		bidder2.stop();
	}
	
	
	@After
	public void stopApplication() {
		app.stop();
	}

}
