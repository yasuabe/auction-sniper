package auctionsniper.xmpp;

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.apache.commons.io.FilenameUtils;

public class LoggingXMPPFailureReporterFactory {
	
	public  static final String LOG_FILE_NAME = "auction-sniper.log";//TODO 置き場所再考
	private static final String LOGGER_NAME   = "auction-sniper";

	public static XMPPFailureReporter create() throws XMPPAuctionException {
		return new LoggingXMPPFailureReporter(makeLogger());
	}
	private static Logger makeLogger() throws SecurityException, XMPPAuctionException  {
		Logger logger = Logger.getLogger(LOGGER_NAME);
		
		logger.setUseParentHandlers(false);
		logger.addHandler(simpleFileHandler());

		return logger;
	}
	//TODO 長すぎるメソッド
	private static FileHandler simpleFileHandler() throws XMPPAuctionException {
		try {
			FileHandler handler = new FileHandler(LOG_FILE_NAME);
			handler.setFormatter(new SimpleFormatter());

			return handler;
		} catch (Exception e) {
			throw new XMPPAuctionException (
					"Could not create logger FileHandler "
							+ FilenameUtils.getFullPath(LOG_FILE_NAME), e);
		}
	}
}