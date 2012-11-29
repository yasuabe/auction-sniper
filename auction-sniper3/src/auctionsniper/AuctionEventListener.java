package auctionsniper;

import java.util.EventListener;

import auctionsniper.values.Increment;
import auctionsniper.values.Amount;

public interface AuctionEventListener extends EventListener {

	enum PriceSource {
		FromSniper,
		FromOtherBidder
	}
	void auctionClosed();
	void currentPrice(Amount price, Increment increment, PriceSource priceSource);
	void auctionFailed();
}
