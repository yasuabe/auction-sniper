package test.auctionsniper;

import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.States;
import org.junit.Test;

import auctionsniper.Auction;
import auctionsniper.AuctionHouse;
import auctionsniper.AuctionSniper;
import auctionsniper.Item;
import auctionsniper.SniperCollector;
import auctionsniper.SniperLauncher;

public class SniperLauncherTest {
	private final Mockery context = new Mockery();
	private final AuctionHouse auctionHouse = context.mock(AuctionHouse.class);
	private final Auction auction = context.mock(Auction.class);
	private final States auctionState = context.states("auction state");
	private final SniperCollector sniperCollector = context.mock(SniperCollector.class);
	private final SniperLauncher launcher = new SniperLauncher(auctionHouse,
			sniperCollector);

	@Test
	public void addsNewSniperToCollectorAndThenJoinsAuction() {
		final String itemId = "item 123";
		context.checking(new Expectations() {{
			//TODO -1
			allowing(auctionHouse).auctionFor(new Item(itemId, -1)); will(returnValue(auction));

			oneOf(auction).addAuctionEventListener(with(sniperForItem(itemId)));
				when(auctionState.isNot("not joined"));
				
			oneOf(sniperCollector).addSniper(with(sniperForItem(itemId)));
				when(auctionState.isNot("not joined"));

			oneOf(auction).join(); then(auctionState.is("joined"));
		}});
		//TODO -1
		launcher.joinAuction(new Item(itemId, -1));
	}
	protected Matcher<AuctionSniper> sniperForItem(String itemId) {
		return new FeatureMatcher<AuctionSniper, String>(
				equalTo(itemId), "sniper with item id", "item") {
			@Override
			protected String featureValueOf(AuctionSniper actual) {
				return actual.getSnapshot().itemId;
			}
		};
	}
}
