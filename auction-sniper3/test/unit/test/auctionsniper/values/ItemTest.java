package test.auctionsniper.values;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static test.auctionsniper.util.TestData.newItem;

import org.junit.Test;

import auctionsniper.values.Amount;

public class ItemTest {
	private static final String itemId = "itemId";

	@Test public void allowsBid() {
		assertToBeAllowed(    MIN_VALUE, MIN_VALUE);
		assertNotToBeAllowed( MIN_VALUE, MIN_VALUE + 1);

		assertToBeAllowed(    0,         -1);
		assertToBeAllowed(    0,         0);
		assertNotToBeAllowed( 0,         1);

		assertToBeAllowed(    MAX_VALUE, MAX_VALUE - 1);
		assertToBeAllowed(    MAX_VALUE, MAX_VALUE);
	}
	
	private static void assertToBeAllowed(int stopPrice, int price) {
		assertTrue(isAllowed(stopPrice, price));
	}
	private static void assertNotToBeAllowed(int stopPrice, int price) {
		assertFalse(isAllowed(stopPrice, price));
	}
	private static boolean isAllowed(int stopPrice, int price) {
		return newItem(itemId,  stopPrice).allowsBid(Amount.fromInt(price));
	}
}
