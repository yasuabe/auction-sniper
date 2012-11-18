package auctionsniper;

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

	private MainWindow ui;
	
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
		XMPPConnection connection = connectTo(args[ARG_HOSTNAME],
													args[ARG_USERNAME],
													args[ARG_PASSWORD]);
		Chat chat = connection.getChatManager().createChat(
				auctionId(args[ARG_ITEM_ID], connection), new MessageListener() {
					public void processMessage(Chat aChat, Message message) {
					}
				});
		chat.sendMessage(new Message());
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
