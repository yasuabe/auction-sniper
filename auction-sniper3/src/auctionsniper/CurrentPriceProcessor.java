package auctionsniper;

import auctionsniper.values.Increment;
import auctionsniper.values.Price;

public abstract class CurrentPriceProcessor {
	public static final CurrentPriceProcessor NullProcessor = new CurrentPriceProcessor() {
		@Override public void process(Price price, Increment increment) {}
	};
	public abstract void process(Price price, Increment increment);
}