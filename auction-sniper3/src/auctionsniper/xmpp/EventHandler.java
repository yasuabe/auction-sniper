package auctionsniper.xmpp;

import auctionsniper.AuctionEvent;
import auctionsniper.MissingValueException;
import auctionsniper.values.SniperId;

public abstract class EventHandler {
	protected final AuctionEventListener listener;
	protected final SniperId sniperId;

	public EventHandler(AuctionEventListener listener, SniperId sniperId) {
		this.listener = listener;
		this.sniperId = sniperId;
	}

	abstract void handle(AuctionEvent event) throws MissingValueException;
}