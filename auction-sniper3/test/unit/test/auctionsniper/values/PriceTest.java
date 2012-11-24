package test.auctionsniper.values;

import static org.junit.Assert.*;

import org.junit.Test;

import auctionsniper.values.Price;

public class PriceTest {

	@Test
	public void isGreatorThanOrEqualTo() {
		assertToBeGreatorThanOrEqualTo(0, -1);
		assertToBeGreatorThanOrEqualTo(0, 0);
		assertNotToBeGreatorThanOrEqualTo(0, 1);
	}
	private static void assertToBeGreatorThanOrEqualTo(int criteria, int target) {
		assertTrue(isGreatorThanOrEqualTo(criteria, target));
	}
	private static void assertNotToBeGreatorThanOrEqualTo(int criteria, int target) {
		assertFalse(isGreatorThanOrEqualTo(criteria, target));
	}
	private static boolean  isGreatorThanOrEqualTo(int criteria, int target) {
		return Price.fromInt(criteria).isGreatorThanOrEqualTo(Price.fromInt(target));
	}
}
