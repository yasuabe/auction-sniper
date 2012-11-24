package auctionsniper;

import auctionsniper.xmpp.AuctionEventListener;

public interface Auction {

	void bid(int bid);
	void join();
	void addAuctionEventListener(AuctionEventListener auctionSniper);
}
