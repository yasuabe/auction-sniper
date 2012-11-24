package auctionsniper.values;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Increment {
	
	private final int value;
	
	private Increment(int value) {
		this.value = value;
	}
	public static Increment fromInt(int value) {
		return new Increment(value);
	}
	int toInt() {
		return value;
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
