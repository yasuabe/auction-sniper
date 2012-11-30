package auctionsniper.ui.component;

import java.text.NumberFormat;

import javax.swing.JFormattedTextField;

@SuppressWarnings("serial")
public class StopPriceField extends JFormattedTextField {

	public static final String NEW_ITEM_STOP_PRICE_NAME = "stop price";
	
	public StopPriceField() {
		super(NumberFormat.getIntegerInstance());
		setColumns(7);
		setName(NEW_ITEM_STOP_PRICE_NAME);
	}

}
