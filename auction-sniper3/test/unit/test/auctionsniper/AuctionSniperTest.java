package test.auctionsniper;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.States;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;

import auctionsniper.Auction;
import auctionsniper.AuctionSniper;
import auctionsniper.SniperListener;
import auctionsniper.SniperSnapshot;
import auctionsniper.SniperState;

import static auctionsniper.xmpp.AuctionEventListener.PriceSource;
import static org.hamcrest.CoreMatchers.*;

@RunWith(JMock.class)
public class AuctionSniperTest {
	protected static final String ITEM_ID = "item-id";
	
	private final Mockery context = new Mockery();
	protected SniperListener sniperListener = context.mock(SniperListener.class);
	protected final Auction auction = context.mock(Auction.class);
	private final AuctionSniper sniper = new AuctionSniper(auction, ITEM_ID, sniperListener);

	private final States sniperState = context.states("sniper");
	
	@Test public void reportsLostWhenAuctionClosesImmediately() {
		context.checking(new Expectations() {{
			atLeast(1).of(sniperListener).sniperLost();
		}});
		sniper.auctionClosed();
	}
	
	@Test public void reportsLostIfAuctionClosesWhenBidding() {
		context.checking(new Expectations() {{
			ignoring(auction);
			allowing(sniperListener).sniperBidding(with(aSniperThatIs(SniperState.BIDDING)));
				then(sniperState.is("Bidding"));
			atLeast(1).of(sniperListener).sniperLost();
				when(sniperState.isNot("bidding"));
		}});
		sniper.currentPrice(123, 45, PriceSource.FromOtherBidder);
		sniper.auctionClosed();
	}
	@Test public void bidsHigherAndReportsBiddingWhenNewPriceArrives()  {
		final int price = 1001;
		final int increment = 25;
		final int bid = price + increment;
		context.checking(new Expectations() {{
			one(auction).bid(bid);
			atLeast(1).of(sniperListener).sniperBidding(
					new SniperSnapshot(ITEM_ID, price, bid, SniperState.BIDDING));
		}});
		sniper.currentPrice(price, increment, PriceSource.FromOtherBidder);
	}
	@Test public void reportsIsWinningWhenCurrentPriceComesFromSniper() {
		context.checking(new Expectations() {{
			atLeast(1).of(sniperListener).sniperStateChanged(
					new SniperSnapshot(ITEM_ID, 135, 135, SniperState.WINNING));
		}});
		sniper.currentPrice(123, 12, PriceSource.FromOtherBidder);
		sniper.currentPrice(135, 45, PriceSource.FromSniper);
	}
	@Test public void reportsWonIfAuctionClosesWhenWinning() {
		context.checking(new Expectations() {{
			ignoring(auction);
			allowing(sniperListener).sniperWinning();
				then(sniperState.is("winning"));
			atLeast(1).of(sniperListener).sniperWon();
				when(sniperState.is("winning"));
		}});
		sniper.currentPrice(123, 45, PriceSource.FromSniper);
		sniper.auctionClosed();
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
}