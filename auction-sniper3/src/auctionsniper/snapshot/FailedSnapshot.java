package auctionsniper.snapshot;

import auctionsniper.values.ItemId;
import auctionsniper.values.Amount;

public class FailedSnapshot extends SniperSnapshot {
	public FailedSnapshot(ItemId itemId, Amount lastPrice, Amount lastBid) {
		super(itemId, lastPrice, lastBid);
	}
}