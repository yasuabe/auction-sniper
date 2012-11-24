package auctionsniper.values;

public class ItemId extends ValueObject {
	
	private final String value;

	public ItemId(String value) {
		this.value = value;
	}
	public static ItemId fromString(String value) {
		return new ItemId(value);
	}
	@Override
	public String toString() {
		return value;
	}
}
