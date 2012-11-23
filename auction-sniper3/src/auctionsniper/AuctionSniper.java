package auctionsniper;

import auctionsniper.xmpp.AuctionEventListener;

public class AuctionSniper implements AuctionEventListener {

	private boolean              isWinning  = false;
	private final SniperListener sniperListener;
	private final Auction        auction;
	private final String         itemId;

	public AuctionSniper(Auction auction, String itemId, SniperListener sniperListener) {
		this.auction        = auction;
		this.sniperListener = sniperListener;
		this.itemId         = itemId;
	}

	@Override
	public void auctionClosed() {
		if (isWinning) {
			sniperListener.sniperWon();
		} else {
			sniperListener.sniperLost();
		}
	}

	@Override
	public void currentPrice(int price, int increment, PriceSource priceSource) {
		isWinning = priceSource == PriceSource.FromSniper;
		if (isWinning) {
			sniperListener.sniperWinning();
		} else {
			final int bid = price + increment;
			auction.bid(bid);
			sniperListener.sniperBidding(new SniperSnapshot(itemId, price, bid));
		}
	}
}
