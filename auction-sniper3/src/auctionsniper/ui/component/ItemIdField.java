package auctionsniper.ui.component;

import javax.swing.JTextField;

@SuppressWarnings("serial")
public class ItemIdField extends JTextField {

	public static final String NEW_ITEM_ID_NAME         = "item id";
	
	public ItemIdField() {
		setColumns(10);
		setName(NEW_ITEM_ID_NAME);
	}
}
