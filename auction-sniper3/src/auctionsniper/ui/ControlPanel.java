package auctionsniper.ui;

import static test.auctionsniper.util.TestData.newItem;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import auctionsniper.UserRequestListener;
import auctionsniper.util.Announcer;

@SuppressWarnings("serial")
public class ControlPanel extends JPanel {
	private final JButton     joinAuctionButton = new JoinAuctionButton();
	private final InputFields inputFields       = new InputFields();

	ControlPanel(final Announcer<UserRequestListener> userRequests) {
		super(new FlowLayout());

		add(inputFields.itemIdField);
		add(inputFields.stopPriceField);
		
		joinAuctionButton.addActionListener(createListener(userRequests));
		add(joinAuctionButton);
	}

	private ActionListener createListener(
			final Announcer<UserRequestListener> userRequests) {
		return new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				//TODO rule 5. One dot per line
				userRequests.announce().joinAuction(newItem(itemId(), stopPrice()));
			}
			private String itemId() { return inputFields.itemId(); }
			//TODO rule 3. Wrap all primitives and Strings
			private int stopPrice() { return inputFields.stopPrice(); }
		};
	}
}