package auctionsniper.xmpp;

import static auctionsniper.Main.BID_COMMAND_FORMAT;
import static auctionsniper.Main.JOIN_COMMAND_FORMAT;
import static java.lang.String.format;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import auctionsniper.Auction;
import auctionsniper.util.Announcer;
import auctionsniper.values.Increment;
import auctionsniper.values.Price;
import auctionsniper.values.ValueObject;

public class XMPPAuction implements Auction {
	public static final String ITEM_ID_AS_LOGIN  = "auction-%s";
	public static final String AUCTION_ID_FORMAT = ITEM_ID_AS_LOGIN + "@%s/"
			+ XMPPAuctionHouse.AUCTION_RESOURCE;
	
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
		sendMessage(format(BID_COMMAND_FORMAT, amount.toInt()));
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

	private AuctionMessageTranslator translatorFor(XMPPConnection connection) {
		return new AuctionMessageTranslator(connection.getUser(),
				auctionEventListeners.announce(), failureReporter);
	}
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
