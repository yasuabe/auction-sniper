package auctionsniper.xmpp.translator;

import auctionsniper.AuctionEventListener;
import auctionsniper.event.AuctionEvent;
import auctionsniper.event.CloseEventHandler;
import auctionsniper.event.EventType;
import auctionsniper.event.PriceEventHandler;
import auctionsniper.values.SniperId;

class TranslatorCore {
	private final EventHandlers handlers = new EventHandlers();

	TranslatorCore(SniperId sniperId, AuctionEventListener listener) {
		initializeHandlers(sniperId, listener);
	}
	private void initializeHandlers(SniperId sniperId, AuctionEventListener listener) {
		handlers.put(EventType.CLOSE, new CloseEventHandler(listener, sniperId));
		handlers.put(EventType.PRICE, new PriceEventHandler(listener, sniperId));
	}
	void translate(String body) throws Exception {
		handlers.handle(AuctionEvent.from(body));
	}
}