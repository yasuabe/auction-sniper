package auctionsniper.sniper;

import auctionsniper.Auction;
import auctionsniper.AuctionEventListener;
import auctionsniper.SniperListener;
import auctionsniper.snapshot.SnapshotRegistry;
import auctionsniper.snapshot.SniperSnapshot;
import auctionsniper.values.Increment;
import auctionsniper.values.Item;
import auctionsniper.values.Amount;

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
	@Override public void currentPrice(Amount price, Increment increment, PriceSource source) {
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
	public void register(SnapshotRegistry registry) {
		registry.addSniperSnapshot(snapshot);		
	}
}
