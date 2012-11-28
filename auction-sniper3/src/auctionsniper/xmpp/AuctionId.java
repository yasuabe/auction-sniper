package auctionsniper.xmpp;

import auctionsniper.values.ItemId;
import auctionsniper.values.StringObject;

public class AuctionId extends StringObject {
	
	public static final String ITEM_ID_AS_LOGIN  = "auction-%s";
	public static final String AUCTION_ID_FORMAT = //
			ITEM_ID_AS_LOGIN + "@%s/" + XMPPAuctionHouse.AUCTION_RESOURCE;
	
	AuctionId(String value) { super(value); }
	
	static AuctionId from(ItemId itemId, String serviceName) {
		return new AuctionId(String.format(AUCTION_ID_FORMAT, itemId.toString(), serviceName));
	}
}