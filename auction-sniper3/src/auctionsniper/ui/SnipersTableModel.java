package auctionsniper.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import auctionsniper.PortfolioListener;
import auctionsniper.SniperListener;
import auctionsniper.snapshot.BiddingSnapshot;
import auctionsniper.snapshot.FailedSnapshot;
import auctionsniper.snapshot.JoiningSnapshot;
import auctionsniper.snapshot.LosingSnapshot;
import auctionsniper.snapshot.LostSnapshot;
import auctionsniper.snapshot.SniperSnapshot;
import auctionsniper.snapshot.WinningSnapshot;
import auctionsniper.snapshot.WonSnapshot;
import auctionsniper.sniper.AuctionSniper;
import auctionsniper.util.Defect;

@SuppressWarnings("serial")
public class SnipersTableModel extends AbstractTableModel implements
		SniperListener, PortfolioListener {

	private static Map<Class<? extends SniperSnapshot>, String> STATUS_TEXT =
    		new HashMap<Class<? extends SniperSnapshot>, String>();
    {
    	STATUS_TEXT.put(JoiningSnapshot.class, "Joining");
    	STATUS_TEXT.put(BiddingSnapshot.class, "Bidding");
    	STATUS_TEXT.put(WinningSnapshot.class, "Winning");
    	STATUS_TEXT.put(LosingSnapshot.class,  "Losing");
    	STATUS_TEXT.put(WonSnapshot.class,     "Won");
    	STATUS_TEXT.put(LostSnapshot.class,    "Lost");
    	STATUS_TEXT.put(FailedSnapshot.class,  "Failed");
    }
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
		//TODO rule 1. One level of indentation per method
		for (int i = 0; i < snapshots.size(); i++) {
			if (snapshot.isForSameItemAs(snapshots.get(i))) {
				return i;
			}
		}
		throw new Defect("Cannot find match for " + snapshot);
	}
	public static String textFor(SniperSnapshot snapshot) {
		return textFor(snapshot.getClass());
	}
	public static String textFor(Class<? extends SniperSnapshot> clazz) {
		return STATUS_TEXT.get(clazz);
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
