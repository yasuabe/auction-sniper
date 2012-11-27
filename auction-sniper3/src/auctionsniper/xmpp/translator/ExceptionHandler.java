package auctionsniper.xmpp.translator;

import auctionsniper.values.SniperId;
import auctionsniper.xmpp.AuctionEventListener;
import auctionsniper.xmpp.XMPPFailureReporter;

class ExceptionHandler {
	private final AuctionEventListener       listener;
	private final TranslationFailureReporter reporter;

	ExceptionHandler(AuctionEventListener listener,
			XMPPFailureReporter failureReporter, SniperId sniperId) {
		this.listener = listener;
		this.reporter = new TranslationFailureReporter(sniperId,
				failureReporter);
	}

	void processParseException(String messageBody, Exception parseException) {
		reporter.reportTranslationError(messageBody, parseException);
		listener.auctionFailed();
	}
}