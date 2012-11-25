package auctionsniper.values;

public class SniperId extends ValueObject {
	public static SniperId fromString(String value) {
		return new SniperId(value);
	}

	private final String value;
	
	public SniperId(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}
}
