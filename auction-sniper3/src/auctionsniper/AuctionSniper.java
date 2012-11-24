package auctionsniper;

import auctionsniper.snapshot.SniperSnapshot;
import auctionsniper.util.Announcer;
import auctionsniper.values.Increment;
import auctionsniper.values.Item;
import auctionsniper.values.Price;
import auctionsniper.xmpp.AuctionEventListener;

public class AuctionSniper implements AuctionEventListener {
	//TODO rule 8. No classes with more than two instance variables
	private SniperSnapshot       snapshot;
	//TODO rule 4. First class collections
	private final Announcer<SniperListener> listeners = Announcer.to(SniperListener.class);
	private final Auction  auction;
	private final Item     item;

	public AuctionSniper(Item item, Auction auction) {
		this.auction  = auction;
		this.item     = item;   
		this.snapshot = SniperSnapshot.joining(item.identifier);
	}

	@Override
	public void auctionClosed() {
		snapshot = snapshot.closed();
		notifyChange();
	}
	//TODO 長すぎるメソッド
	@Override
	public void currentPrice(Price price, Increment increment, PriceSource priceSource) {
		//TODO rule 2. Don’t use the ELSE keyword 
		switch (priceSource) {
		case FromSniper:
			snapshot = snapshot.winning(price);
			break;
		case FromOtherBidder:
			Price bid = price.add(increment);
			boolean allowsBid = item.allowsBid(bid);
			if (allowsBid) auction.bid(bid);
			snapshot = allowsBid ? snapshot.bidding(price, bid): snapshot.losing(price); 
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
	public SniperSnapshot getSnapshot() {
		return snapshot;
	}
	@Override
	public void auctionFailed() {
		snapshot = snapshot.failed();
		notifyChange();
	}
}
