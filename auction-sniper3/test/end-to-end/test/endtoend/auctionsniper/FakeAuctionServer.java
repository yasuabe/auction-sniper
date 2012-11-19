package test.endtoend.auctionsniper;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class FakeAuctionServer {
	class SingleMessageListener implements MessageListener {
		private final ArrayBlockingQueue<Message> messages = new ArrayBlockingQueue<>(1);

		@Override
		public void processMessage(Chat chat, Message message) {
			messages .add(message);
		}

		public void receivesAMessage() throws InterruptedException {
			assertThat("Message", messages.poll(5, TimeUnit.SECONDS), is(notNullValue()));
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

	public void hasReceivedJoinRequestFromSniper() throws InterruptedException {
		messageListener.receivesAMessage();
	}

	public void announceClosed() throws XMPPException {
		currentChat.sendMessage(new Message());
	}

	public void stop() {
		connection.disconnect();
	}

	public String getItemId() {
		return itemId;
	}

	public void hasReceivedJoinRequestFrom(String sniperXmppId) {
		// TODO Auto-generated method stub
		
	}

	public void reportPrice(int price, int increment, String bidder) throws XMPPException {
		currentChat.sendMessage(String.format(
				"SQLVersion: 1.1; Event: PRICE; CurrentPrice: %d; Increment: %d; Bidder: %s;", 
				price, increment, bidder));
	}

	public void hasReceivedBid(int bid, String sniperId) throws InterruptedException {
		assertThat(currentChat.getParticipant(), equalTo(sniperId));
		messageListener.receivesAMessage();
	}
}
