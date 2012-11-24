package auctionsniper;

import auctionsniper.util.Announcer;
import auctionsniper.xmpp.AuctionEventListener;

public class AuctionSniper implements AuctionEventListener {

	private SniperSnapshot       snapshot;
	
	private final Announcer<SniperListener> listeners = Announcer.to(SniperListener.class);
	private final Auction        auction;
	private final String         itemId;

	public AuctionSniper(String itemId, Auction auction) {
		this.auction        = auction;
		this.itemId         = itemId;
		this.snapshot       = SniperSnapshot.joining(itemId);
	}

	@Override
	public void auctionClosed() {
		snapshot = snapshot.closed();
		notifyChange();
	}

	@Override
	public void currentPrice(int price, int increment, PriceSource priceSource) {
		switch (priceSource) {
		case FromSniper:
			snapshot = snapshot.winning(price);
			break;
		case FromOtherBidder:
			int bid = price + increment;
			auction.bid(bid);
			snapshot = snapshot.bidding(price, bid);
			break;
		}
		notifyChange();
	}
	private void notifyChange() {
		this.listeners.announce().sniperStateChanged(snapshot);
	}
	public void addSniperListener(SniperListener listener) {
		listeners.addListener(listener);
	}
}
