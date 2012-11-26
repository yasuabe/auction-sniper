package auctionsniper.ui;

import auctionsniper.UserRequestListener;
import auctionsniper.util.Announcer;
import auctionsniper.values.Item;

public class AnnouncerToUserRequestListener {
	private final Announcer<UserRequestListener> userRequests;
	AnnouncerToUserRequestListener() {			
		userRequests = new Announcer<>(UserRequestListener.class);
	}
	public void add(UserRequestListener listener) {
		userRequests.addListener(listener);
	}
	public void joinAuction(Item newItem) {
		userRequests.announce().joinAuction(newItem);
	}
}