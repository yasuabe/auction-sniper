package auctionsniper.sniper;

import auctionsniper.snapshot.SniperSnapshot;
import auctionsniper.values.Increment;
import auctionsniper.values.Amount;

public abstract class CurrentPriceProcessor {
	public static final CurrentPriceProcessor NullProcessor = new CurrentPriceProcessor() {
		@Override public SniperSnapshot process(Amount price, Increment increment, SniperSnapshot snapshot) {
			return snapshot;
		}
	};
	public abstract SniperSnapshot process(Amount price, Increment increment, SniperSnapshot snapshot);
}