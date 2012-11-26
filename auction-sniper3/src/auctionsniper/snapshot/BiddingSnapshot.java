package auctionsniper.snapshot;

import auctionsniper.values.ItemId;
import auctionsniper.values.Amount;

public class BiddingSnapshot extends SniperSnapshot {
	@Override public SniperSnapshot closed() {
		return new LostSnapshot(itemId, lastPrice(), lastBid());
	}
	public BiddingSnapshot(ItemId itemId, Amount lastPrice, Amount lastBid) {
		super(itemId, lastPrice, lastBid);
	}
}