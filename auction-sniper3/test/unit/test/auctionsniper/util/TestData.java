package test.auctionsniper.util;

import auctionsniper.SniperSnapshot;
import auctionsniper.SniperState;
import auctionsniper.values.Item;
import auctionsniper.values.ItemId;
import auctionsniper.values.Price;

public class TestData {

	public static SniperSnapshot newSnapshot(String itemId, int price, int bid,
			SniperState state) {

		return new SniperSnapshot(ItemId.fromString(itemId),
				Price.fromInt(price), Price.fromInt(bid), state);
	}

	public static Item newItem(String itemId, int stopPrice) {
		return new Item(ItemId.fromString(itemId), Price.fromInt(stopPrice));
	}
}
