package unittest;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

import auctionhouse.Auction;
import auctionhouse.AuctionBroker;
import auctionhouse.AuctionHouse;
import auctionhouse.AuctionStartError;
import auctionhouse.BidderSnapshot;
import auctionhouse.BrokerListener;

public class AuctionBrokerTest {
	private final Mockery context = new Mockery();
	private final BrokerListener listener = context.mock(BrokerListener.class);
	private final AuctionHouse auctionHouse = context.mock(AuctionHouse.class);
	private final AuctionBroker broker = new AuctionBroker(listener);
	
	@Test
	public void registerAsAuctionCommandHandlerOnStart() throws Exception {
		context.checking(new Expectations() {
			{
				ignoring(listener);
				oneOf(auctionHouse).start(broker);
			}
		});
		broker.startAuction(auctionHouse);
		context.assertIsSatisfied();
	}


	@Test
	public void updateBidIfItIsHigherThanPrevious() {
		final int winningPrice = 5000;
		context.checking(new Expectations() {
			{
				atLeast(1).of(listener).setStatus("Winner", "bidder-1",
						winningPrice);
				allowing(listener).bidderChanged(
						with(any(BidderSnapshot.class)));
			}
		});

		broker.onBid("bidder-1", winningPrice);
		broker.onBid("bidder-2", winningPrice);

		context.assertIsSatisfied();
	}

	@Test
	public void firstJoinIgnoredWhenSecondJoinReceived() {
		final Auction auction1 = context.mock(Auction.class, "bidder-1");
		final Auction auction2 = context.mock(Auction.class, "bidder-2");

		context.checking(new Expectations() {
			{
				ignoring(listener);
				allowing(auction1).currentPrice(with(any(int.class)),
						with(any(int.class)), with(any(String.class)));
				never(auction1).auctionClosed();
				oneOf(auction2).currentPrice(1000, 50, "Broker");
				oneOf(auction2).auctionClosed();
			}
		});

		broker.onJoin("bidder-1", auction1);
		broker.onJoin("bidder-1", auction2);

		broker.sendClose();

		context.assertIsSatisfied();
	}

}
