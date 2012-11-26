package auctionsniper;

import java.util.ArrayList;
import java.util.List;

import auctionsniper.sniper.AuctionSniper;

public class SniperPortfolio implements SniperCollector {
	private final AnnouncerToPortfolioListener announcer = new AnnouncerToPortfolioListener();
	private final List<AuctionSniper> snipers = new ArrayList<AuctionSniper>();

	public void addPortfolioListener(PortfolioListener listener) {
		announcer.addListener(listener);
	}

	@Override public void addSniper(AuctionSniper sniper) {
		snipers.add(sniper);//GC 防止のために必要らしい
		announcer.announceSniperAdded(sniper);
	}
}