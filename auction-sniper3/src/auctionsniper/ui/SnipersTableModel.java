package auctionsniper.ui;

import javax.swing.table.AbstractTableModel;
import auctionsniper.PortfolioListener;
import auctionsniper.SniperListener;
import auctionsniper.snapshot.SniperSnapshot;
import auctionsniper.sniper.AuctionSniper;

@SuppressWarnings("serial")
public class SnipersTableModel extends AbstractTableModel implements
		SniperListener, PortfolioListener {

	SniperSnapshots snapshots = new SniperSnapshots();
	
	@Override public int    getColumnCount()          { return Column.values().length; }
	@Override public String getColumnName(int column) { return Column.at(column).name; }

	@Override public int    getRowCount() { return snapshots.size(); }
	@Override public Object getValueAt(int row, int column) {
		return snapshots.get(row, Column.at(column));
	}	

	@Override public void sniperStateChanged(SniperSnapshot newSnapshot) {
		int row = snapshots.set(newSnapshot);
		fireTableRowsUpdated(row, row);
	}
	@Override public void sniperAdded(AuctionSniper sniper) {
		sniper.register(this);
		sniper.addSniperListener(new SwingThreadSniperListener(this));
	}
	public void addSniperSnapshot(SniperSnapshot snapshot) {
		int row = snapshots.add(snapshot);
		fireTableRowsInserted(row, row);
	}
}
