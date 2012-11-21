package auctionsniper.xmpp;

public interface AuctionEventListener {

	enum PriceSource {
		FromSniper,
		FromOtherBidder
	}
	void auctionClosed();
	void currentPrice(int i, int j);
}
