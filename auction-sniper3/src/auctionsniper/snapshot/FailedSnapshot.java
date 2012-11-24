package auctionsniper.snapshot;

import auctionsniper.SniperState;
import auctionsniper.values.ItemId;
import auctionsniper.values.Price;

public class FailedSnapshot extends SniperSnapshot {
	public FailedSnapshot(ItemId itemId, Price lastPrice, Price lastBid) {
		super(itemId, lastPrice, lastBid);
	}
	@Override public SniperState getState() { return SniperState.FAILED; }
}