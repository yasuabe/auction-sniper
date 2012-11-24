package auctionsniper;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import auctionsniper.values.Price;

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

	//TODO 後で int -> Price に。テストも書く。
	public boolean allowsBid(int bid) {
		return bid <= stopPrice.toInt();
	}
}
