package auctionsniper.values;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Price {
	
	public static final Price ZERO = new Price(0);
	private final int value;

	private Price(int value) {
		this.value = value;
	}
	public static Price fromInt(int value) {
		return new Price(value);
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
		return String.valueOf(value);
	}
}
