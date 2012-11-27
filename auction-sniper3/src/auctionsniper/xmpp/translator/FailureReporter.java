package auctionsniper.xmpp.translator;

import auctionsniper.values.SniperId;
import auctionsniper.xmpp.XMPPFailureReporter;

class FailureReporter {
	private final XMPPFailureReporter  reporter;
	private final SniperId             sniperId;

	FailureReporter(SniperId sniperId, XMPPFailureReporter reporter) {
		this.sniperId = sniperId;
		this.reporter = reporter;
	}

	void report(String messageBody, Exception exception) {
		reporter.cannotTranslateMessage(sniperId, messageBody, exception);
	}
}