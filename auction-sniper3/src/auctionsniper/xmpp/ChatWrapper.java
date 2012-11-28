package auctionsniper.xmpp;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import auctionsniper.values.ItemId;
import auctionsniper.xmpp.translator.AuctionMessageTranslator;

public class ChatWrapper {
	private final Chat chat;
	ChatWrapper(Chat chat) { this.chat = chat; }
	static ChatWrapper create(XMPPConnection connection, ItemId itemId,
			MessageListener listener) {
		
		AuctionId          auctionId   = AuctionId.from(itemId, connection.getServiceName());
		ChatManager manager = connection.getChatManager();

		return new ChatWrapper(manager.createChat(auctionId.toString(), listener));
	}
	void send(String message) throws XMPPException { chat.sendMessage(message); }
	public void removeListener(AuctionMessageTranslator translator) {
		chat.removeMessageListener(translator);
	}
}