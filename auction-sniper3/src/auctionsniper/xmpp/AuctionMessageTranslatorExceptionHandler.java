package auctionsniper.xmpp;

import auctionsniper.values.SniperId;

class AuctionMessageTranslatorExceptionHandler {
	private final AuctionEventListener listener;
	private final XMPPFailureReporter  failureReporter;
	private final SniperId             sniperId;

	AuctionMessageTranslatorExceptionHandler(AuctionEventListener listener, XMPPFailureReporter failureReporter, SniperId sniperId) {
		this.listener = listener;
		this.failureReporter = failureReporter;
		this.sniperId = sniperId; 
	}
	void processParseException(String messageBody, Exception parseException) {
		failureReporter.cannotTranslateMessage(sniperId, messageBody, parseException);
		listener.auctionFailed();
	}
}