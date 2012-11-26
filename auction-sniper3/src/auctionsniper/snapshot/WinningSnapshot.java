package auctionsniper.snapshot;

import auctionsniper.values.ItemId;
import auctionsniper.values.Amount;

public class WinningSnapshot extends SniperSnapshot {
	public WinningSnapshot(ItemId itemId, Amount lastPrice, Amount lastBid) {
		super(itemId, lastPrice, lastBid);
	}
	@Override public SniperSnapshot closed() {
		return new WonSnapshot(itemId, lastPrice(), lastBid());
	}
}