package test.endtoend.auctionsniper;

import static com.objogate.wl.swing.matcher.IterableComponentsMatcher.matching;
import static com.objogate.wl.swing.matcher.JLabelTextMatcher.withLabelText;
import static java.lang.String.valueOf;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.table.JTableHeader;

import auctionsniper.ui.component.ItemIdField;
import auctionsniper.ui.component.JoinAuctionButton;
import auctionsniper.ui.component.MainWindow;
import auctionsniper.ui.component.StopPriceField;

import com.objogate.wl.swing.AWTEventQueueProber;
import com.objogate.wl.swing.driver.JButtonDriver;
import com.objogate.wl.swing.driver.JFrameDriver;
import com.objogate.wl.swing.driver.JTableDriver;
import com.objogate.wl.swing.driver.JTableHeaderDriver;
import com.objogate.wl.swing.driver.JTextFieldDriver;
import com.objogate.wl.swing.gesture.GesturePerformer;

@SuppressWarnings("unchecked")
public class AuctionSniperDriver extends JFrameDriver {

	public AuctionSniperDriver(int timeoutMillis) {
		super(new GesturePerformer(),
				JFrameDriver.topLevelFrame(
						named(MainWindow.MAIN_WINDOW_NAME),
						showingOnScreen()),
						new AWTEventQueueProber(timeoutMillis, 100));
	}
	public void showsSniperStatus(String itemId, int lastPrice, int lastBid,
			String statusText) {

		JTableDriver table = new JTableDriver(this);
		table.hasRow(matching(withLabelText(itemId), withLabelText(valueOf(lastPrice)),
				withLabelText(valueOf(lastBid)), withLabelText(statusText)));
	}
	public void hasColumnTitles() {
		JTableHeaderDriver headers = new JTableHeaderDriver(
				this, JTableHeader.class);

		headers.hasHeaders(matching(
				withLabelText("Item"),
				withLabelText("Last Price"),
				withLabelText("Last Bid"),
				withLabelText("State")));
	}
	public void startBiddingFor(String itemId) {
		itemIdField().replaceAllText(itemId);
		bidButton().click();
	}
	private JButtonDriver bidButton() {
		return new JButtonDriver(this, JButton.class, named(JoinAuctionButton.JOIN_BUTTON_NAME));
	}

	private JTextFieldDriver itemIdField() {
		JTextFieldDriver newItemId = new JTextFieldDriver(this,
				JTextField.class, named(ItemIdField.NEW_ITEM_ID_NAME));
		newItemId.focusWithMouse();
		newItemId.focusWithMouse();
		return newItemId;
	}
	public void startBiddingWithStopPrice(String itemId, int stopPrice) {
	    textField(ItemIdField.NEW_ITEM_ID_NAME).replaceAllText(itemId); 
	    textField(StopPriceField.NEW_ITEM_STOP_PRICE_NAME).replaceAllText(String.valueOf(stopPrice)); 
	    bidButton().click(); 
	}
	private JTextFieldDriver textField(String fieldName) {
		JTextFieldDriver newItemId = new JTextFieldDriver(this,
				JTextField.class, named(fieldName));
		newItemId.focusWithMouse();
		newItemId.focusWithMouse();
		return newItemId;
	}
}
