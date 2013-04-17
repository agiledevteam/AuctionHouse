package unittest;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import junit.framework.Assert;

import org.junit.Test;

import auctionhouse.Auction;
import auctionhouse.AuctionCommandHandler;
import auctionhouse.xmpp.XMPPAuctionHouse;
import endtoend.FakeBidder;

public class XMPPAuctionHouseTest {
	private static final int PRICE = 100;
	private static final String BIDDER_ID = "bidder-1";
	final FakeBidder bidder = new FakeBidder(BIDDER_ID);
	
	CountDownLatch joinLock = new CountDownLatch(1);
	CountDownLatch bidLock = new CountDownLatch(1);

	private final AuctionCommandHandler auctionCommandHandler = new AuctionCommandHandler() {
		
		@Override
		public void onJoin(String bidderId, Auction auction) {
			joinLock.countDown();
			Assert.assertEquals(BIDDER_ID, bidderId);
		}
		
		@Override
		public void onBid(String bidderId, int price) {
			bidLock.countDown();
			Assert.assertEquals(BIDDER_ID, bidderId);
			Assert.assertEquals(PRICE, price);
		}
	};

	private final XMPPAuctionHouse auctionHouse = new XMPPAuctionHouse(
			"localhost", "auction-item-54321@localhost", "auction");

	@Test
	public void onJoinAndBid() throws Exception {

		auctionHouse.start(auctionCommandHandler);

		bidder.join();
		bidder.bid(PRICE);
		
		assertTrue(joinLock.await(2, TimeUnit.SECONDS));
		assertTrue(bidLock.await(2, TimeUnit.SECONDS));
		
		auctionHouse.stop();
	}

}
