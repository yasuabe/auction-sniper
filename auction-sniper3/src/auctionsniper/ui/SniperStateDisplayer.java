package auctionsniper.ui;

import javax.swing.SwingUtilities;

import auctionsniper.SniperListener;
import auctionsniper.SniperState;

public class SniperStateDisplayer implements SniperListener {

	private final MainWindow ui;

	public SniperStateDisplayer(MainWindow ui) {
		this.ui = ui;
	}
	
	@Override
	public void sniperLost() {
		showStatus(MainWindow.STATUS_LOST);
	}

	@Override
	public void sniperBidding(final SniperState sniperState) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override public void run() { 
				ui.showStatusChanged(sniperState, MainWindow.STATUS_BIDDING); 
			}
		});
	}

	private void showStatus(final String status) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override public void run() { ui.showStatus(status); }
		});
	}

	@Override
	public void sniperWinning() {
		showStatus(MainWindow.STATUS_WINNING);
	}
	@Override
	public void sniperWon() {
		showStatus(MainWindow.STATUS_WON);
	}
}
