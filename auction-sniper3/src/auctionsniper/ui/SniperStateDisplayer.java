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
	public void sniperLost() {
		showStatus(MainWindow.STATUS_LOST);
	}

	private void showStatus(final String status) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override public void run() { ui.showStatus(status); }
		});
	}
	@Override
	public void sniperWon() {
		showStatus(MainWindow.STATUS_WON);
	}

	@Override
	public void sniperStateChanged(final SniperSnapshot snapshot) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				ui.showStatusChanged(snapshot);
			}
		});
	}
}
