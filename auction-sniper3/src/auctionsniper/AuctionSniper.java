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

	CurrentPriceProcessor fromSniper = new CurrentPriceProcessor() {
		@Override public void process(Price price, Increment increment) {
			snapshot = snapshot.winning(price);
		}
	};
	CurrentPriceProcessor fromOtherBidder = new CurrentPriceProcessor() {
		@Override public void process(Price price, Increment increment) {
			Price   bid       = price.add(increment);
			boolean allowsBid = item.allowsBid(bid);
			if (allowsBid) auction.bid(bid);
			snapshot = allowsBid ? snapshot.bidding(price, bid): snapshot.losing(price); 
		}
	};

	CurrentPriceProcessors processors = new CurrentPriceProcessors();
	{
		processors.put(PriceSource.FromSniper, fromSniper);
		processors.put(PriceSource.FromOtherBidder, fromOtherBidder);
	}

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
	@Override
	public void currentPrice(Price price, Increment increment, PriceSource source) {
		processors.get(source).process(price, increment);
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
