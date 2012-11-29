package auctionsniper.values;

public class Increment extends IntValue {
	
	private Increment(int value) {
		super(value);
	}
	public static Increment fromInt(int value) {
		return new Increment(value);
	}
}
