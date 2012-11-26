package test.auctionsniper;

import static org.hamcrest.CoreMatchers.equalTo;
import static test.auctionsniper.util.TestData.newItem;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.Sequence;
import org.jmock.States;
import org.jmock.integration.junit4.JMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import test.auctionsniper.util.TestData;
import auctionsniper.Auction;
import auctionsniper.SniperListener;
import auctionsniper.snapshot.BiddingSnapshot;
import auctionsniper.snapshot.LosingSnapshot;
import auctionsniper.snapshot.LostSnapshot;
import auctionsniper.snapshot.SniperSnapshot;
import auctionsniper.snapshot.WinningSnapshot;
import auctionsniper.snapshot.WonSnapshot;
import auctionsniper.sniper.AuctionSniper;
import auctionsniper.values.Increment;
import auctionsniper.values.Item;
import auctionsniper.values.Price;
import auctionsniper.xmpp.AuctionEventListener.PriceSource;

@RunWith(JMock.class)
public class AuctionSniperTest {
	protected static final String ITEM_ID = "item-id";
	public    static final Item   ITEM    = newItem(ITEM_ID, 1234);
	
	private final Mockery context = new Mockery();
	protected SniperListener sniperListener = context.mock(SniperListener.class);
	protected final Auction auction = context.mock(Auction.class);
	private final AuctionSniper sniper = new AuctionSniper(ITEM, auction);

	private final States sniperState = context.states("sniper");
	
	@Before
	public void attachListener() {
		sniper.addSniperListener(sniperListener);
	}
	@Test public void reportsLostWhenAuctionClosesImmediately() {
		context.checking(new Expectations() {{
			atLeast(1).of(sniperListener).sniperStateChanged(with(aSniperThatIs(LostSnapshot.class)));
		}});
		sniper.auctionClosed();
	}
	
	@Test public void reportsLostIfAuctionClosesWhenBidding() {
		context.checking(new Expectations() {{
			ignoring(auction);
			allowing(sniperListener).sniperStateChanged(with(aSniperThatIs(BiddingSnapshot.class)));
				then(sniperState.is("Bidding"));
				atLeast(1).of(sniperListener).sniperStateChanged(with(aSniperThatIs(LostSnapshot.class)));
				when(sniperState.isNot("bidding"));
		}});
		currentPrice(123, 45, PriceSource.FromOtherBidder);
		sniper.auctionClosed();
	}
	@Test public void bidsHigherAndReportsBiddingWhenNewPriceArrives()  {
		final int price = 1001;
		final int increment = 25;
		final int bid = price + increment;
		context.checking(new Expectations() {{
			one(auction).bid(Price.fromInt(bid));
			atLeast(1).of(sniperListener).sniperStateChanged(
					TestData.biddingSnapshot(ITEM_ID, price, bid));
		}});
		currentPrice(price, increment, PriceSource.FromOtherBidder);
	}
	@Test public void reportsIsWinningWhenCurrentPriceComesFromSniper() {
		context.checking(new Expectations() {{
			ignoring(auction);
			allowing(sniperListener).sniperStateChanged(
					with(aSniperThatIs(BiddingSnapshot.class)));
					then(sniperState.is("bidding"));

			atLeast(1).of(sniperListener).sniperStateChanged(
					TestData.winningSnapshot(ITEM_ID, 135, 135));
					when(sniperState.is("bidding"));
		}});
		currentPrice(123, 12, PriceSource.FromOtherBidder);
		currentPrice(135, 45, PriceSource.FromSniper);
	}
	@Test public void reportsWonIfAuctionClosesWhenWinning() {
		context.checking(new Expectations() {{
			ignoring(auction);
			allowing(sniperListener).sniperStateChanged(with(aSniperThatIs(WinningSnapshot.class)));
				then(sniperState.is("winning"));
				atLeast(1).of(sniperListener).sniperStateChanged(with(aSniperThatIs(WonSnapshot.class)));
				when(sniperState.is("winning"));
		}});
		currentPrice(123, 45, PriceSource.FromSniper);
		sniper.auctionClosed();
	}
	@Test
	public void doesNotBidAndReportsLosingIfPriceAfterWinningIsAboveStopPrice() {
		final int price = 1233;
		final int increment = 25;

		allowingSniperBidding();
		allowingSniperWinning();
		context.checking(new Expectations() {{
			int bid = 123 + 45;
			allowing(auction).bid(Price.fromInt(bid));

			atLeast(1).of(sniperListener).sniperStateChanged(
					TestData.losingSnapshot(ITEM_ID, price, bid));
			when(sniperState.is("winning"));
		}});
		currentPrice(123, 45, PriceSource.FromOtherBidder);
		currentPrice(168, 45, PriceSource.FromSniper);
		currentPrice(price, increment, PriceSource.FromOtherBidder);
	}

