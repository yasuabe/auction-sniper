package auctionsniper.ui;

import static test.auctionsniper.util.TestData.newItem;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JTextField;

import auctionsniper.UserRequestListener;
import auctionsniper.util.Announcer;

@SuppressWarnings("serial")
public class ControlPanel extends JPanel {
	public static final String NEW_ITEM_ID_NAME    = "item id";
	public static final String JOIN_BUTTON_NAME    = "join button";
	public static final String NEW_ITEM_STOP_PRICE_NAME = "stop price";

	private final Announcer<UserRequestListener> userRequests;

	final JTextField          itemIdField    = itemIdField();
	final JFormattedTextField stopPriceField = stopPriceField();
	JButton joinAuctionButton = new JButton("Join Auction");
	
	ControlPanel(Announcer<UserRequestListener> userRequests) {
		super(new FlowLayout());
		this.userRequests = userRequests;
		add(itemIdField);
		add(stopPriceField);
		joinAuctionButton.setName(JOIN_BUTTON_NAME);
		joinAuctionButton.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				ControlPanel.this.userRequests.announce().joinAuction(newItem(itemId(), stopPrice()));
			}
			private String itemId() {
				return itemIdField.getText();
			}
			private int stopPrice() {
				return ((Number) stopPriceField.getValue()).intValue();
			}
		});
		add(joinAuctionButton);
	}
	private JTextField itemIdField() {
		JTextField itemIdField = new JTextField();
		itemIdField.setColumns(10);
		itemIdField.setName(NEW_ITEM_ID_NAME);
		return itemIdField;
	}
	private JFormattedTextField stopPriceField() {
		JFormattedTextField stopPriceField = new JFormattedTextField(
				NumberFormat.getIntegerInstance());
		stopPriceField.setColumns(7);
		stopPriceField.setName(NEW_ITEM_STOP_PRICE_NAME);
		return stopPriceField;
	}
}