package auctionsniper.ui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import auctionsniper.values.Item;
import auctionsniper.values.ItemId;
import auctionsniper.values.Price;


@SuppressWarnings("serial")
public class ControlPanel extends JPanel {
	private final JButton     joinAuctionButton = new JoinAuctionButton();
	private final InputFields inputFields       = new InputFields();

	ControlPanel(final AnnouncerToUserRequestListener userRequests) {
		super(new FlowLayout());

		add(inputFields.itemIdField);
		add(inputFields.stopPriceField);
		
		joinAuctionButton.addActionListener(createListener(userRequests));
		add(joinAuctionButton);
	}

	private ActionListener createListener(
			final AnnouncerToUserRequestListener userRequests) {
		return new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				userRequests.joinAuction(new Item(itemId(), stopPrice()));
			}
			private ItemId itemId() { return inputFields.itemId(); }
			private Price stopPrice() { return inputFields.stopPrice(); }
		};
	}
}