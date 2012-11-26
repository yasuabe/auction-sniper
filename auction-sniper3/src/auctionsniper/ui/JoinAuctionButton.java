package auctionsniper.ui;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class JoinAuctionButton extends JButton {
	public static final String JOIN_BUTTON_NAME    = "join button";
	public JoinAuctionButton() {
		super("Join Auction");
		setName(JOIN_BUTTON_NAME);
	}
}