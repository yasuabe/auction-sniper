package auctionsniper.xmpp;

import java.util.HashMap;
import java.util.Map;

import auctionsniper.AuctionEvent;
import auctionsniper.MissingValueException;

public class EventHandlers {
	private final Map<String, EventHandler> map = new HashMap<String, EventHandler>();

	void handle(AuctionEvent event) throws MissingValueException {
		EventHandler handler = map.get(event.type());
		if (null != handler) handler.handle(event);
	}
	public void put(String event, EventHandler handler) {
		map.put(event, handler);
	}
}