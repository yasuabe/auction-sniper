package auctionsniper.ui;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import auctionsniper.PortfolioListener;
import auctionsniper.SniperListener;
import auctionsniper.snapshot.SniperSnapshot;
import auctionsniper.sniper.AuctionSniper;
import auctionsniper.util.Defect;

//TODO rule 7. Keep all entities small
@SuppressWarnings("serial")
public class SnipersTableModel extends AbstractTableModel implements
		SniperListener, PortfolioListener {

	private List<SniperSnapshot> snapshots = new ArrayList<SniperSnapshot>();
	
	@Override public int getColumnCount() { return Column.values().length; }
	@Override public String getColumnName(int column) { return Column.at(column).name; }
	@Override public void sniperStateChanged(SniperSnapshot newSnapshot) {
		int row = rowMatching(newSnapshot);
		snapshots.set(row, newSnapshot);
		fireTableRowsUpdated(row, row);
	}
	@Override public void sniperAdded(AuctionSniper sniper) {
		addSniperSnapshot(sniper.getSnapshot());
		sniper.addSniperListener(new SwingThreadSniperListener(this));
	}
	@Override public int getRowCount() { return snapshots.size(); }

	@Override public Object getValueAt(int rowIndex, int columnIndex) {
		return Column.at(columnIndex).valueIn(snapshots.get(rowIndex));
	}	
	private int rowMatching(SniperSnapshot snapshot) {
		for (int i = 0; i < snapshots.size(); i++) {
			if (snapshot.isForSameItemAs(snapshots.get(i))) return i;
		}
		throw new Defect("Cannot find match for " + snapshot);
	}
	public static String textFor(SniperSnapshot snapshot) {
		return StatusTexts.textFor(snapshot.getClass());
	}
	public void addSniperSnapshot(SniperSnapshot joining) {
		snapshots.add(joining);
		int row = snapshots.size() - 1;
		fireTableRowsInserted(row, row);
	}
}
