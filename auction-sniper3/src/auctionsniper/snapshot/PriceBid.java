package auctionsniper.snapshot;

import auctionsniper.values.Price;
import auctionsniper.values.ValueObject;

public class PriceBid extends ValueObject {
	final Price price;
	final Price bid;

	PriceBid(Price price, Price bid) {
		this.price = price;
		this.bid   = bid;
	}
}