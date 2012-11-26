package auctionsniper.snapshot;

import auctionsniper.values.ItemId;
import auctionsniper.values.Amount;

public class LostSnapshot extends SniperSnapshot {
	public LostSnapshot(ItemId itemId, Amount lastPrice, Amount lastBid) {
		super(itemId, lastPrice, lastBid);
	}
}