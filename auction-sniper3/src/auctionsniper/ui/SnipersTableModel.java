package auctionsniper.ui;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import auctionsniper.PortfolioListener;
import auctionsniper.SniperListener;
import auctionsniper.snapshot.SniperSnapshot;
import auctionsniper.sniper.AuctionSniper;
import auctionsniper.util.Defect;

@SuppressWarnings("serial")
public class SnipersTableModel extends AbstractTableModel implements
		SniperListener, PortfolioListener {

	//TODO rule 4. First class collections
	private final List<SniperSnapshot> snapshots = new ArrayList<SniperSnapshot>();
	
	@Override public int getColumnCount() { return Column.values().length; }
	@Override public String getColumnName(int column) { return Column.at(column).name; }
	@Override public int getRowCount() { return snapshots.size(); }
	@Override public Object getValueAt(int row, int column) {
		return Column.at(column).valueIn(snapshots.get(row));
	}	

	@Override public void sniperStateChanged(SniperSnapshot newSnapshot) {
		int row = rowMatching(newSnapshot);
		snapshots.set(row, newSnapshot);
		fireTableRowsUpdated(row, row);
	}
	@Override public void sniperAdded(AuctionSniper sniper) {
		addSniperSnapshot(sniper.getSnapshot());
		sniper.addSniperListener(new SwingThreadSniperListener(this));
	}
	private int rowMatching(SniperSnapshot snapshot) {
		for (int i = 0; i < snapshots.size(); i++) {
			if (snapshot.isForSameItemAs(snapshots.get(i))) return i;
		}
		throw new Defect("Cannot find match for " + snapshot);
	}
	public void addSniperSnapshot(SniperSnapshot snapshot) {
		snapshots.add(snapshot);
		int row = snapshots.size() - 1;
		fireTableRowsInserted(row, row);
	}
}
