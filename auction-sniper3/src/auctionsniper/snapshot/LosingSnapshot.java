package auctionsniper.snapshot;

import auctionsniper.values.ItemId;
import auctionsniper.values.Price;

public class LosingSnapshot extends SniperSnapshot {
	public LosingSnapshot(ItemId itemId, Price lastPrice, Price lastBid) {
		super(itemId, lastPrice, lastBid);
	}
	@Override public SniperSnapshot closed() {
		return new LostSnapshot(itemId, lastPrice(), lastBid());
	}
}