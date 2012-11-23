package auctionsniper;

public enum SniperState {
	JOINING {
		@Override public SniperState whenAuctionClosed() { return LOST; }
	},
	BIDDING {
		@Override public SniperState whenAuctionClosed() { return LOST; }
	},
	WINNING {
		@Override public SniperState whenAuctionClosed() { return WON; }
	},
	LOST, //
	WON;

	public SniperState whenAuctionClosed() {
		// TODO 後で Defect に変更
		throw new AssertionError();
	}
}
