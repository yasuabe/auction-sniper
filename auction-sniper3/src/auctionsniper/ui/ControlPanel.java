package auctionsniper.ui;

import static test.auctionsniper.util.TestData.newItem;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import auctionsniper.UserRequestListener;
import auctionsniper.util.Announcer;

//TODO rule 7. Keep all entities small
@SuppressWarnings("serial")
public class ControlPanel extends JPanel {
	public static final String NEW_ITEM_ID_NAME    = "item id";
	public static final String JOIN_BUTTON_NAME    = "join button";
	public static final String NEW_ITEM_STOP_PRICE_NAME = "stop price";

	private final JButton joinAuctionButton = new JButton("Join Auction");
	private final InputFields inputFields = new InputFields();
	//TODO rule 7. Keep all entities small
	ControlPanel(final Announcer<UserRequestListener> userRequests) {
		super(new FlowLayout());
		add(inputFields.itemIdField);
		add(inputFields.stopPriceField);
		joinAuctionButton.setName(JOIN_BUTTON_NAME);
		joinAuctionButton.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				userRequests.announce().joinAuction(newItem(itemId(), stopPrice()));
			}
			private String itemId() {
				return inputFields.itemIdField.getText();
			}
			private int stopPrice() {
				return ((Number) inputFields.stopPriceField.getValue()).intValue();
			}
		});
		add(joinAuctionButton);
	}
}