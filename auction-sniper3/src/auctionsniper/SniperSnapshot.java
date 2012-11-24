package auctionsniper;

import static auctionsniper.SniperState.*;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import auctionsniper.values.Price;

public class SniperSnapshot {

	//TODO rule 8. No classes with more than two instance variables
	public final String      itemId; //TODO rule 3. Wrap all primitives and Strings
	public final Price       lastPrice;
	public final Price       lastBid;
	public final SniperState state;

	public SniperSnapshot(String itemId, Price lastPrice, Price lastBid, SniperState state) {
		this.itemId    = itemId;
		this.lastPrice = lastPrice;
		this.lastBid   = lastBid;
		this.state     = state;
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

	public static SniperSnapshot joining(String itemId) {
		return new SniperSnapshot(itemId, Price.ZERO, Price.ZERO, JOINING);
	}
	public SniperSnapshot winning(Price newLastPrice) {
		return new SniperSnapshot(itemId, newLastPrice, lastBid, WINNING);
	}
	public SniperSnapshot bidding(Price newLastPrice, Price newLastBid) {
		return new SniperSnapshot(itemId, newLastPrice, newLastBid, BIDDING);
	}
	public SniperSnapshot losing(Price newLastPrice) {
		return new SniperSnapshot(itemId, newLastPrice, lastBid, SniperState.LOSING);
	}
	public SniperSnapshot closed() {
		return new SniperSnapshot(itemId, lastPrice, lastBid, state.whenAuctionClosed());
	}
	public boolean isForSameItemAs(SniperSnapshot sniperSnapshot) {
		return itemId.equals(sniperSnapshot.itemId);
	}
	public SniperSnapshot failed() {
		return new SniperSnapshot(itemId, Price.ZERO, Price.ZERO, SniperState.FAILED);
	}
}