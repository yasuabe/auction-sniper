package auctionsniper;

import auctionsniper.xmpp.AuctionEventListener;

public class AuctionSniper implements AuctionEventListener {

	private final SniperListener sniperListener;

	public AuctionSniper(SniperListener sniperListener) {
		this.sniperListener = sniperListener;
	}

	@Override
	public void auctionClosed() {
		sniperListener.sniperLost();
	}

	@Override
	public void currentPrice(int i, int j) {
		// TODO Auto-generated method stub

	}

}
