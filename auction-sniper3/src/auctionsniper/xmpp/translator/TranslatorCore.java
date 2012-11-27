package auctionsniper.xmpp.translator;

import auctionsniper.event.AuctionEvent;
import auctionsniper.event.CloseEventHandler;
import auctionsniper.event.PriceEventHandler;
import auctionsniper.values.SniperId;
import auctionsniper.xmpp.AuctionEventListener;
import auctionsniper.xmpp.EventType;

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