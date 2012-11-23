package auctionsniper.ui;

import javax.swing.SwingUtilities;

import auctionsniper.SniperListener;
import auctionsniper.SniperSnapshot;

public class SwingThreadSniperListener implements SniperListener {

	private final SniperListener sniperListener;

	public SwingThreadSniperListener(SniperListener sniperListener) {
		this.sniperListener = sniperListener;
	}
	@Override
	public void sniperStateChanged(final SniperSnapshot snapshot) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override public void run() {
				sniperListener.sniperStateChanged(snapshot);
			}
		});
	}
}
