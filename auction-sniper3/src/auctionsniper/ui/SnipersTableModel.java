package auctionsniper.ui;

import javax.swing.table.AbstractTableModel;

import auctionsniper.SniperState;

@SuppressWarnings("serial")
public class SnipersTableModel extends AbstractTableModel {
	private String statusText = MainWindow.STATUS_JOINING;
	
	public int getColumnCount() {
		return Column.values().length;
	}
	public int getRowCount() { return 1; }
	public Object getValueAt(int rowIndex, int columnIndex) { return statusText; }
	
	public void setStatusText(String newStatusText) {
		statusText = newStatusText;
		fireTableRowsUpdated(0, 0);
	}
	public void sniperStatusChanged(SniperState state, String statusText2) {
		// TODO Auto-generated method stub
		
	}
}
