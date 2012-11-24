package auctionsniper.xmpp;

import java.util.EventListener;

import auctionsniper.values.Price;

public interface AuctionEventListener extends EventListener {

	enum PriceSource {
		FromSniper,
		FromOtherBidder
	}
	void auctionClosed();
	void currentPrice(Price price, int increment, PriceSource priceSource);
	void auctionFailed();
}
