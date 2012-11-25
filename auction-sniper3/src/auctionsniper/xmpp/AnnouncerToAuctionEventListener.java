package auctionsniper.xmpp;

import auctionsniper.util.Announcer;

public class AnnouncerToAuctionEventListener {
	private Announcer<AuctionEventListener> announcer =
			Announcer.to(AuctionEventListener.class);

	public void addListener(AuctionEventListener listener) {
		announcer.addListener(listener);
	}

	public AuctionEventListener announce() {
		return announcer.announce();
	}
}