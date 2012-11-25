package auctionsniper.xmpp;

import java.util.logging.Logger;

import auctionsniper.values.SniperId;

public class LoggingXMPPFailureReporter implements XMPPFailureReporter {
	private static final String MESSAGE_FORMAT = "<%s> Could not translate message \"%s\" because \"%s\"";
	private final Logger logger;

	public LoggingXMPPFailureReporter(Logger logger) {
		this.logger = logger;
	}

	//TODO auctionId ? sniperId ?
	public void cannotTranslateMessage(SniperId auctionId, String failedMessage,
			Exception exception) {
		logger.severe(String.format(MESSAGE_FORMAT, auctionId, failedMessage,
				exception.toString()));
	}
}
