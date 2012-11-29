package auctionsniper.sniper;

import java.util.HashMap;
import java.util.Map;

import auctionsniper.AuctionEventListener.PriceSource;

public class CurrentPriceProcessors {
	Map<PriceSource, CurrentPriceProcessor> map = new HashMap<>();
	void put(PriceSource source, CurrentPriceProcessor processor) {
		map.put(source, processor);
	}
	CurrentPriceProcessor get(PriceSource source) {
		CurrentPriceProcessor processor = map.get(source);
		return null != processor ? processor: CurrentPriceProcessor.NullProcessor;
	}
}