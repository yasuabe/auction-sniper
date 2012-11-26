package auctionsniper;

import auctionsniper.sniper.AuctionSniper;
import auctionsniper.util.Announcer;

class AnnouncerToPortfolioListener {
	private final Announcer<PortfolioListener> announcer = Announcer
			.to(PortfolioListener.class);

	public void addListener(PortfolioListener listener) {
		announcer.addListener(listener);
	}
	public void announceSniperAdded(AuctionSniper sniper) {
		announcer.announce().sniperAdded(sniper);
	}
}