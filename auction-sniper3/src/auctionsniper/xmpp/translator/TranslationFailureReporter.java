package auctionsniper.xmpp.translator;

import auctionsniper.values.SniperId;
import auctionsniper.xmpp.XMPPFailureReporter;

class TranslationFailureReporter {
	private final XMPPFailureReporter  failureReporter;
	private final SniperId             sniperId;

	TranslationFailureReporter(//
			SniperId sniperId, XMPPFailureReporter failureReporter) {
		this.sniperId = sniperId;
		this.failureReporter = failureReporter;
	}

	void reportTranslationError(String messageBody, Exception parseException) {
		failureReporter.cannotTranslateMessage(sniperId, messageBody,
				parseException);
	}
}