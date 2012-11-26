package auctionsniper.ui;

import java.util.ArrayList;
import java.util.List;

import auctionsniper.snapshot.SniperSnapshot;
import auctionsniper.util.Defect;

class SniperSnapshots {
	private final List<SniperSnapshot> snapshots = new ArrayList<SniperSnapshot>();

	public int add(SniperSnapshot snapshot) {
		snapshots.add(snapshot);
		return snapshots.size() - 1;
	}
	private int rowMatching(SniperSnapshot snapshot) {
		for (int i = 0; i < snapshots.size(); i++) 
			if (snapshot.isForSameItemAs(snapshots.get(i))) return i;
		throw new Defect("Cannot find match for " + snapshot);
	}
	public int set(SniperSnapshot newSnapshot) {
		int row = rowMatching(newSnapshot);
		snapshots.set(row, newSnapshot);
		return row;
	}
	public int size() { return snapshots.size(); }
	public Object get(int row, Column column) { 
		return column.valueIn(snapshots.get(row));
	}
}