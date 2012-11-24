package test.endtoend.auctionsniper;

import static auctionsniper.ui.SnipersTableModel.textFor;
import static org.hamcrest.Matchers.containsString;
import static test.endtoend.auctionsniper.FakeAuctionServer.XMPP_HOSTNAME;

import java.io.IOException;

import auctionsniper.Main;
import auctionsniper.snapshot.BiddingSnapshot;
import auctionsniper.snapshot.FailedSnapshot;
import auctionsniper.snapshot.JoiningSnapshot;
import auctionsniper.snapshot.LosingSnapshot;
import auctionsniper.snapshot.LostSnapshot;
import auctionsniper.snapshot.WinningSnapshot;
import auctionsniper.snapshot.WonSnapshot;
import auctionsniper.ui.MainWindow;
import auctionsniper.ui.SnipersTableModel;

public class ApplicationRunner {
	public static final String SNIPER_ID = "sniper";
	public static final String SNIPER_PASSWORD = "sniper";
	public static final String SNIPER_XMPP_ID = SNIPER_ID + "@" + XMPP_HOSTNAME + "/Auction";

	private AuctionLogDriver logDriver = new AuctionLogDriver();
	private AuctionSniperDriver driver;

	public void startBiddingIn(final FakeAuctionServer... auctions) {
		startSniper();
		for (FakeAuctionServer auction: auctions) {
			openBiddingFor(auction, Integer.MAX_VALUE);
		}
	}
	private void startSniper() {
		logDriver.clearLog();
		Thread thread = new Thread("Test Application") {
			@Override
			public void run() {
				try {
					Main.main(XMPP_HOSTNAME, SNIPER_ID, SNIPER_PASSWORD);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		thread.setDaemon(true);
		thread.start();

		driver = new AuctionSniperDriver(1000);
		driver.hasTitle(MainWindow.APPLICATION_TITLE);
		driver.hasColumnTitles();
	}
	public void showsSniperHasLostAuction(FakeAuctionServer auction, int lastPrice, int lastBid) {
		driver.showsSniperStatus(auction.getItemId(), lastPrice, lastBid,
				SnipersTableModel.textFor(LostSnapshot.class));
	}

	public void stop() {
		if (driver != null) driver.dispose();
	}
	public void hasShownSniperIsBidding(FakeAuctionServer auction, int lastPrice, int lastBid) {
		driver.showsSniperStatus(auction.getItemId(), lastPrice, lastBid,
				SnipersTableModel.textFor(BiddingSnapshot.class));
	}
	public void hasShownSniperIsWinning(FakeAuctionServer auction, int winningBid) {
		driver.showsSniperStatus(auction.getItemId(), winningBid, winningBid,
				SnipersTableModel.textFor(WinningSnapshot.class));
	}
	public void showsSniperHasWonAuction(FakeAuctionServer auction, int lastPrice) {
		driver.showsSniperStatus(auction.getItemId(), lastPrice, lastPrice,
				SnipersTableModel.textFor(WonSnapshot.class));
	}
	public void startBiddingWithStopPrice(FakeAuctionServer auction, int stopPrice) {
	    startSniper();
	    openBiddingFor(auction, stopPrice);
	}
	public void hasShownSniperIsLosing(FakeAuctionServer auction, int lastPrice, int lastBid) {
	    driver.showsSniperStatus(auction.getItemId(), lastPrice, lastBid, 
	    		textFor(LosingSnapshot.class));
	}
	private void openBiddingFor(FakeAuctionServer auction, int stopPrice) {
		final String itemId = auction.getItemId();
		driver.startBiddingWithStopPrice(itemId, stopPrice);
		driver.showsSniperStatus(itemId, 0, 0, textFor(JoiningSnapshot.class));
	}
	public void hasShownSniperHasFailed(FakeAuctionServer auction) {
	    driver.showsSniperStatus(auction.getItemId(), 0, 0, textFor(FailedSnapshot.class));
	}
	public void reportsInvalidMessage(FakeAuctionServer auction,
			String brokenMessage) throws IOException {
		logDriver.hasEntry(containsString(brokenMessage));
	}
}
