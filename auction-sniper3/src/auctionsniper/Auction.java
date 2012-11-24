package auctionsniper;

import auctionsniper.values.Price;
import auctionsniper.xmpp.AuctionEventListener;

public interface Auction {

	void bid(Price bid);
	void join();
	void addAuctionEventListener(AuctionEventListener auctionSniper);
}
