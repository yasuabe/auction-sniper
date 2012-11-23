package auctionsniper.ui;

import javax.swing.table.AbstractTableModel;

import auctionsniper.SniperSnapshot;
import auctionsniper.SniperState;

@SuppressWarnings("serial")
public class SnipersTableModel extends AbstractTableModel {
	//TODO 臨時null
	private static final SniperSnapshot STARTING_UP = SniperSnapshot.joining("");

    private static String[] STATUS_TEXT = {
        "Joining",
        "Bidding",
        "Winning",
        "Won",
        "Lost"
        };
	private SniperSnapshot snapshot = STARTING_UP;
	
	public int getColumnCount() {
		return Column.values().length;
	}
	public int getRowCount() { return 1; }

	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(Column.at(columnIndex)) {
		case ITEM_IDENTIFIER:
			return snapshot.itemId;
		case LAST_PRICE:
			return snapshot.lastPrice;
		case LAST_BID:
			return snapshot.lastBid;
		case SNIPER_STATE:
			return textFor(snapshot.state);
		default:
			throw new IllegalArgumentException("No column at " + columnIndex);
		}
	}	
	public void sniperStatusChanged(SniperSnapshot newSnapshot) {
		snapshot  = newSnapshot;
		fireTableRowsUpdated(0, 0);
	}
	public static String textFor(SniperState state) {
		return STATUS_TEXT[state.ordinal()];
	}
}
