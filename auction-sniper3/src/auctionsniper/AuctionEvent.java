package auctionsniper;

import java.util.HashMap;
import java.util.Map;

import auctionsniper.values.Increment;
import auctionsniper.values.Price;
import auctionsniper.xmpp.AuctionEventListener.PriceSource;
import static auctionsniper.xmpp.AuctionEventListener.PriceSource.*;

public class AuctionEvent {
	private final Map<String, String> fields = new HashMap<String, String>();

	public String type() throws MissingValueException {
		return get("Event");
	}
	public Price currentPrice() throws MissingValueException {
		return Price.fromInt(getInt("CurrentPrice"));
	}
	public Increment increment() throws MissingValueException {
		return Increment.fromInt(getInt("Increment"));
	}
	private int getInt(String fieldName) throws MissingValueException {
		return Integer.parseInt(get(fieldName));
	}
	public String get(String fieldName) throws MissingValueException {
		String value = fields.get(fieldName);
		if (null == value) {
			throw new MissingValueException(fieldName);
		}
		return value;
	}
	public static AuctionEvent from(String messageBody) {
		AuctionEvent event = new AuctionEvent();
		for (String field : fieldsIn(messageBody)) {
			event.addField(field);
		}
		return event;
	}
	private void addField(String field) {
		String[] pair = field.split(":");
		//TODO rule 5. One dot per line
		fields.put(pair[0].trim(), pair[1].trim());
	}
	static String[] fieldsIn(String messageBody) {
		return messageBody.split(";");
	}
	public PriceSource isFrom(String sniperId) throws MissingValueException {
		return sniperId.equals(bidder()) ? FromSniper: FromOtherBidder;
	}
	private String bidder() throws MissingValueException {
		return get("Bidder");
	}
}