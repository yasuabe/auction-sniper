package auctionsniper.xmpp.translator;

import auctionsniper.AuctionEventListener;
import auctionsniper.values.SniperId;
import auctionsniper.xmpp.XMPPFailureReporter;

class ExceptionHandler {
	private final AuctionEventListener listener;
	private final FailureReporter      reporter;

	ExceptionHandler(AuctionEventListener listener,
			XMPPFailureReporter failureReporter, SniperId sniperId) {
		this.listener = listener;
		this.reporter = new FailureReporter(sniperId, failureReporter);
	}

	void processParseException(String messageBody, Exception parseException) {
		reporter.report(messageBody, parseException);
		listener.auctionFailed();
	}
}