package auctionsniper;

import java.util.HashMap;
import java.util.Map;

import auctionsniper.xmpp.AuctionEventListener.PriceSource;

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