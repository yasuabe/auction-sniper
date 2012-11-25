package auctionsniper;

import auctionsniper.snapshot.SniperSnapshot;
import auctionsniper.values.Increment;
import auctionsniper.values.Price;

public abstract class CurrentPriceProcessor {
	public static final CurrentPriceProcessor NullProcessor = new CurrentPriceProcessor() {
		@Override public SniperSnapshot process(Price price, Increment increment, SniperSnapshot snapshot) {
			return snapshot;
		}
	};
	public abstract SniperSnapshot process(Price price, Increment increment, SniperSnapshot snapshot);
}