package auctionsniper.event;

import auctionsniper.AuctionEventListener;
import auctionsniper.values.SniperId;

public class CloseEventHandler extends EventHandler {
	public CloseEventHandler(AuctionEventListener listener, SniperId sniperId) {
		super(listener, sniperId);
	}

	public @Override void handle(AuctionEvent event) {
		listener.auctionClosed();
	}
}