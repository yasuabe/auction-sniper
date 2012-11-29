package auctionsniper.values;

public class SniperId extends StringObject {
	public static SniperId fromString(String value) {
		return new SniperId(value);
	}
	public SniperId(String value) {
		super(value);
	}
}
