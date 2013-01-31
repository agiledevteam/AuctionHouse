package unittest;

import static org.junit.Assert.assertEquals;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

import auctionhouse.Auction;
import auctionhouse.AuctionBroker;
import auctionhouse.BrokerListener;

public class AuctionBrokerTest {
	private final Mockery context = new Mockery();
	private final BrokerListener listener = context.mock(BrokerListener.class);
	private final AuctionBroker broker = new AuctionBroker(listener);
	
	@Test
	public void updateBidIfItIsHigherThanPrevious() {
		context.checking(new Expectations() {
			{
				ignoring(listener);
			}
		});

		int winningPrice = 5000;

		broker.onBid("bidder-1", winningPrice);
		broker.onBid("bidder-2", winningPrice);

		assertEquals("bidder-1", broker.getWinner());

		context.assertIsSatisfied();
	}


	@Test
	public void firstJoinIgnoredWhenDuplicatedJoin() {
		final Auction auction1 = context.mock(Auction.class, "bidder-1");
		final Auction auction2 = context.mock(Auction.class, "bidder-2");

		context.checking(new Expectations() {
			{
				ignoring(listener);
				allowing(auction1).sendPrice(with(any(int.class)),
						with(any(int.class)), with(any(String.class)));
				never(auction1).closeAuction();
				one(auction2).sendPrice(1000, 50, "Broker");
				one(auction2).closeAuction();
			}
		});

		broker.onJoin("bidder-1", auction1);
		broker.onJoin("bidder-1", auction2);

		broker.sendClose();

		context.assertIsSatisfied();
	}

}
