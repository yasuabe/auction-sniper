package auctionsniper;

import java.util.ArrayList;
import java.util.List;

import auctionsniper.util.Announcer;

public class SniperPortfolio implements SniperCollector {
	//TODO rule 4. First class collections
	private final Announcer<PortfolioListener> announcer = Announcer
			.to(PortfolioListener.class);
	private final List<AuctionSniper> snipers = new ArrayList<AuctionSniper>();

	public void addPortfolioListener(PortfolioListener listener) {
		announcer.addListener(listener);
	}

	@Override
	public void addSniper(AuctionSniper sniper) {
		snipers.add(sniper);
		announcer.announce().sniperAdded(sniper);
	}
}