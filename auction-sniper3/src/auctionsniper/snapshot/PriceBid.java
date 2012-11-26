package auctionsniper.snapshot;

import auctionsniper.values.Amount;
import auctionsniper.values.ValueObject;

public class PriceBid extends ValueObject {
	final Amount price;
	final Amount bid;

	PriceBid(Amount price, Amount bid) {
		this.price = price;
		this.bid   = bid;
	}
}