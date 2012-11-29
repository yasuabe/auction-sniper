package auctionsniper.values;

public class ItemId extends StringObject {
	public ItemId(String value) {
		super(value);
	}
	public static ItemId fromString(String value) {
		return new ItemId(value);
	}
}
