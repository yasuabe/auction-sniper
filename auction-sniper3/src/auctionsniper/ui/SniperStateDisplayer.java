package auctionsniper.ui;

import javax.swing.SwingUtilities;

import auctionsniper.SniperListener;
import auctionsniper.SniperSnapshot;

public class SniperStateDisplayer implements SniperListener {

	private final MainWindow ui;

	public SniperStateDisplayer(MainWindow ui) {
		this.ui = ui;
	}
	@Override
	public void sniperStateChanged(final SniperSnapshot snapshot) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override public void run() {
				ui.showStatusChanged(snapshot);
			}
		});
	}
}
