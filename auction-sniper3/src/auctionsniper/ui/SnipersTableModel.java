package auctionsniper.ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import auctionsniper.SniperListener;
import auctionsniper.SniperSnapshot;
import auctionsniper.SniperState;

@SuppressWarnings("serial")
public class SnipersTableModel extends AbstractTableModel implements SniperListener {
	//TODO 既存テストを通すために itemId をべた書きした
	private static final SniperSnapshot STARTING_UP = SniperSnapshot.joining("item-54321");
	 
    private static String[] STATUS_TEXT = {
        "Joining",
        "Bidding",
        "Winning",
        "Won",
        "Lost"
        };
	private SniperSnapshot snapshot = STARTING_UP;
	private List<SniperSnapshot> snapshots = new ArrayList<SniperSnapshot>();
	
	public int getColumnCount() {
		return Column.values().length;
	}
	@Override
	public String getColumnName(int column) {
		return Column.at(column).name;
	}
	public int getRowCount() { return snapshots.size(); }

	public Object getValueAt(int rowIndex, int columnIndex) {
		return Column.at(columnIndex).valueIn(snapshots.get(rowIndex));
	}	
	@Override
	public void sniperStateChanged(SniperSnapshot newSnapshot) {
		snapshots.add(newSnapshot);
		snapshot  = newSnapshot;
		fireTableRowsUpdated(0, 0);
	}
	public static String textFor(SniperState state) {
		return STATUS_TEXT[state.ordinal()];
	}
	public void addSniper(SniperSnapshot joining) {
		snapshots.add(joining);
	}
}
