package auctionsniper.xmpp;

import static auctionsniper.util.CommandFormat.BID;
import static auctionsniper.util.CommandFormat.JOIN;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import auctionsniper.Auction;
import auctionsniper.values.Price;
import auctionsniper.values.SniperId;
import auctionsniper.values.ValueObject;

//TODO rule 7. Keep all entities small
public class XMPPAuction implements Auction {
	public static final String ITEM_ID_AS_LOGIN  = "auction-%s";
	public static final String AUCTION_ID_FORMAT = ITEM_ID_AS_LOGIN + "@%s/"
			+ XMPPAuctionHouse.AUCTION_RESOURCE;

	private AnnouncerToAuctionEventListener auctionEventListeners =
			new AnnouncerToAuctionEventListener();
	private final Chat chat;
	
	public XMPPAuction(XMPPConnection connection, ValueObject itemId, XMPPFailureReporter failureReporter) {
		AuctionMessageTranslator translator = translatorFor(connection, failureReporter);
		this.chat = connection.getChatManager().createChat(
				auctionId(itemId.toString(), connection), translator);
	    addAuctionEventListener(chatDisconnectorFor(translator));
	}
	@Override public void bid(Price amount) {
		sendMessage(BID.format(amount));
	}
	@Override public void join() {
		sendMessage(JOIN.format());
	}
	private void sendMessage(final String message) {
		try { chat.sendMessage(message); } 
		catch (XMPPException e) { e.printStackTrace(); }
	}
	private static String auctionId(String itemId, XMPPConnection connection) {
		return String.format(AUCTION_ID_FORMAT, itemId, connection.getServiceName());
	}
	@Override
	public void addAuctionEventListener(AuctionEventListener auctionSniper) {
		auctionEventListeners.addListener(auctionSniper);
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
