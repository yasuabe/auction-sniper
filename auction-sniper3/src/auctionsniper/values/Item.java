package auctionsniper.values;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


public class Item {
	
	public final String identifier;
	public final Price  stopPrice;

	public Item(String identifier, Price stopPrice) {
		this.identifier = identifier;
		this.stopPrice  = stopPrice;
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
		return "Item: " + identifier + ", stop price: " + stopPrice;
	}

	public boolean allowsBid(Price bid) {
		return stopPrice.isGreatorThanOrEqualTo(bid);
	}
}
