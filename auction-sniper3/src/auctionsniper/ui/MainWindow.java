package auctionsniper.ui;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import auctionsniper.Main;
import auctionsniper.SniperSnapshot;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	private static final String SNIPERS_TABLE_NAME = "Snipers Table";
	
	public static final String STATUS_BIDDING = "Bidding";
	public static final String STATUS_JOINING = "Joining";
	public static final String STATUS_WINNING = "Winning";
	public static final String STATUS_WON = "Won";
	public static final String STATUS_LOST = "Lost";

	public static final String APPLICATION_TITLE = "Auction Sniper";
	
	private final SnipersTableModel snipers = new SnipersTableModel();
	
	public MainWindow() {
		super(APPLICATION_TITLE);
		setName(Main.MAIN_WINDOW_NAME);
		fillContentPane(makeSnipersTable());
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	public void showStatus(String status) {
		snipers.setStatusText(status);
	}
	private void fillContentPane(JTable snipersTable) {
		final Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		
		contentPane.add(new JScrollPane(snipersTable), BorderLayout.CENTER);
	}
	private JTable makeSnipersTable() {
		final JTable snipersTable = new JTable(snipers);
		snipersTable.setName(SNIPERS_TABLE_NAME);
		return snipersTable;
	}
	public void showStatusChanged(SniperSnapshot sniperState, String statusText) {
		snipers.sniperStatusChanged(sniperState, statusText);		
	}
}
