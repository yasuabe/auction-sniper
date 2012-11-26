package auctionsniper;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.SwingUtilities;

import auctionsniper.connection.ConnectionInfo;
import auctionsniper.ui.MainWindow;
import auctionsniper.xmpp.XMPPAuctionHouse;

public class Main {
	//TODO: 置き場所再考
	public static final String MAIN_WINDOW_NAME   = "Auction Sniper Main";
	public static final String SNIPER_STATUS_NAME = "sniper status";
	
	private MainWindow ui;
	private final SniperPortfolio portfolio = new SniperPortfolio();
	
	public Main() throws Exception {
		startUserInterface();
	}
	private void startUserInterface() throws Exception {
		SwingUtilities.invokeAndWait(new Runnable() {
			@Override public void run() {
				ui = new MainWindow(portfolio);
			}
		});
	}
	public static void main(String... args) throws Exception {
		XMPPAuctionHouse auctionHouse = XMPPAuctionHouse.connect(new ConnectionInfo(args));

		Main main = new Main();
		main.disconnectWhenUICloses(auctionHouse);
		main.addUserRequestListenerFor(auctionHouse);
	}
	private void addUserRequestListenerFor(final AuctionHouse auctionHouse) {
		ui.add(new SniperLauncher(auctionHouse, portfolio));
	}
	private void disconnectWhenUICloses(final XMPPAuctionHouse auctionHouse) {
		ui.addWindowListener(new WindowAdapter() {
			@Override public void windowClosed(WindowEvent e) {
				auctionHouse.disconnect();
			}
		});
	}
}
