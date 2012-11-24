package test.auctionsniper;

import static auctionsniper.SniperState.*;
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
import auctionsniper.AuctionSniper;
import auctionsniper.SniperListener;
import auctionsniper.SniperSnapshot;
import auctionsniper.SniperState;
import auctionsniper.values.Item;
import auctionsniper.values.Price;

import static auctionsniper.xmpp.AuctionEventListener.PriceSource;
import static org.hamcrest.CoreMatchers.*;

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
			atLeast(1).of(sniperListener).sniperStateChanged(with(aSniperThatIs(LOST)));
		}});
		sniper.auctionClosed();
	}
	
	@Test public void reportsLostIfAuctionClosesWhenBidding() {
		context.checking(new Expectations() {{
			ignoring(auction);
			allowing(sniperListener).sniperStateChanged(with(aSniperThatIs(BIDDING)));
				then(sniperState.is("Bidding"));
				atLeast(1).of(sniperListener).sniperStateChanged(with(aSniperThatIs(LOST)));
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
					TestData.newSnapshot(ITEM_ID, price, bid, BIDDING));
		}});
		currentPrice(price, increment, PriceSource.FromOtherBidder);
	}
	@Test public void reportsIsWinningWhenCurrentPriceComesFromSniper() {
		context.checking(new Expectations() {{
			ignoring(auction);
			allowing(sniperListener).sniperStateChanged(
					with(aSniperThatIs(BIDDING)));
					then(sniperState.is("bidding"));

			atLeast(1).of(sniperListener).sniperStateChanged(
					TestData.newSnapshot(ITEM_ID, 135, 135, WINNING));
					when(sniperState.is("bidding"));
		}});
		currentPrice(123, 12, PriceSource.FromOtherBidder);
		currentPrice(135, 45, PriceSource.FromSniper);
	}
	@Test public void reportsWonIfAuctionClosesWhenWinning() {
		context.checking(new Expectations() {{
			ignoring(auction);
			allowing(sniperListener).sniperStateChanged(with(aSniperThatIs(WINNING)));
				then(sniperState.is("winning"));
				atLeast(1).of(sniperListener).sniperStateChanged(with(aSniperThatIs(WON)));
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
					TestData.newSnapshot(ITEM_ID, price, bid, LOSING));
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
					TestData.newSnapshot(ITEM_ID, price, 0, LOSING));
		}});
		currentPrice(price, increment, PriceSource.FromOtherBidder);
	}

	@Test
	public void reportsLostIfAuctionClosesWhenLosing() {
		allowingSniperLosing();
		context.checking(new Expectations() {{
			atLeast(1).of(sniperListener).sniperStateChanged(
					TestData.newSnapshot(ITEM_ID, 1230, 0, LOST));
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
					TestData.newSnapshot(ITEM_ID, price1, 0, LOSING));
			inSequence(states);
			atLeast(1).of(sniperListener).sniperStateChanged(
					TestData.newSnapshot(ITEM_ID, price2, 0, LOSING));
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
					TestData.newSnapshot(ITEM_ID, 00, 0, SniperState.FAILED));
			when(sniperState.is(state));
		}});
	}
	private Matcher<SniperSnapshot> aSniperThatIs(final SniperState state) {
		return new FeatureMatcher<SniperSnapshot, SniperState>(
				equalTo(state), "sniper that is ", "was") {
			@Override
			protected SniperState featureValueOf(SniperSnapshot actual) {
				return actual.state;
			}
		};
	}
	private void allowingSniperBidding() {
		allowSniperStateChange(BIDDING, "bidding");
	}
	private void allowingSniperWinning() {
		allowSniperStateChange(WINNING, "winning");
	}
	private void allowingSniperLosing() {
		allowSniperStateChange(LOSING, "losing");
	}
	private void allowSniperStateChange(final SniperState newState,
			final String oldState) {
		context.checking(new Expectations() {{
				allowing(sniperListener).sniperStateChanged(
						with(aSniperThatIs(newState)));
				then(sniperState.is(oldState));
			}});
	}
	private void currentPrice(int price, int increment, PriceSource source) {
		sniper.currentPrice(Price.fromInt(price), increment, source);
	}
}