package auctionsniper.ui;

import javax.swing.table.AbstractTableModel;

import auctionsniper.SniperSnapshot;

@SuppressWarnings("serial")
public class SnipersTableModel extends AbstractTableModel {
	//TODO 臨時null
	private static final SniperSnapshot STARTING_UP = new SniperSnapshot("", 0, 0, null);
	private static String[] STATUS_TEXT = {
		MainWindow.STATUS_JOINING,
		MainWindow.STATUS_BIDDING
	};

	private String      statusText  = MainWindow.STATUS_JOINING;
	private SniperSnapshot sniperState = STARTING_UP;
	
	public int getColumnCount() {
		return Column.values().length;
	}
	public int getRowCount() { return 1; }

	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(Column.at(columnIndex)) {
		case ITEM_IDENTIFIER:
			return sniperState.itemId;
		case LAST_PRICE:
			return sniperState.lastPrice;
		case LAST_BID:
			return sniperState.lastBid;
		case SNIPER_STATE:
			return statusText;
		default:
			throw new IllegalArgumentException("No column at " + columnIndex);
		}
	}	
	
	public void setStatusText(String newStatusText) {
		statusText = newStatusText;
		fireTableRowsUpdated(0, 0);
	}
	public void sniperStatusChanged(SniperSnapshot newSnapshot) {
		sniperState  = newSnapshot;
		statusText   = STATUS_TEXT[newSnapshot.state.ordinal()];
		fireTableRowsUpdated(0, 0);
	}
}
