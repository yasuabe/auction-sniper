package auctionsniper.values;


public class Price extends IntValue {
	
	public static final Price ZERO = new Price(0);

	private Price(int value) {
		super(value); 
	}
	public static Price fromInt(int value) {
		return new Price(value);
	}
	public Price add(Increment increment) {
		return new Price(value + increment.toInt());
	}
	public boolean isGreatorThanOrEqualTo(Price bid) {
		return value >= bid.value;
	}
}
