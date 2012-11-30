package auctionsniper.ui.component;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class SnipersTable extends JTable {
	private static final String SNIPERS_TABLE_NAME = "Snipers Table";
	SnipersTable(AbstractTableModel model) {
		super(model);
		setName(SNIPERS_TABLE_NAME);
	}
}