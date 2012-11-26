package auctionsniper.ui;

import java.text.NumberFormat;

import javax.swing.JFormattedTextField;
import javax.swing.JTextField;

public class InputFields {
	public static final String NEW_ITEM_ID_NAME         = "item id";
	public static final String NEW_ITEM_STOP_PRICE_NAME = "stop price";

	final JTextField          itemIdField    = itemIdField();
	final JFormattedTextField stopPriceField = stopPriceField();

	private static JTextField itemIdField() {
		JTextField itemIdField = new JTextField();
		
		itemIdField.setColumns(10);
		itemIdField.setName(NEW_ITEM_ID_NAME);

		return itemIdField;
	}
	private static JFormattedTextField stopPriceField() {
		JFormattedTextField stopPriceField = new JFormattedTextField(
				NumberFormat.getIntegerInstance());
		
		stopPriceField.setColumns(7);
		stopPriceField.setName(NEW_ITEM_STOP_PRICE_NAME);
		
		return stopPriceField;
	}
	public String itemId() {
		return itemIdField.getText();
	}
	public int stopPrice() {
		return ((Number) stopPriceField.getValue()).intValue();
	}
}