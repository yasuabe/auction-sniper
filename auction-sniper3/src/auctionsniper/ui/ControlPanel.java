package auctionsniper.ui;

import static test.auctionsniper.util.TestData.newItem;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;


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
				userRequests.joinAuction(newItem(itemId(), stopPrice()));
			}
			private String itemId() { return inputFields.itemId(); }
			//TODO rule 3. Wrap all primitives and Strings
			private int stopPrice() { return inputFields.stopPrice(); }
		};
	}
}