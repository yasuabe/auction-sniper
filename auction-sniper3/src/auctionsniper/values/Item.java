package auctionsniper.values;

public class Item extends ValueObject {
	
	public final ItemId identifier;
	public final Amount  stopPrice;

	public Item(ItemId identifier, Amount stopPrice) {
		this.identifier = identifier;
		this.stopPrice  = stopPrice;
	}
	@Override
	public String toString() {
		return "Item: " + identifier + ", stop price: " + stopPrice;
	}
	public boolean allowsBid(Amount bid) {
		return stopPrice.isGreatorThanOrEqualTo(bid);
	}
}
