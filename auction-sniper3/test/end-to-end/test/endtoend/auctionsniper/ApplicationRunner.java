package test.endtoend.auctionsniper;

import static test.endtoend.auctionsniper.FakeAuctionServer.XMPP_HOSTNAME;
import static auctionsniper.Main.*;
import auctionsniper.Main;

public class ApplicationRunner {
	public static final String SNIPER_ID = "sniper";
	public static final String SNIPER_PASSWORD = "sniper";
	//TODO とりあえず null
	public static final String SNIPER_XMPP_ID = null;
	private AuctionSniperDriver driver;

	public void startBiddingIn(final FakeAuctionServer auction) {
		Thread thread = new Thread("Test Application") {
			@Override
			public void run() {
				try {
					Main.main(XMPP_HOSTNAME, SNIPER_ID, SNIPER_PASSWORD, auction.getItemId());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		thread.setDaemon(true);
		thread.start();
		driver = new AuctionSniperDriver(1000);
		driver.showsSniperStatus(STATUS_JOINING);
	}

	public void showsSniperHasLostAuction() {
		driver.showsSniperStatus(STATUS_LOST);
	}

	public void stop() {
		if (driver != null) driver.dispose();
	}

	public void hasShownSniperIsBidding() {
		// TODO Auto-generated method stub
		
	}

}
