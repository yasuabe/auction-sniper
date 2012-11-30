package auctionsniper.ui.component;

import javax.swing.JFormattedTextField;
import javax.swing.JTextField;

import auctionsniper.values.ItemId;
import auctionsniper.values.Amount;

public class InputFields {

	final JTextField          itemIdField    = new ItemIdField();
	final JFormattedTextField stopPriceField = new StopPriceField();

	public ItemId itemId() {
		return ItemId.fromString(itemIdField.getText());
	}
	public Amount stopPrice() {
		return Amount.fromInt(((Number) stopPriceField.getValue()).intValue());
	}
}