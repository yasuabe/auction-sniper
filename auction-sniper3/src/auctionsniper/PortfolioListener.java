package auctionsniper;

import java.util.EventListener;

import auctionsniper.sniper.AuctionSniper;

public interface PortfolioListener extends EventListener {
	void sniperAdded(AuctionSniper sniper);
}
