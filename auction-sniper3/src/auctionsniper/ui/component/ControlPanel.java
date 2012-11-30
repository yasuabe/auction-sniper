package auctionsniper.ui.component;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import auctionsniper.ui.AnnouncerToUserRequestListener;
import auctionsniper.values.Item;
import auctionsniper.values.ItemId;
import auctionsniper.values.Amount;


@SuppressWarnings("serial")
public class ControlPanel extends JPanel {
	private final JButton     joinAuctionButton = new JoinAuctionButton();
	private final InputFields inputFields       = new InputFields();

	ControlPanel(final AnnouncerToUserRequestListener announcer) {
		super(new FlowLayout());

		add(inputFields.itemIdField);
		add(inputFields.stopPriceField);
		
		joinAuctionButton.addActionListener(createListener(announcer));
		add(joinAuctionButton);
	}

	private ActionListener createListener(
			final AnnouncerToUserRequestListener announcer) {
		return new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				announcer.announceJoinAuction(new Item(itemId(), stopPrice()));
			}
			private ItemId itemId() {   return inputFields.itemId(); }
			private Amount stopPrice() { return inputFields.stopPrice(); }
		};
	}
}