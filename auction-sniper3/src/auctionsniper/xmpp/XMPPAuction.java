package auctionsniper.xmpp;

import static auctionsniper.util.CommandFormat.BID;
import static auctionsniper.util.CommandFormat.JOIN;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import auctionsniper.Auction;
import auctionsniper.util.Announcer;
import auctionsniper.values.Increment;
import auctionsniper.values.Price;
import auctionsniper.values.SniperId;
import auctionsniper.values.ValueObject;

//TODO rule 7. Keep all entities small
public class XMPPAuction implements Auction {
	public static final String ITEM_ID_AS_LOGIN  = "auction-%s";
	public static final String AUCTION_ID_FORMAT = ITEM_ID_AS_LOGIN + "@%s/"
			+ XMPPAuctionHouse.AUCTION_RESOURCE;

	//TODO rule 8. No classes with more than two instance variables
	private Announcer<AuctionEventListener> auctionEventListeners =
			Announcer.to(AuctionEventListener.class);
			 
	private final Chat chat;
	private final XMPPFailureReporter failureReporter;
	
	public XMPPAuction(XMPPConnection connection, ValueObject itemId, XMPPFailureReporter failureReporter) {
		this.failureReporter = failureReporter;
		AuctionMessageTranslator translator = translatorFor(connection);
		this.chat = connection.getChatManager().createChat(
				auctionId(itemId.toString(), connection), translator);
	    addAuctionEventListener(chatDisconnectorFor(translator));
	}
	@Override public void bid(Price amount) {
		sendMessage(BID.format(amount));
	}
	@Override
	public void join() {
		sendMessage(JOIN.format());
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

	private AuctionMessageTranslator translatorFor(XMPPConnection connection) {
		return new AuctionMessageTranslator(SniperId.fromString(connection.getUser()),
				auctionEventListeners.announce(), failureReporter);
	}
	//TODO 長すぎるメソッド
	private AuctionEventListener chatDisconnectorFor(
			final AuctionMessageTranslator translator) {

		return new AuctionEventListener() {
			public void auctionFailed() {
				chat.removeMessageListener(translator);
			}
			public void auctionClosed() {}
			public void currentPrice(Price price, Increment increment,
					PriceSource priceSource) {}
		};
	}
}
