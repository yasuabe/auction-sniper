package auctionsniper.xmpp;

import static auctionsniper.Main.BID_COMMAND_FORMAT;
import static auctionsniper.Main.JOIN_COMMAND_FORMAT;
import static java.lang.String.format;

import auctionsniper.Auction;
import auctionsniper.Main;
import auctionsniper.util.Announcer;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

public class XMPPAuction implements Auction {
	private Announcer<AuctionEventListener> auctionEventListeners =
			Announcer.to(AuctionEventListener.class);
			 
	//TODO 後で private に戻す
	public final Chat chat;
	
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
		//TODO AUCTION_ID_FORMAT を後でこのクラスに持ってくる
		return String.format(Main.AUCTION_ID_FORMAT, itemId, connection.getServiceName());
	}
}
