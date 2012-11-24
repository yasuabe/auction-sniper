package auctionsniper.snapshot;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import auctionsniper.SniperState;
import auctionsniper.util.Defect;
import auctionsniper.values.ItemId;
import auctionsniper.values.Price;

public abstract class SniperSnapshot {
	public final ItemId   itemId;
	public final PriceBid lastPriceBid;

	//TODO rule 9. No getters/setters/properties
	public Price lastPrice() { return lastPriceBid.price; }
	public Price lastBid() {   return lastPriceBid.bid; }
	
	//TODO getter禁止だけどリファクタ過程で一時的に使う。後で削除。
	public abstract SniperState getState();
	
	public SniperSnapshot(ItemId itemId, Price lastPrice, Price lastBid) {
		this.itemId    = itemId;
		this.lastPriceBid = new PriceBid(lastPrice, lastBid);
	}
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	public static SniperSnapshot joining(ItemId itemId) {
		return new JoiningSnapshot(itemId, Price.ZERO, Price.ZERO);
	}
	public SniperSnapshot winning(Price newLastPrice) {
		return new WinningSnapshot(itemId, newLastPrice, lastPriceBid.bid);
	}
	public SniperSnapshot bidding(Price newLastPrice, Price newLastBid) {
		return new BiddingSnapshot(itemId, newLastPrice, newLastBid);
	}
	public SniperSnapshot losing(Price newLastPrice) {
		return new LosingSnapshot(itemId, newLastPrice, lastPriceBid.bid);
	}
	public SniperSnapshot lost(Price newLastPrice) {
		return new LostSnapshot(itemId, newLastPrice, Price.ZERO);
	}
	public SniperSnapshot failed() {
		return new FailedSnapshot(itemId, Price.ZERO, Price.ZERO);
	}
	public boolean isForSameItemAs(SniperSnapshot sniperSnapshot) {
		return itemId.equals(sniperSnapshot.itemId);
	}
	public SniperSnapshot closed() {
		throw new Defect("Auction is already closed");
	}
}