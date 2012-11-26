package auctionsniper.xmpp;

import static auctionsniper.util.CommandFormat.BID;
import static auctionsniper.util.CommandFormat.JOIN;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import auctionsniper.Auction;
import auctionsniper.values.ItemId;
import auctionsniper.values.Price;
import auctionsniper.values.SniperId;

public class XMPPAuction implements Auction {
	
	public static final String ITEM_ID_AS_LOGIN  = "auction-%s";

	private AnnouncerToAuctionEventListener auctionEventListeners =
			new AnnouncerToAuctionEventListener();
	private final Chat chat;
	
	public XMPPAuction(XMPPConnection connection, ItemId itemId, XMPPFailureReporter failureReporter) {
		AuctionMessageTranslator translator = translatorFor(connection, failureReporter);
		this.chat = connection.getChatManager().createChat(
				AuctionId.from(itemId, connection.getServiceName()), translator);
	    addAuctionEventListener(chatDisconnectorFor(translator));
	}
	@Override public void bid(Price amount) { sendMessage(BID.format(amount)); }
	@Override public void join() {            sendMessage(JOIN.format());      }
	@Override public void addAuctionEventListener(AuctionEventListener auctionSniper) {
		auctionEventListeners.addListener(auctionSniper);
	}
	private void sendMessage(final String message) {
		try { chat.sendMessage(message); } 
		catch (XMPPException e) { e.printStackTrace(); }
	}
	private AuctionMessageTranslator translatorFor(XMPPConnection connection, XMPPFailureReporter failureReporter) {
		return new AuctionMessageTranslator(SniperId.fromString(connection.getUser()),
				auctionEventListeners.announce(), failureReporter);
	}
	private AuctionEventListener chatDisconnectorFor(final AuctionMessageTranslator translator) {
		return new AuctionEventAdapter() {
			public void auctionFailed() {
				chat.removeMessageListener(translator);
			}
		};
	}
}
