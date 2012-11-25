package auctionsniper.xmpp;

import auctionsniper.values.Increment;
import auctionsniper.values.Price;
import auctionsniper.xmpp.AuctionEventListener.PriceSource;

public class AuctionEventAdapter implements AuctionEventListener {
	public void auctionFailed() {}
	public void auctionClosed() {}
	public void currentPrice(Price price, Increment increment,
			PriceSource priceSource) {}
}