	@Test
	public void doesNotBidAndReportsLosingIfFirstPriceIsAboveStopPrice() {
		final int price = 1233;
		final int increment = 25;

		context.checking(new Expectations() {{
			atLeast(1).of(sniperListener).sniperStateChanged(
					TestData.losingSnapshot(ITEM_ID, price, 0));
		}});
		currentPrice(price, increment, PriceSource.FromOtherBidder);
	}

	@Test
	public void reportsLostIfAuctionClosesWhenLosing() {
		allowingSniperLosing();
		context.checking(new Expectations() {{
			atLeast(1).of(sniperListener).sniperStateChanged(
					TestData.lostSnapshot(ITEM_ID, 1230, 0));
			when(sniperState.is("losing"));
		}});

		currentPrice(1230, 456, PriceSource.FromOtherBidder);
		sniper.auctionClosed();
	}

	@Test
	public void continuesToBeLosingOnceStopPriceHasBeenReached() {
		final Sequence states = context.sequence("sniper states");
		final int price1 = 1233;
		final int price2 = 1258;

		context.checking(new Expectations() {{
			atLeast(1).of(sniperListener).sniperStateChanged(
					TestData.losingSnapshot(ITEM_ID, price1, 0));
			inSequence(states);
			atLeast(1).of(sniperListener).sniperStateChanged(
					TestData.losingSnapshot(ITEM_ID, price2, 0));
			inSequence(states);
		}});
		currentPrice(price1, 25, PriceSource.FromOtherBidder);
		currentPrice(price2, 25, PriceSource.FromOtherBidder);
	}
	@Test
	public void reportsFailedIfAuctionFailsWhenBidding() {
		ignoringAuction();
		allowingSniperBidding();

		expectSniperToFailWhenItIs("bidding");

		currentPrice(123, 45, PriceSource.FromOtherBidder);
		sniper.auctionFailed();
	}
	private void ignoringAuction() {
		context.checking(new Expectations() {{
			ignoring(auction);
		}});
	}
	private void expectSniperToFailWhenItIs(final String state) {
		context.checking(new Expectations() {{
			atLeast(1).of(sniperListener).sniperStateChanged(
					TestData.failedSnapshot(ITEM_ID, 00, 0));
			when(sniperState.is(state));
		}});
	}
	@SuppressWarnings("unchecked")
	private Matcher<SniperSnapshot> aSniperThatIs(final Class<? extends SniperSnapshot> clazz) {
		return new FeatureMatcher<SniperSnapshot, Class<? extends SniperSnapshot>>(
				//TODO 後でキャスト見直し
				(Matcher<? super Class<? extends SniperSnapshot>>) equalTo(clazz), "sniper that is ", "was") {
			@Override
			protected Class<? extends SniperSnapshot> featureValueOf(SniperSnapshot actual) {
				return actual.getClass();
			}
		};
	}
	private void allowingSniperBidding() {
		allowSniperStateChange(BiddingSnapshot.class, "bidding");
	}
	private void allowingSniperWinning() {
		allowSniperStateChange(WinningSnapshot.class, "winning");
	}
	private void allowingSniperLosing() {
		allowSniperStateChange(LosingSnapshot.class, "losing");
	}
	private void allowSniperStateChange(final Class<? extends SniperSnapshot> snapshotClass,
			final String oldState) {
		context.checking(new Expectations() {{
				allowing(sniperListener).sniperStateChanged(
						with(aSniperThatIs(snapshotClass)));
				then(sniperState.is(oldState));
			}});
	}
	private void currentPrice(int price, int increment, PriceSource source) {
		sniper.currentPrice(Price.fromInt(price), Increment.fromInt(increment), source);
	}
}