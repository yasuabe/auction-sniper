package auctionsniper;

import java.util.EventListener;

import auctionsniper.values.Item;

public interface UserRequestListener extends EventListener {
	  void joinAuction(Item item);
}
