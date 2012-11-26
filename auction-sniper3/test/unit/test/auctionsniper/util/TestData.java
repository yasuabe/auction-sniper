package test.auctionsniper.util;

import auctionsniper.snapshot.SniperSnapshot;
import auctionsniper.values.Item;
import auctionsniper.values.ItemId;
import auctionsniper.values.Amount;

public class TestData {
	public static SniperSnapshot joinSnapshot(String itemId) {
		return SniperSnapshot.joining(ItemId.fromString(itemId));
	}
	public static SniperSnapshot biddingSnapshot(String itemId, int newLastPrice, int newLastBid) {
		return joinSnapshot(itemId).bidding(Amount.fromInt(newLastPrice), Amount.fromInt(newLastBid));
	}
	public static SniperSnapshot winningSnapshot(String itemId, int newLastPrice, int newLastBid) {
		return biddingSnapshot(itemId, newLastPrice, newLastBid).winning(Amount.fromInt(newLastPrice));
	}
	public static SniperSnapshot losingSnapshot(String itemId, int newLastPrice, int newLastBid) {
		return biddingSnapshot(itemId, newLastPrice, newLastBid).losing(Amount.fromInt(newLastPrice));
	}
	public static SniperSnapshot closedSnapshot(String itemId, int newLastPrice, int newLastBid) {
		return biddingSnapshot(itemId, newLastPrice, newLastBid).closed();
	}
	public static SniperSnapshot lostSnapshot(String itemId, int newLastPrice, int newLastBid) {
		return biddingSnapshot(itemId, newLastPrice, newLastBid).lost(Amount.fromInt(newLastPrice));
	}
	public static SniperSnapshot failedSnapshot(String itemId, int newLastPrice, int newLastBid) {
		return biddingSnapshot(itemId, newLastPrice, newLastBid).failed();
	}
	public static Item newItem(String itemId, int stopPrice) {
		return new Item(ItemId.fromString(itemId), Amount.fromInt(stopPrice));
	}
}
