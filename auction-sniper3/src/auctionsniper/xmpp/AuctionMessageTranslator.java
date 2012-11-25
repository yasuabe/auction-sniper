package auctionsniper.xmpp;


import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

import auctionsniper.AuctionEvent;
import auctionsniper.values.SniperId;

public class AuctionMessageTranslator implements MessageListener {
	//TODO rule 8. No classes with more than two instance variables
	private final AuctionEventListener listener;
	private final SniperId             sniperId;
	private final XMPPFailureReporter  failureReporter;
	private final EventHandlers        handlers = new EventHandlers();
	
	public AuctionMessageTranslator(SniperId sniperId,
			AuctionEventListener listener, XMPPFailureReporter failureReporter) {
		this.sniperId        = sniperId;
		this.listener        = listener;
		this.failureReporter = failureReporter;
		initializeHandlers();
	}
	private void initializeHandlers() {
		handlers.put("CLOSE", new CloseEventHandler(listener, sniperId));
		handlers.put("PRICE", new PriceEventHandler(listener, sniperId));
	}
	@Override public void processMessage(Chat chat, Message message) {
		String messageBody = message.getBody();
		try { translate(messageBody); }
		catch (Exception parseException) {
			processParseException(messageBody, parseException);
		}
	}
	private void processParseException(String messageBody, Exception parseException) {
		failureReporter.cannotTranslateMessage(sniperId, messageBody, parseException);
		listener.auctionFailed();
	}
	private void translate(String body) throws Exception {
		handlers.handle(AuctionEvent.from(body));
	}
}
