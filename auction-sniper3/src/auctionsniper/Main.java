package auctionsniper;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import auctionsniper.ui.MainWindow;
import auctionsniper.ui.SnipersTableModel;
import auctionsniper.ui.SwingThreadSniperListener;
import auctionsniper.util.Announcer;
import auctionsniper.xmpp.AuctionEventListener;
import auctionsniper.xmpp.AuctionMessageTranslator;
import auctionsniper.xmpp.XMPPAuction;

public class Main {
	public static final int ARG_HOSTNAME = 0;
	public static final int ARG_USERNAME = 1;
	public static final int ARG_PASSWORD = 2;
	public static final int ARG_ITEM_ID = 3;
	
	public static final String MAIN_WINDOW_NAME = "Auction Sniper Main";
	public static final String SNIPER_STATUS_NAME = "sniper status";
	
	public static final String AUCTION_RESOURCE = "Auction";
	public static final String JOIN_COMMAND_FORMAT = "SOLVersion: 1.1; Command: JOIN;";
	public static final String BID_COMMAND_FORMAT = "SOLVersion: 1.1; Command: BID; Price: %d;";

	private MainWindow ui;
	private final List<Auction> notToBeGCd = new ArrayList<Auction>();
	private final SnipersTableModel snipers = new SnipersTableModel();
	
	public Main() throws Exception {
		startUserInterface();
	}
	private void startUserInterface() throws Exception {
		SwingUtilities.invokeAndWait(new Runnable() {
			@Override public void run() {
				ui = new MainWindow(snipers);
			}
		});
	}
	public static void main(String... args) throws Exception {
		Main main = new Main();
		XMPPConnection connection = connection(//
				args[ARG_HOSTNAME], args[ARG_USERNAME], args[ARG_PASSWORD]);
		main.disconnectWhenUICloses(connection);
		main.addUserRequestListenerFor(connection);
	}
	private void addUserRequestListenerFor(final XMPPConnection connection) {
		ui.addUserRequestListener(new UserRequestListener() {
			@Override
			public void joinAuction(String itemId) {
				snipers.addSniper(SniperSnapshot.joining(itemId));
				Auction auction = new XMPPAuction(connection, itemId);

				notToBeGCd.add(auction);
				
				auction.addAuctionEventListener(
						//TODO AuctionSniper コンストラクタのauction と itemId の順序を変える
						new AuctionSniper(itemId, auction, new SwingThreadSniperListener(snipers)));
				auction.join();
			}
		});
	}
	private void disconnectWhenUICloses(final XMPPConnection connection) {
		ui.addWindowListener(new WindowAdapter() {
			@Override public void windowClosed(WindowEvent e) {
				connection.disconnect();
			}
		});
	}
	//TODO private からpublic に変えた。後で戻す。
	public static XMPPConnection connection(
			String hostName, String userName, String password) throws XMPPException {
		XMPPConnection connection = new XMPPConnection(hostName);
		connection.connect();
		connection.login(userName, password, AUCTION_RESOURCE);
		
		return connection;
	}
}
