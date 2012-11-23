package auctionsniper;

import auctionsniper.xmpp.AuctionEventListener;

public class AuctionSniper implements AuctionEventListener {

	private boolean              isWinning  = false;
	private SniperSnapshot       snapshot;
	
	private final SniperListener sniperListener;
	private final Auction        auction;
	private final String         itemId;

	public AuctionSniper(Auction auction, String itemId, SniperListener sniperListener) {
		this.auction        = auction;
		this.sniperListener = sniperListener;
		this.itemId         = itemId;
		this.snapshot = SniperSnapshot.joining(itemId);
	}

	@Override
	public void auctionClosed() {
		sniperListener.sniperStateChanged(snapshot.closed());
	}

	@Override
	public void currentPrice(int price, int increment, PriceSource priceSource) {
		isWinning = priceSource == PriceSource.FromSniper;
		if (isWinning) {
			snapshot = snapshot.winning(price);
		} else {
			final int bid = price + increment;
			auction.bid(bid);
			snapshot = snapshot.bidding(price, bid);
		}
		sniperListener.sniperStateChanged(snapshot);
	}
}
