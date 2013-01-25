package unittest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import auctionhouse.AuctionBroker;

public class AuctionBrokerTest {

	@Test
	public void updateBidIfItIsHigherThanPrevious() {
		AuctionBroker broker = new AuctionBroker();
		int winningPrice = broker.getIncrement() + broker.getPrice();
		broker.updateBid(winningPrice, "bidder-1");
		broker.updateBid(winningPrice, "bidder-2");
		assertEquals("bidder-1", broker.getWinner());
	}

}
