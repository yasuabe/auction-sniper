package auctionsniper.event;

import auctionsniper.MissingValueException;
import auctionsniper.values.SniperId;
import auctionsniper.xmpp.AuctionEventListener;

public class PriceEventHandler extends EventHandler {
	public PriceEventHandler(AuctionEventListener listener, SniperId sniperId) {
		super(listener, sniperId);
	}
	@Override public void handle(AuctionEvent event) throws MissingValueException {
		listener.currentPrice(
				event.currentPrice(), event.increment(), event.isFrom(sniperId));
	}
}