package auctionsniper.snapshot;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import auctionsniper.values.Price;
import auctionsniper.values.ValueObject;

public class PriceBid extends ValueObject {
	final Price price;
	final Price bid;

	PriceBid(Price price, Price bid) {
		this.price = price;
		this.bid   = bid;
	}
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
}