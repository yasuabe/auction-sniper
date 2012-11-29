package auctionsniper.event;

import auctionsniper.AuctionEventListener;
import auctionsniper.values.SniperId;

public abstract class EventHandler {
	protected final AuctionEventListener listener;
	protected final SniperId sniperId;

	public EventHandler(AuctionEventListener listener, SniperId sniperId) {
		this.listener = listener;
		this.sniperId = sniperId;
	}

	public abstract void handle(AuctionEvent event) throws MissingValueException;
}