package auctionsniper.xmpp.translator;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

import auctionsniper.values.SniperId;
import auctionsniper.xmpp.AuctionEventListener;
import auctionsniper.xmpp.XMPPFailureReporter;

public class AuctionMessageTranslator implements MessageListener {
	private final TranslatorCore translatorCore;
	private final ExceptionHandler exceptionHandler;

	public AuctionMessageTranslator(SniperId sniperId,
			AuctionEventListener listener, XMPPFailureReporter failureReporter) {
		this.translatorCore = new TranslatorCore(sniperId,
				listener);
		this.exceptionHandler = new ExceptionHandler(
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