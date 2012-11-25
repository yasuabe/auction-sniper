package auctionsniper.event;

import auctionsniper.values.SniperId;
import auctionsniper.xmpp.AuctionEventListener;

public class CloseEventHandler extends EventHandler {
	public CloseEventHandler(AuctionEventListener listener, SniperId sniperId) {
		super(listener, sniperId);
	}

	public @Override void handle(AuctionEvent event) {
		listener.auctionClosed();
	}
}