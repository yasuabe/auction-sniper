package auctionsniper.event;

import java.util.EnumMap;
import java.util.Map;

public class EventHandlers {
	private final Map<EventType , EventHandler> map = new EnumMap<>(EventType.class);

	public void handle(AuctionEvent event) throws MissingValueException {
		EventHandler handler = map.get(event.type());
		if (null != handler) handler.handle(event);
	}
	public void put(EventType eventType, EventHandler handler) {
		map.put(eventType, handler);
	}
}