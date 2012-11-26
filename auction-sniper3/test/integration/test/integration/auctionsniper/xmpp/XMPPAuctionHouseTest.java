package test.integration.auctionsniper.xmpp;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertTrue;
import static test.auctionsniper.util.TestData.newItem;

import java.util.concurrent.CountDownLatch;

import org.jivesoftware.smack.XMPPException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import test.endtoend.auctionsniper.ApplicationRunner;
import test.endtoend.auctionsniper.FakeAuctionServer;
import auctionsniper.Auction;
import auctionsniper.connection.ConnectionInfo;
import auctionsniper.values.Increment;
import auctionsniper.values.Amount;
import auctionsniper.xmpp.AuctionEventListener;
import auctionsniper.xmpp.XMPPAuctionHouse;

public class XMPPAuctionHouseTest {
	private final FakeAuctionServer auctionServer = new FakeAuctionServer(
			"item-54321");
	private XMPPAuctionHouse auctionHouse;

	@Before
	public void openConnection() throws Exception {
		auctionHouse = XMPPAuctionHouse.connect(new ConnectionInfo(new String[]{
				FakeAuctionServer.XMPP_HOSTNAME, 
				ApplicationRunner.SNIPER_ID, 
				ApplicationRunner.SNIPER_PASSWORD}));
	}

	@After
	public void closeConnection() {
		if (auctionHouse != null) {
			auctionHouse.disconnect();
		}
	}

	@Before
	public void startAuction() throws XMPPException {
		auctionServer.startSellingItem();
	}

	@After
	public void stopAuction() {
		auctionServer.stop();
	}

	@Test
	public void receivesEventsFromAuctionServerAfterJoining() throws Exception {
		CountDownLatch auctionWasClosed = new CountDownLatch(1);

		//stop price は このテストでは問題にならないので -1
		Auction auction = auctionHouse.auctionFor(newItem(auctionServer.getItemId(), -1));
		auction.addAuctionEventListener(auctionClosedListener(auctionWasClosed));
		
		auction.join();
		auctionServer.hasReceivedJoinRequestFrom(ApplicationRunner.SNIPER_XMPP_ID);
		auctionServer.announceClosed();

		assertTrue("should have been closed", auctionWasClosed.await(2, SECONDS));
	}

	private AuctionEventListener auctionClosedListener(final CountDownLatch auctionWasClosed) {

		return new AuctionEventListener() {
			public void auctionClosed() {
				auctionWasClosed.countDown();
			}
			public void currentPrice(Amount price, Increment increment, PriceSource priceSource) {}
			@Override
			public void auctionFailed() {}
		};
	}
}
