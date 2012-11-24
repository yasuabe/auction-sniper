package auctionsniper.xmpp;

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.apache.commons.io.FilenameUtils;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import auctionsniper.Auction;
import auctionsniper.AuctionHouse;
import auctionsniper.Item;

public class XMPPAuctionHouse implements AuctionHouse {

	public static final String AUCTION_RESOURCE = "Auction";
	public static final String LOG_FILE_NAME = "auction-sniper.log";
	private static final String LOGGER_NAME = "auction-sniper";
	private XMPPConnection connection;
	private final XMPPFailureReporter failureReporter;
	
	@Override
	public Auction auctionFor(Item item) {
	    return new XMPPAuction(connection, item.identifier, failureReporter);
	}

	public XMPPAuctionHouse(XMPPConnection connection) {
		this.connection      = connection;
		this.failureReporter = new LoggingXMPPFailureReporter(makeLogger());
	}

	public static XMPPAuctionHouse connect(//
			String hostname, String username, String password) //
			throws XMPPAuctionException {

		XMPPConnection connection = new XMPPConnection(hostname);
		try {
			connection.connect();
			connection.login(username, password, AUCTION_RESOURCE);

			return new XMPPAuctionHouse(connection);

		} catch (XMPPException xmppe) {
			throw new XMPPAuctionException("Could not connect to auction: "
					+ connection, xmppe);
		}
	}

	public void disconnect() {
		connection.disconnect();		
	}
	private Logger makeLogger()  {
		Logger logger = Logger.getLogger(LOGGER_NAME);
		logger.setUseParentHandlers(false);
		logger.addHandler(simpleFileHandler());
		return logger;
	}
	private FileHandler simpleFileHandler() {
		try {
			FileHandler handler = new FileHandler(LOG_FILE_NAME);
			handler.setFormatter(new SimpleFormatter());

			return handler;
		} catch (Exception e) {
			//TODO 後で XMPPAuctionException を作る
			throw new RuntimeException(
					"Could not create logger FileHandler "
							+ FilenameUtils.getFullPath(LOG_FILE_NAME), e);
		}
	}
}
