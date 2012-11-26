package auctionsniper.ui;

import auctionsniper.UserRequestListener;
import auctionsniper.util.Announcer;
import auctionsniper.values.Item;

public class AnnouncerToUserRequestListener {
	
	private final Announcer<UserRequestListener> announcer;

	AnnouncerToUserRequestListener() {			
		announcer = new Announcer<>(UserRequestListener.class);
	}
	public void add(UserRequestListener listener) {
		announcer.addListener(listener);
	}
	public void announceJoinAuction(Item newItem) {
		announcer.announce().joinAuction(newItem);
	}
}