package auctionsniper.xmpp;

import auctionsniper.values.SniperId;

public interface XMPPFailureReporter { 
  void cannotTranslateMessage(SniperId sniperId, String failedMessage, Exception exception);
}