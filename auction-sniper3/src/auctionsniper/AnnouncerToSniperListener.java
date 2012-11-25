package auctionsniper;

import auctionsniper.snapshot.SniperSnapshot;
import auctionsniper.util.Announcer;

public class AnnouncerToSniperListener {
	Announcer<SniperListener> listeners = Announcer.to(SniperListener.class);
	void sniperStateChanged(SniperSnapshot snapshot) {
		this.listeners.announce().sniperStateChanged(snapshot);
	}
	public void addListener(SniperListener listener) {
		listeners.addListener(listener);
	}
}