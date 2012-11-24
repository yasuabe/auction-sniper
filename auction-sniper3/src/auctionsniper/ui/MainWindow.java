package auctionsniper.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import auctionsniper.Main;
import auctionsniper.SniperPortfolio;
import auctionsniper.UserRequestListener;
import auctionsniper.util.Announcer;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	private static final String SNIPERS_TABLE_NAME = "Snipers Table";
	
	public static final String APPLICATION_TITLE = "Auction Sniper";
	public static final String NEW_ITEM_ID_NAME = "item id";
	public static final String JOIN_BUTTON_NAME = "join button";
	public static final String NEW_ITEM_STOP_PRICE_NAME = "stop price";
	
	private final Announcer<UserRequestListener> userRequests = Announcer
			.to(UserRequestListener.class);
	
	public MainWindow(SniperPortfolio portfolio) {
		super(APPLICATION_TITLE);

		setName(Main.MAIN_WINDOW_NAME);
		fillContentPane(makeSnipersTable(portfolio), makeControls());
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
	private JPanel makeControls() {
		JPanel controls = new JPanel(new FlowLayout());
		final JTextField itemIdField = new JTextField();
		
		itemIdField.setColumns(25);
		itemIdField.setName(NEW_ITEM_ID_NAME);
		controls.add(itemIdField);
		
		JButton joinAuctionButton = new JButton("Join Auction");
		joinAuctionButton.setName(JOIN_BUTTON_NAME);
		joinAuctionButton.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				userRequests.announce().joinAuction(itemIdField.getText());
			}
		});
		controls.add(joinAuctionButton);
		
		return controls;
	}
	private JTable makeSnipersTable(SniperPortfolio portfolio) {
		SnipersTableModel model = new SnipersTableModel();
		portfolio.addPortfolioListener(model);
		
		final JTable snipersTable = new JTable(model);
		snipersTable.setName(SNIPERS_TABLE_NAME);
		
		return snipersTable;
	}
	public void addUserRequestListener(UserRequestListener userRequestListener) {
		userRequests.addListener(userRequestListener);
 	}
}
