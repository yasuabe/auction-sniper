package auctionsniper.snapshot;

import org.apache.commons.lang.builder.ToStringBuilder;

import auctionsniper.util.Defect;
import auctionsniper.values.ItemId;
import auctionsniper.values.Amount;
import auctionsniper.values.ValueObject;

public abstract class SniperSnapshot extends ValueObject {
	public final ItemId   itemId;
	public final PriceBid lastPriceBid;

	//TODO rule 9. No getters/setters/properties
	public Amount lastPrice() { return lastPriceBid.price; }
	public Amount lastBid() {   return lastPriceBid.bid; }
	
	public SniperSnapshot(ItemId itemId, Amount lastPrice, Amount lastBid) {
		this.itemId       = itemId;
		this.lastPriceBid = new PriceBid(lastPrice, lastBid);
	}
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	public static SniperSnapshot joining(ItemId itemId) {
		return new JoiningSnapshot(itemId, Amount.ZERO, Amount.ZERO);
	}
	public SniperSnapshot winning(Amount newLastPrice) {
		return new WinningSnapshot(itemId, newLastPrice, lastPriceBid.bid);
	}
	public SniperSnapshot bidding(Amount newLastPrice, Amount newLastBid) {
		return new BiddingSnapshot(itemId, newLastPrice, newLastBid);
	}
	public SniperSnapshot losing(Amount newLastPrice) {
		return new LosingSnapshot(itemId, newLastPrice, lastPriceBid.bid);
	}
	public SniperSnapshot lost(Amount newLastPrice) {
		return new LostSnapshot(itemId, newLastPrice, Amount.ZERO);
	}
	public SniperSnapshot failed() {
		return new FailedSnapshot(itemId, Amount.ZERO, Amount.ZERO);
	}
	public boolean isForSameItemAs(SniperSnapshot sniperSnapshot) {
		return itemId.equals(sniperSnapshot.itemId);
	}
	public SniperSnapshot closed() {
		throw new Defect("Auction is already closed");
	}
}