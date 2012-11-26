package auctionsniper.values;


public class StringObject extends ValueObject {
	private final String value;
	public StringObject(String value) {
		this.value = value;
	}
	@Override public String toString() {
		return value;
	}
}
