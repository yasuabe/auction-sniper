package auctionsniper;

import auctionsniper.snapshot.SniperSnapshot;
import auctionsniper.values.Increment;
import auctionsniper.values.Item;
import auctionsniper.values.Price;
import auctionsniper.xmpp.AuctionEventListener.PriceSource;

public class AuctionSniperCore {
	private final AnnouncerToSniperListener listeners = new AnnouncerToSniperListener();
	private final CurrentPriceHelper        helper;

	public AuctionSniperCore(Auction auction, Item item) {
		this.helper = new CurrentPriceHelper(auction, item);
	}
	void notifyChange(SniperSnapshot snapshot) {
		listeners.sniperStateChanged(snapshot);
	}
	SniperSnapshot process(Price price, Increment increment,
			PriceSource source, SniperSnapshot snapshot) {
		return helper.process(price, increment, source, snapshot);
	}
	public void addListener(SniperListener listener) {
		listeners.addListener(listener);
	}
}