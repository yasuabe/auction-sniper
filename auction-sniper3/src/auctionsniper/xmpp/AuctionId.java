package auctionsniper.xmpp;

import auctionsniper.values.ItemId;

public class AuctionId {
	public static final String ITEM_ID_AS_LOGIN  = "auction-%s";
	public static final String AUCTION_ID_FORMAT = //
			ITEM_ID_AS_LOGIN + "@%s/" + XMPPAuctionHouse.AUCTION_RESOURCE;
	
	static String from(ItemId itemId, String serviceName) {
		return String.format(AUCTION_ID_FORMAT, itemId.toString(), serviceName);
	}
}