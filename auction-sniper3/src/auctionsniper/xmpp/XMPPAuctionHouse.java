package auctionsniper.xmpp;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import auctionsniper.Auction;
import auctionsniper.AuctionHouse;
import auctionsniper.Item;

public class XMPPAuctionHouse implements AuctionHouse {

	public static final String AUCTION_RESOURCE = "Auction";
	private XMPPConnection connection;

	@Override
	public Auction auctionFor(Item item) {
		//TODO XMPPAuction でItem を浮けとるようにする
	    return new XMPPAuction(connection, item.identifier);
	}

	public XMPPAuctionHouse(XMPPConnection connection) {
		this.connection = connection;
	}

	public static XMPPAuctionHouse connect(//
			String hostname, String username, String password) //
			throws XMPPException {

		XMPPConnection connection = new XMPPConnection(hostname);
		connection.connect();
		connection.login(username, password, AUCTION_RESOURCE);

		return new XMPPAuctionHouse(connection);
	}

	public void disconnect() {
		connection.disconnect();		
	}
}
