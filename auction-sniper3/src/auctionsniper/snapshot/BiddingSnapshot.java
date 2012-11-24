package auctionsniper.snapshot;

import auctionsniper.SniperState;
import auctionsniper.values.ItemId;
import auctionsniper.values.Price;

public class BiddingSnapshot extends SniperSnapshot {
	@Override public SniperSnapshot closed() {
		return new LostSnapshot(itemId, lastPrice(), lastBid());
	}
	public BiddingSnapshot(ItemId itemId, Price lastPrice, Price lastBid) {
		super(itemId, lastPrice, lastBid);
	}
	@Override public SniperState getState() { return SniperState.BIDDING; }
}