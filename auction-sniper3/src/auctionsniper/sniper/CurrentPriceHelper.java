package auctionsniper.sniper;

import auctionsniper.Auction;
import auctionsniper.AuctionEventListener.PriceSource;
import auctionsniper.snapshot.SniperSnapshot;
import auctionsniper.values.Increment;
import auctionsniper.values.Item;
import auctionsniper.values.Amount;

public class CurrentPriceHelper {
	public CurrentPriceHelper(Auction auction, Item item) {
		this.auction = auction;
		this.item    = item;
	}
	private final Auction  auction;
	private final Item     item;
	
	CurrentPriceProcessor fromSniper = new CurrentPriceProcessor() {
		@Override public SniperSnapshot process(Amount price, Increment increment, SniperSnapshot snapshot) {
			return snapshot.winning(price);
		}
	};
	CurrentPriceProcessor fromOtherBidder = new CurrentPriceProcessor() {
		@Override public SniperSnapshot process(Amount price, Increment increment, SniperSnapshot snapshot) {
			Amount   bid       = price.add(increment);
			boolean allowsBid = item.allowsBid(bid);
			if (allowsBid) auction.bid(bid);
			return allowsBid ? snapshot.bidding(price, bid): snapshot.losing(price); 
		}
	};
	CurrentPriceProcessors processors = new CurrentPriceProcessors();
	{
		processors.put(PriceSource.FromSniper,      fromSniper);
		processors.put(PriceSource.FromOtherBidder, fromOtherBidder);
	}

	SniperSnapshot process(Amount price, Increment increment,
			PriceSource source, SniperSnapshot snapshot) {
		return processors.get(source).process(price, increment, snapshot);
	}
}