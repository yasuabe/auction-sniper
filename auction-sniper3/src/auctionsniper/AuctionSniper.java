package auctionsniper;

import auctionsniper.xmpp.AuctionEventListener;

public class AuctionSniper implements AuctionEventListener {

	private SniperSnapshot       snapshot;
	
	private final SniperListener sniperListener;
	private final Auction        auction;
	private final String         itemId;

	public AuctionSniper(String itemId, Auction auction, SniperListener sniperListener) {
		this.auction        = auction;
		this.sniperListener = sniperListener;
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
		this.sniperListener.sniperStateChanged(snapshot);
	}
}
