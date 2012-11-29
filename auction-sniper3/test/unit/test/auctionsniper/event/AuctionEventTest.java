package test.auctionsniper.event;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import auctionsniper.event.AuctionEvent;
import auctionsniper.event.EventType;
import auctionsniper.event.MissingValueException;

public class AuctionEventTest {

	@Test public void valueOf() throws Exception {
		assertEquals(EventType.CLOSE, AuctionEvent.from("Event:CLOSE").type());
		assertEquals(EventType.PRICE, AuctionEvent.from("Event:PRICE").type());
	}
	@Test(expected=IllegalArgumentException.class) 
	public void valueOf_IllegalArgument() throws Exception {
		assertEquals(EventType.PRICE, AuctionEvent.from("Event:a").type());
	}
	@Test(expected=MissingValueException.class) 
	public void valueOf_MissingValue() throws Exception {
		assertEquals(EventType.PRICE, AuctionEvent.from("CurrentPrice:100").type());
	}
}
