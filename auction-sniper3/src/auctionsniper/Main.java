package auctionsniper;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.SwingUtilities;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import auctionsniper.ui.MainWindow;

public class Main {
	public static final int ARG_HOSTNAME = 0;
	public static final int ARG_USERNAME = 1;
	public static final int ARG_PASSWORD = 2;
	public static final int ARG_ITEM_ID = 3;
	
	public static final String STATUS_JOINING = "Joining";
	public static final String STATUS_LOST = "Lost";
	public static final String MAIN_WINDOW_NAME = "Auction Sniper Main";
	public static final String SNIPER_STATUS_NAME = "sniper status";
	
	public static final String AUCTION_RESOURCE = "Auction";
	public static final String ITEM_ID_AS_LOGIN = "auction-%s";
	public static final String AUCTION_ID_FORMAT = 
			ITEM_ID_AS_LOGIN + "@%s/" + AUCTION_RESOURCE;

	public static final String JOIN_COMMAND_FORMAT = "SOLVersion: 1.1; Command: JOIN;";
	public static final String BID_COMMAND_FORMAT = "SOLVersion: 1.1; Command: BID; Price: %d;";

	private MainWindow ui;
	private Chat notToBeGCd;
	
	public Main() throws Exception {
		startUserInterface();
	}
	private void startUserInterface() throws Exception {
		SwingUtilities.invokeAndWait(new Runnable() {
			@Override
			public void run() {
				ui = new MainWindow();
			}
		});
	}
	public static void main(String... args) throws Exception {
		Main main = new Main();
		main.joinAuction(
				connectTo(args[ARG_HOSTNAME], args[ARG_USERNAME],
						args[ARG_PASSWORD]), args[ARG_ITEM_ID]);
	}

	private void joinAuction(XMPPConnection connection, String itemId)
			throws XMPPException {
		disconnectWhenUICloses(connection);
		Chat chat = connection.getChatManager().createChat(
				auctionId(itemId, connection), new MessageListener() {
					public void processMessage(Chat arg0, Message arg1) {
						SwingUtilities.invokeLater(new Runnable() {
							@Override
							public void run() {
								ui.showStatus(Main.STATUS_LOST);
							}
						});
					}
				});
		this.notToBeGCd = chat;
		chat.sendMessage(JOIN_COMMAND_FORMAT);
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

	private static XMPPConnection connectTo(
			String hostName, String userName, String password) throws XMPPException {
		XMPPConnection connection = new XMPPConnection(hostName);
		connection.connect();
		connection.login(userName, password, AUCTION_RESOURCE);
		
		return connection;
	}
}
