package auctionsniper.xmpp;

import auctionsniper.AuctionEvent;
import auctionsniper.MissingValueException;
import auctionsniper.values.SniperId;

public class PriceEventHandler extends EventHandler {
	public PriceEventHandler(AuctionEventListener listener, SniperId sniperId) {
		super(listener, sniperId);
	}
	@Override void handle(AuctionEvent event) throws MissingValueException {
		listener.currentPrice(
				event.currentPrice(), event.increment(), event.isFrom(sniperId));
	}
}