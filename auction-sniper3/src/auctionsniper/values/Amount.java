package auctionsniper.values;


public class Amount extends IntValue {
	
	public static final Amount ZERO = new Amount(0);

	private Amount(int value) {
		super(value); 
	}
	public static Amount fromInt(int value) {
		return new Amount(value);
	}
	public Amount add(Increment increment) {
		return new Amount(value + increment.toInt());
	}
	public boolean isGreatorThanOrEqualTo(Amount bid) {
		return value >= bid.value;
	}
}
