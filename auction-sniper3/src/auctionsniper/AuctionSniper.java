package auctionsniper;


import auctionsniper.snapshot.SniperSnapshot;
import auctionsniper.values.Increment;
import auctionsniper.values.Item;
import auctionsniper.values.Price;
import auctionsniper.xmpp.AuctionEventListener;

public class AuctionSniper implements AuctionEventListener {
	//TODO rule 8. No classes with more than two instance variables
	private SniperSnapshot                  snapshot;
	private final AnnouncerToSniperListener listeners = new AnnouncerToSniperListener();
	private final CurrentPriceHelper        currentPriceHelper;

	public AuctionSniper(Item item, Auction auction) {
		this.currentPriceHelper = new CurrentPriceHelper(auction, item); 
		this.snapshot           = SniperSnapshot.joining(item.identifier);
	}

	@Override public void auctionClosed() {
		snapshot = snapshot.closed();
		notifyChange();
	}
	@Override public void currentPrice(Price price, Increment increment, PriceSource source) {
		snapshot = currentPriceHelper.process(price, increment, source, snapshot);
		notifyChange();
	}
	@Override public void auctionFailed() {
		snapshot = snapshot.failed();
		notifyChange();
	}

	private void notifyChange() {
		this.listeners.sniperStateChanged(snapshot);
	}
	public void addSniperListener(SniperListener listener) {
		listeners.addListener(listener);
	}
	public SniperSnapshot getSnapshot() {
		return snapshot;
	}
}
