package auctionsniper.snapshot;

import auctionsniper.values.ItemId;
import auctionsniper.values.Amount;

public class WonSnapshot extends SniperSnapshot {
	public WonSnapshot(ItemId itemId, Amount lastPrice, Amount lastBid) {
		super(itemId, lastPrice, lastBid);
	}
}