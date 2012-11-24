package test.auctionsniper.util;

import auctionsniper.Item;
import auctionsniper.SniperSnapshot;
import auctionsniper.SniperState;
import auctionsniper.values.Price;

public class TestData {

	public static SniperSnapshot newSnapshot(String itemId, int price, int bid, SniperState state) {
		return new SniperSnapshot(itemId, Price.fromInt(price), Price.fromInt(bid), state);	
	}
	public static Item newItem(String itemId, int stopPrice) {
		return new Item(itemId, Price.fromInt(stopPrice));
	}
}
