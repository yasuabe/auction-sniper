package auctionsniper.xmpp;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

import auctionsniper.AuctionEvent;

public class AuctionMessageTranslator implements MessageListener {
	private final AuctionEventListener listener;
	private final String               sniperId;
	private final XMPPFailureReporter  failureReporter;
	
	public AuctionMessageTranslator(String sniperId,
			AuctionEventListener listener, XMPPFailureReporter failureReporter) {
		this.sniperId        = sniperId;
		this.listener        = listener;
		this.failureReporter = failureReporter;
	}
	//TODO 長すぎるメソッド
	@Override public void processMessage(Chat chat, Message message) {
		String messageBody = message.getBody();
		try {
			translate(messageBody);
		} catch (Exception parseException) {
			failureReporter.cannotTranslateMessage(sniperId, messageBody, parseException);
			listener.auctionFailed();
		}
	}
	//TODO 長すぎるメソッド
	private void translate(String body) throws Exception {
		AuctionEvent event     = AuctionEvent.from(body);
		String       eventType = event.type();

		if ("CLOSE".equals(eventType)) {
			listener.auctionClosed();
		} else if ("PRICE".equals(eventType)) {
			listener.currentPrice(
					event.currentPrice(), event.increment(), event.isFrom(sniperId));
		}
	}
}
