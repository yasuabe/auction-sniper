package test.endtoend.auctionsniper;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.hamcrest.Matcher;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import auctionsniper.Main;

import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.hasProperty;

public class FakeAuctionServer {
	class SingleMessageListener implements MessageListener {
		private final ArrayBlockingQueue<Message> messages = new ArrayBlockingQueue<>(1);

		@Override
		public void processMessage(Chat chat, Message message) {
			messages .add(message);
		}

		public void receivesAMessage(Matcher<? super String> messageMatcher) 
				throws InterruptedException {

			final Message message = messages.poll(5, TimeUnit.SECONDS);
			assertThat(message, hasProperty("body", messageMatcher));
		}
	}
	public static final String XMPP_HOSTNAME = "localhost";
	public static final String ITEM_ID_AS_LOGIN = "auction-%s";
	public static final String AUCTION_PASSWORD = "auction";
	public static final String AUCTION_RESOURCE = "Auction";

	private final String itemId;
	private final XMPPConnection connection;
	private final SingleMessageListener messageListener = new SingleMessageListener();

	private Chat currentChat;

	public FakeAuctionServer(String itemId) {
		this.itemId = itemId;
		this.connection = new XMPPConnection(XMPP_HOSTNAME);
	}

	public void startSellingItem() throws XMPPException {
		connection.connect();
		connection.login(String.format(ITEM_ID_AS_LOGIN, itemId),
				AUCTION_PASSWORD, AUCTION_RESOURCE);
		connection.getChatManager().addChatListener(new ChatManagerListener() {
			@Override
			public void chatCreated(Chat chat, boolean createdLocally) {
				currentChat = chat;
				chat.addMessageListener(messageListener);
			}
		});
	}

	public void announceClosed() throws XMPPException {
		currentChat.sendMessage("SOLVersion: 1.1; Event: CLOSE;");
	}

	public void stop() {
		connection.disconnect();
	}

	public String getItemId() {
		return itemId;
	}

	public void hasReceivedJoinRequestFrom(String sniperId) throws InterruptedException {
		receivesAMessageMatching(sniperId, equalTo(Main.JOIN_COMMAND_FORMAT));
	}

	private void receivesAMessageMatching(String sniperId,
			Matcher<? super String> messageMatcher) throws InterruptedException {
		messageListener.receivesAMessage(messageMatcher);
		assertThat(currentChat.getParticipant(), equalTo(sniperId));
	}

	public void reportPrice(int price, int increment, String bidder) throws XMPPException {
		currentChat.sendMessage(String.format(
				"SQLVersion: 1.1; Event: PRICE; CurrentPrice: %d; Increment: %d; Bidder: %s;", 
				price, increment, bidder));
	}

	public void hasReceivedBid(int bid, String sniperId) throws InterruptedException {
		receivesAMessageMatching(sniperId, 
				equalTo(format(Main.BID_COMMAND_FORMAT, bid)));
	}
}
