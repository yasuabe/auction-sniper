package auctionsniper.event;

import static auctionsniper.xmpp.AuctionEventListener.PriceSource.FromOtherBidder;
import static auctionsniper.xmpp.AuctionEventListener.PriceSource.FromSniper;

import java.util.HashMap;
import java.util.Map;

import auctionsniper.MissingValueException;
import auctionsniper.values.Increment;
import auctionsniper.values.Price;
import auctionsniper.values.SniperId;
import auctionsniper.xmpp.AuctionEventListener.PriceSource;

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
		if (null == value) throw new MissingValueException(fieldName);
		return value;
	}
	public static AuctionEvent from(String messageBody) {
		AuctionEvent event = new AuctionEvent();
		for (Field field : Field.fieldsIn(messageBody)) event.addField(field);
		return event;
	}
	private void addField(Field field) {
		field.register(fields);
	}
	public PriceSource isFrom(SniperId sniperId) throws MissingValueException {
		return sniperId.equals(bidder()) ? FromSniper: FromOtherBidder;
	}
	private SniperId bidder() throws MissingValueException {
		return SniperId.fromString(get("Bidder"));
	}
}