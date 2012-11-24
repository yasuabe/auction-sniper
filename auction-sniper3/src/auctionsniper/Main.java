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
	public static final String ITEM_ID_AS_LOGIN = "auction-%s";
	public static final String AUCTION_ID_FORMAT = 
			ITEM_ID_AS_LOGIN + "@%s/" + AUCTION_RESOURCE;

	public static final String JOIN_COMMAND_FORMAT = "SOLVersion: 1.1; Command: JOIN;";
	public static final String BID_COMMAND_FORMAT = "SOLVersion: 1.1; Command: BID; Price: %d;";

	private MainWindow ui;
	private final List<Chat> notToBeGCd = new ArrayList<Chat>();
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
				//TODO 後でAuctionに戻す
				XMPPAuction auction = new XMPPAuction(connection, itemId);
				
				Announcer<AuctionEventListener> auctionEventListeners = 
						Announcer.to(AuctionEventListener.class);
				auction.chat.addMessageListener(new AuctionMessageTranslator(connection.getUser(),
						auctionEventListeners.announce()));

				notToBeGCd.add(auction.chat);
				
				auctionEventListeners.addListener(
						new AuctionSniper(auction, itemId, new SwingThreadSniperListener(snipers)));
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
	private static String auctionId(String itemId, XMPPConnection connection) {
		return String.format(AUCTION_ID_FORMAT, itemId, connection.getServiceName());
	}

	private static XMPPConnection connection(
			String hostName, String userName, String password) throws XMPPException {
		XMPPConnection connection = new XMPPConnection(hostName);
		connection.connect();
		connection.login(userName, password, AUCTION_RESOURCE);
		
		return connection;
	}
}
