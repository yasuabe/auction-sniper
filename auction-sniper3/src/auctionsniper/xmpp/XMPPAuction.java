package auctionsniper.xmpp;

import static auctionsniper.Main.BID_COMMAND_FORMAT;
import static auctionsniper.Main.JOIN_COMMAND_FORMAT;
import static java.lang.String.format;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import auctionsniper.Auction;
import auctionsniper.Main;
import auctionsniper.util.Announcer;

public class XMPPAuction implements Auction {
	public static final String ITEM_ID_AS_LOGIN = "auction-%s";
	public static final String AUCTION_ID_FORMAT = ITEM_ID_AS_LOGIN + "@%s/"
			+ Main.AUCTION_RESOURCE;
	
	private Announcer<AuctionEventListener> auctionEventListeners =
			Announcer.to(AuctionEventListener.class);
			 
	private final Chat chat;

	public XMPPAuction(XMPPConnection connection, String itemId) {
		this.chat = connection.getChatManager().createChat(
				auctionId(itemId, connection), new AuctionMessageTranslator(
						connection.getUser(),  
						auctionEventListeners.announce()));
	}
	@Override public void bid(int amount) {
		sendMessage(format(BID_COMMAND_FORMAT, amount));
	}
	@Override
	public void join() {
		sendMessage(JOIN_COMMAND_FORMAT);
	}
	private void sendMessage(final String message) {
		try {
			chat.sendMessage(message);
		} catch (XMPPException e) {
			e.printStackTrace();
		}
	}
	private static String auctionId(String itemId, XMPPConnection connection) {
		return String.format(AUCTION_ID_FORMAT, itemId, connection.getServiceName());
	}
	@Override
	public void addAuctionEventListener(AuctionEventListener auctionSniper) {
		auctionEventListeners.addListener(auctionSniper);
	}
}
