package auctionsniper.xmpp;

import static auctionsniper.util.CommandFormat.BID;
import static auctionsniper.util.CommandFormat.JOIN;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import auctionsniper.Auction;
import auctionsniper.AuctionEventListener;
import auctionsniper.values.ItemId;
import auctionsniper.values.Amount;
import auctionsniper.values.SniperId;
import auctionsniper.xmpp.translator.AuctionMessageTranslator;

public class XMPPAuction implements Auction {
	
	private final AnnouncerToAuctionEventListener announcer = new AnnouncerToAuctionEventListener();
	private final ChatWrapper                     chat;
	
	public XMPPAuction(XMPPConnection connection, ItemId itemId,
			XMPPFailureReporter failureReporter) {

		AuctionMessageTranslator translator = translatorFor(connection, failureReporter);
		this.chat = ChatWrapper.create(connection, itemId, translator);
	    addAuctionEventListener(chatDisconnectorFor(translator));
	}
	@Override public void bid(Amount bid) { sendMessage(BID.format(bid)); }
	@Override public void join()          { sendMessage(JOIN.format());   }
	@Override public void addAuctionEventListener(AuctionEventListener auctionSniper) {
		announcer.addListener(auctionSniper);
	}
	private void sendMessage(final String message) {
		try { chat.send(message); } 
		catch (XMPPException e) { e.printStackTrace(); }
	}
	private AuctionMessageTranslator translatorFor(//
			XMPPConnection connection, XMPPFailureReporter failureReporter) {
		return new AuctionMessageTranslator(SniperId.fromString(connection.getUser()),
				announcer.announce(), failureReporter);
	}
	private AuctionEventListener chatDisconnectorFor(final AuctionMessageTranslator translator) {
		return new AuctionEventAdapter() {
			public void auctionFailed() {
				chat.removeListener(translator);
			}
		};
	}
}
