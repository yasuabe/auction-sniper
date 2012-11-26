package auctionsniper.xmpp;

import auctionsniper.values.Increment;
import auctionsniper.values.Price;

public class AuctionEventAdapter implements AuctionEventListener {
	public void auctionFailed() {}
	public void auctionClosed() {}
	public void currentPrice(Price price, Increment increment,
			PriceSource priceSource) {}
}