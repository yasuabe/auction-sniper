package auctionsniper;

import auctionsniper.values.Amount;
import auctionsniper.xmpp.AuctionEventListener;

public interface Auction {

	void bid(Amount bid);
	void join();
	void addAuctionEventListener(AuctionEventListener auctionSniper);
}
