package auctionsniper.xmpp;

import auctionsniper.event.AuctionEvent;
import auctionsniper.event.CloseEventHandler;
import auctionsniper.event.PriceEventHandler;
import auctionsniper.values.SniperId;

class AuctionMessageTranslatorCore {
	private final EventHandlers handlers = new EventHandlers();

	AuctionMessageTranslatorCore(SniperId sniperId, AuctionEventListener listener) {
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