package auctionsniper.xmpp;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

import auctionsniper.values.SniperId;

public class AuctionMessageTranslator implements MessageListener {
	private final AuctionMessageTranslatorCore translatorCore;
	private final AuctionMessageTranslatorExceptionHandler exceptionHandler;

	public AuctionMessageTranslator(SniperId sniperId,
			AuctionEventListener listener, XMPPFailureReporter failureReporter) {
		this.translatorCore = new AuctionMessageTranslatorCore(sniperId,
				listener);
		this.exceptionHandler = new AuctionMessageTranslatorExceptionHandler(
				listener, failureReporter, sniperId);
	}

	@Override
	public void processMessage(Chat chat, Message message) {
		String messageBody = message.getBody();

		try {
			translatorCore.translate(messageBody);
		} catch (Exception parseException) {
			exceptionHandler.processParseException(messageBody, parseException);
		}
	}
}