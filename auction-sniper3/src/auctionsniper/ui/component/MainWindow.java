package auctionsniper.ui.component;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import auctionsniper.SniperPortfolio;
import auctionsniper.UserRequestListener;
import auctionsniper.ui.AnnouncerToUserRequestListener;
import auctionsniper.ui.SnipersTableModel;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	public static final String APPLICATION_TITLE = "Auction Sniper";
	
	private final AnnouncerToUserRequestListener announcer = new AnnouncerToUserRequestListener();

	//TODO: 置き場所再考
	public static final String MAIN_WINDOW_NAME   = "Auction Sniper Main";
	public MainWindow(SniperPortfolio portfolio) {
		super(APPLICATION_TITLE);

		setName(MainWindow.MAIN_WINDOW_NAME);
		fillContentPane(makeSnipersTable(portfolio), new ControlPanel(announcer));
		pack();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	private void fillContentPane(JTable snipersTable, JPanel controls) {
		final Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		
		contentPane.add(controls, BorderLayout.NORTH);
		contentPane.add(new JScrollPane(snipersTable), BorderLayout.CENTER);
	}
	private JTable makeSnipersTable(SniperPortfolio portfolio) {
		SnipersTableModel model = new SnipersTableModel();
		portfolio.addPortfolioListener(model);

		return new SnipersTable(model);
	}
	public void add(UserRequestListener listener) {
		announcer.add(listener);
 	}
}
