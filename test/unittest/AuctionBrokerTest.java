package unittest;

import static org.junit.Assert.assertEquals;

import org.hamcrest.Description;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.api.Expectation;
import org.jmock.api.Invocation;
import org.junit.Test;

import auctionhouse.AuctionBroker;
import auctionhouse.BrokerListener;

public class AuctionBrokerTest {

	@Test
	public void updateBidIfItIsHigherThanPrevious() {
		Mockery context = new Mockery();
		final BrokerListener listener = context.mock(BrokerListener.class);
		context.checking(new Expectations() {
			{
				ignoring(listener);
			}
		});
		
		AuctionBroker broker = new AuctionBroker(listener);
		int winningPrice = 5000;
		
		broker.onBid("bidder-1", winningPrice);
		broker.onBid("bidder-2", winningPrice);
		
		assertEquals("bidder-1", broker.getWinner());
		
		context.assertIsSatisfied();
	}

}
