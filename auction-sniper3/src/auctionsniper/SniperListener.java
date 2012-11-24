package auctionsniper;

import java.util.EventListener;

import auctionsniper.snapshot.SniperSnapshot;

public interface SniperListener extends EventListener {
	void sniperStateChanged(SniperSnapshot state);
}
