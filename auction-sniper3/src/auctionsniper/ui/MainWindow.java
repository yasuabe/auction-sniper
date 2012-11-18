package auctionsniper.ui;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import auctionsniper.Main;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	public static final String SNIPER_STATUS_NAME = "sniper status";
	private final JLabel sniperStatus = createLabel1(Main.STATUS_JOINING);
	public MainWindow() {
		super("Auction Sniper");
		setName(Main.MAIN_WINDOW_NAME);
		add(sniperStatus);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	private JLabel createLabel1(String initialText) {
		JLabel result = new JLabel(initialText);
		result.setName(SNIPER_STATUS_NAME);
		result.setBorder(new LineBorder(Color.BLACK));
		return result;
	}
}
