package auctionsniper.values;

public class Item extends ValueObject {
	
	public final ItemId identifier;
	public final Price  stopPrice;

	public Item(ItemId identifier, Price stopPrice) {
		this.identifier = identifier;
		this.stopPrice  = stopPrice;
	}
	@Override
	public String toString() {
		return "Item: " + identifier + ", stop price: " + stopPrice;
	}
	public boolean allowsBid(Price bid) {
		return stopPrice.isGreatorThanOrEqualTo(bid);
	}
}
