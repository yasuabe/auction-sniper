package auctionsniper.snapshot;

import auctionsniper.values.ItemId;
import auctionsniper.values.Price;

public class WinningSnapshot extends SniperSnapshot {
	public WinningSnapshot(ItemId itemId, Price lastPrice, Price lastBid) {
		super(itemId, lastPrice, lastBid);
	}
	@Override public SniperSnapshot closed() {
		return new WonSnapshot(itemId, lastPrice(), lastBid());
	}
}