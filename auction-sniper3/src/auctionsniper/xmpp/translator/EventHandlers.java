package auctionsniper.xmpp.translator;

import java.util.EnumMap;
import java.util.Map;

import auctionsniper.event.AuctionEvent;
import auctionsniper.event.EventHandler;
import auctionsniper.event.MissingValueException;
import auctionsniper.xmpp.EventType;

public class EventHandlers {
	private final Map<EventType , EventHandler> map = new EnumMap<>(EventType.class);

	void handle(AuctionEvent event) throws MissingValueException {
		EventHandler handler = map.get(event.type());
		if (null != handler) handler.handle(event);
	}
	public void put(EventType eventType, EventHandler handler) {
		map.put(eventType, handler);
	}
}