package auctionsniper.xmpp;

import auctionsniper.AuctionEvent;
import auctionsniper.values.SniperId;

class CloseEventHandler extends EventHandler {
	public CloseEventHandler(AuctionEventListener listener, SniperId sniperId) {
		super(listener, sniperId);
	}

	@Override void handle(AuctionEvent event) {
		listener.auctionClosed();
	}
}