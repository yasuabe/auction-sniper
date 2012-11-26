package auctionsniper.xmpp;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import auctionsniper.Auction;
import auctionsniper.AuctionHouse;
import auctionsniper.ConnectionInfo;
import auctionsniper.values.Item;

public class XMPPAuctionHouse implements AuctionHouse {

	public static final String AUCTION_RESOURCE = "Auction";

	private final XMPPConnection      connection;
	private final XMPPFailureReporter failureReporter;
	
	@Override
	public Auction auctionFor(Item item) {
	    return new XMPPAuction(connection, item.identifier, failureReporter);
	}
	public XMPPAuctionHouse(XMPPConnection connection) throws XMPPAuctionException {
		this.connection      = connection;
		this.failureReporter = LoggingXMPPFailureReporterFactory.create();
	}
	public static XMPPAuctionHouse connect(ConnectionInfo info) throws XMPPAuctionException {
		XMPPConnection connection = new XMPPConnection(info.hostname());
		try { return create(connection, info); } 
		catch (XMPPException xmppe) { 
			throw new XMPPAuctionException("Could not connect to auction: " + connection, xmppe);
		}
	}
	public void disconnect() { connection.disconnect(); }

	private static XMPPAuctionHouse create(XMPPConnection connection,
			ConnectionInfo info) throws XMPPException, XMPPAuctionException {
		connection.connect();
		connection.login(info.username(), info.password(), AUCTION_RESOURCE);

		return new XMPPAuctionHouse(connection);
	}
}
