package auctionsniper.snapshot;

import auctionsniper.SniperState;
import auctionsniper.values.ItemId;
import auctionsniper.values.Price;

public class WonSnapshot extends SniperSnapshot {
	public WonSnapshot(ItemId itemId, Price lastPrice, Price lastBid) {
		super(itemId, lastPrice, lastBid);
	}
	@Override public SniperState getState() { return SniperState.WON; }
}