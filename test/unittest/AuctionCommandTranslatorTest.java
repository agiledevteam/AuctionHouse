package unittest;

import static org.hamcrest.Matchers.notNullValue;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;
import org.junit.runner.RunWith;

import auctionhouse.Auction;
import auctionhouse.AuctionCommandHandler;
import auctionhouse.xmpp.AuctionCommandTranslator;

@RunWith(JMock.class)
public class AuctionCommandTranslatorTest {
	private final Mockery context = new Mockery() {
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};
	AuctionCommandHandler handler = context.mock(AuctionCommandHandler.class);
	private final AuctionCommandTranslator translator = new AuctionCommandTranslator(
			handler);
	private final Chat chat = context.mock(Chat.class);

	@Test
	public void test() {
		context.checking(new Expectations() {
			{
				oneOf(chat).getParticipant();
				will(returnValue("bidder-1@localhost"));
				oneOf(handler).onJoin(with("bidder-1"),
						with(notNullValue(Auction.class)));
			}
		});
		Message message = new Message();
		message.setBody("SOLVersion: 1.1; Command: JOIN;");
		translator.processMessage(chat, message);
	}

}
