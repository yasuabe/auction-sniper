package test.auctionsniper;

import static auctionsniper.xmpp.AuctionEventListener.PriceSource;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;

import auctionsniper.xmpp.AuctionEventListener;
import auctionsniper.xmpp.AuctionMessageTranslator;

@RunWith(JMock.class)
public class AuctionMessageTranslatorTest {

	private static final String SNIPER_ID   = "sniper id";
	public  static final Chat   UNUSED_CHAT = null;
	
	private final Mockery context = new Mockery();
	private final AuctionEventListener listener = context.mock(AuctionEventListener.class);
	
	private final AuctionMessageTranslator translator = //
			new AuctionMessageTranslator(SNIPER_ID, this.listener);
	
	@Test public void notifiesAuctionCloseWhenCloseMessageReceived() {
		context.checking(new Expectations() {{
			oneOf(listener).auctionClosed();
		}});
		Message message = new Message();
		message.setBody("SOLVersion: 1.1; Event: CLOSE;");
		
		translator.processMessage(UNUSED_CHAT, message);
	}
	@Test public void notifiesBidDetailsWhenCurrentPriceMessageReceivedFromOtherBidder() {
		context.checking(new Expectations() {{
			exactly(1).of(listener).currentPrice(192, 7, PriceSource.FromOtherBidder);
		}});
		Message message = new Message();
		message.setBody("SOLVersion: 1.1; Event: PRICE; CurrentPrice: 192; Increment: 7; " +
				"Bidder: Someone else;");
		
		translator.processMessage(UNUSED_CHAT, message);
	}
	@Test public void notifiesBidDetailsWhenCurrentPriceMessageReceivedFromSniper() {
		context.checking(new Expectations() {{
			exactly(1).of(listener).currentPrice(192, 7, PriceSource.FromSniper);
		}});
		Message message = new Message();
		message.setBody("SOLVersion: 1.1; Event: PRICE; CurrentPrice: 192; Increment: 7; " +
				"Bidder: " + SNIPER_ID + ";");
		
		translator.processMessage(UNUSED_CHAT, message);
	}
	@Test public void notifiesAuctionFailedWhenBadMessageReceived() {
		context.checking(new Expectations() {{
			exactly(1).of(listener).auctionFailed();
		}});
		Message message = new Message();
		message.setBody("a bad message");
		translator.processMessage(UNUSED_CHAT, message);
	}
	@Test public void notifiesAuctionFailedWhenEventTypeMissing() {
		context.checking(new Expectations() {{
			exactly(1).of(listener).auctionFailed();
		}});
		Message message = new Message();
		message.setBody("SOLVersion: 1.1; CurrentPrice: 234; Increment: 5; Bidder: "
				+ SNIPER_ID + ";"	);
		translator.processMessage(UNUSED_CHAT, message);
	}
}
