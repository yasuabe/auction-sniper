package auctionsniper.ui;

import java.util.HashMap;
import java.util.Map;

import auctionsniper.snapshot.BiddingSnapshot;
import auctionsniper.snapshot.FailedSnapshot;
import auctionsniper.snapshot.JoiningSnapshot;
import auctionsniper.snapshot.LosingSnapshot;
import auctionsniper.snapshot.LostSnapshot;
import auctionsniper.snapshot.SniperSnapshot;
import auctionsniper.snapshot.WinningSnapshot;
import auctionsniper.snapshot.WonSnapshot;

public class StatusTexts {
	private static Map<Class<? extends SniperSnapshot>, String> STATUS_TEXT =
    		new HashMap<Class<? extends SniperSnapshot>, String>();
    static {
    	STATUS_TEXT.put(JoiningSnapshot.class, "Joining");
    	STATUS_TEXT.put(BiddingSnapshot.class, "Bidding");
    	STATUS_TEXT.put(WinningSnapshot.class, "Winning");
    	STATUS_TEXT.put(LosingSnapshot.class,  "Losing");
    	STATUS_TEXT.put(WonSnapshot.class,     "Won");
    	STATUS_TEXT.put(LostSnapshot.class,    "Lost");
    	STATUS_TEXT.put(FailedSnapshot.class,  "Failed");
    }
	public static String textFor(Class<? extends SniperSnapshot> clazz) {
		return STATUS_TEXT.get(clazz);
	}
	public static String textFor(SniperSnapshot snapshot) {
		return textFor(snapshot.getClass());
	}
}