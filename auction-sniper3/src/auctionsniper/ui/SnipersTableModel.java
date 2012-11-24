package auctionsniper.ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import auctionsniper.AuctionSniper;
import auctionsniper.Defect;
import auctionsniper.PortfolioListener;
import auctionsniper.SniperListener;
import auctionsniper.SniperSnapshot;
import auctionsniper.SniperState;

@SuppressWarnings("serial")
public class SnipersTableModel extends AbstractTableModel implements
		SniperListener, PortfolioListener {
    private static String[] STATUS_TEXT = {
        "Joining",
        "Bidding",
        "Winning",
        "Losing",
        "Won",
        "Lost",
        "Failed"
        };
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
		int row = rowMatching(newSnapshot);
		snapshots.set(row, newSnapshot);
		fireTableRowsUpdated(row, row);
	}
	private int rowMatching(SniperSnapshot snapshot) {
		for (int i = 0; i < snapshots.size(); i++) {
			if (snapshot.isForSameItemAs(snapshots.get(i))) {
				return i;
			}
		}
		throw new Defect("Cannot find match for " + snapshot);
	}
	public static String textFor(SniperState state) {
		return STATUS_TEXT[state.ordinal()];
	}
	public void addSniperSnapshot(SniperSnapshot joining) {
		snapshots.add(joining);
		int row = snapshots.size() - 1;
		fireTableRowsInserted(row, row);
	}
	@Override
	public void sniperAdded(AuctionSniper sniper) {
		addSniperSnapshot(sniper.getSnapshot());
		sniper.addSniperListener(new SwingThreadSniperListener(this));
	}
}
