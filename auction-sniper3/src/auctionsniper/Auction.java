package auctionsniper;

import auctionsniper.values.Amount;

public interface Auction {

	void bid(Amount bid);
	void join();
	void addAuctionEventListener(AuctionEventListener auctionSniper);
}
