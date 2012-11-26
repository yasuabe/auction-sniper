package auctionsniper.snapshot;

import auctionsniper.values.ItemId;
import auctionsniper.values.Amount;

public class JoiningSnapshot extends SniperSnapshot {
	public JoiningSnapshot(ItemId itemId, Amount lastPrice, Amount lastBid) {
		super(itemId, lastPrice, lastBid);
	}
	@Override public SniperSnapshot closed() {
		return new LostSnapshot(itemId, lastPrice(), lastBid());
	}
}