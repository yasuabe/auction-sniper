package auctionsniper.sniper;


import auctionsniper.Auction;
import auctionsniper.SniperListener;
import auctionsniper.snapshot.SniperSnapshot;
import auctionsniper.values.Increment;
import auctionsniper.values.Item;
import auctionsniper.values.Price;
import auctionsniper.xmpp.AuctionEventListener;

public class AuctionSniper implements AuctionEventListener {
	private       SniperSnapshot    snapshot;
	private final AuctionSniperCore core;

	public AuctionSniper(Item item, Auction auction) {
		this.snapshot = SniperSnapshot.joining(item.identifier);
		this.core     = new AuctionSniperCore(auction, item);
	}

	@Override public void auctionClosed() {
		snapshot = snapshot.closed();
		core.notifyChange(snapshot);
	}
	@Override public void currentPrice(Price price, Increment increment, PriceSource source) {
		snapshot = core.process(price, increment, source, snapshot);
		core.notifyChange(snapshot);
	}
	@Override public void auctionFailed() {
		snapshot = snapshot.failed();
		core.notifyChange(snapshot);
	}
	public void addSniperListener(SniperListener listener) {
		core.addListener(listener);
	}
	//TODO rule 9. No getters/setters/properties
	public SniperSnapshot getSnapshot() {
		return snapshot;
	}
}
