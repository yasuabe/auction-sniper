package auctionsniper;

import java.util.EventListener;

public interface SniperListener extends EventListener {

	void sniperLost();
	void sniperBidding(SniperSnapshot sniperState);
	void sniperWon();
	
	void sniperStateChanged(SniperSnapshot state);
}
