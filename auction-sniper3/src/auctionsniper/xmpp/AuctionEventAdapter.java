package auctionsniper.xmpp;

import auctionsniper.AuctionEventListener;
import auctionsniper.values.Increment;
import auctionsniper.values.Amount;

public class AuctionEventAdapter implements AuctionEventListener {
	public void auctionFailed() {}
	public void auctionClosed() {}
	public void currentPrice(Amount price, Increment increment,
			PriceSource priceSource) {}
}