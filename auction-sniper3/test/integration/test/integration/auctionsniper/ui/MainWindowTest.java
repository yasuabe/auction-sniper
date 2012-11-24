package test.integration.auctionsniper.ui;

import static org.hamcrest.Matchers.equalTo;
import static test.auctionsniper.util.TestData.newItem;

import org.junit.Test;

import test.endtoend.auctionsniper.AuctionSniperDriver;
import auctionsniper.SniperPortfolio;
import auctionsniper.UserRequestListener;
import auctionsniper.ui.MainWindow;
import auctionsniper.values.Item;

import com.objogate.wl.swing.probe.ValueMatcherProbe;

public class MainWindowTest {

	private final MainWindow          mainWindow = new MainWindow(new SniperPortfolio());
	private final AuctionSniperDriver driver     = new AuctionSniperDriver(100);

	@Test
	public void makesUserRequestWhenJoinButtonClicked() {
		final ValueMatcherProbe<Item> itemProbe = 
				new ValueMatcherProbe<Item>(equalTo(newItem("item-id", 789)), "item request");
	
		mainWindow.addUserRequestListener(new UserRequestListener() {
			public void joinAuction(Item item) {
				itemProbe.setReceivedValue(item);
			}
		});
		driver.startBiddingWithStopPrice("item-id", 789);
		driver.check(itemProbe);
	}
}
