package test.auctionsniper.ui;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.hamcrest.Matcher;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import auctionsniper.SniperSnapshot;
import auctionsniper.SniperState;
import auctionsniper.ui.Column;
import auctionsniper.ui.MainWindow;
import auctionsniper.ui.SnipersTableModel;

public class SnipersTableModelTest {
	private final Mockery context = new Mockery();
	private TableModelListener listener = context.mock(TableModelListener.class);
	private final SnipersTableModel model = new SnipersTableModel();
	
	@Before public void attachModelListener() {
		model.addTableModelListener(listener);
	}
	@Test
	public void hasEnoughColumns() {
	    assertThat(model.getColumnCount(), equalTo(Column.values().length)); 
	}
	@Test
	public void setsSniperValuesInColumns() {
		context.checking(new Expectations(){{
			one(listener).tableChanged(with(aRowChangedEvent()));
		}});
		model.sniperStatusChanged(new SniperSnapshot("item id", 555, 666, SniperState.BIDDING));
		assertColumnEquals(Column.ITEM_IDENTIFIER, "item id");
		assertColumnEquals(Column.LAST_PRICE, 555);
		assertColumnEquals(Column.LAST_BID, 666);
		assertColumnEquals(Column.SNIPER_STATE, SnipersTableModel.textFor(SniperState.BIDDING));
	}
	private void assertColumnEquals(Column column, Object expected) {
		final int rowIndex = 0;
		final int columnIndex = column.ordinal();
		assertEquals(expected, model.getValueAt(rowIndex, columnIndex));
	}
	private Matcher<TableModelEvent> aRowChangedEvent() {
		return samePropertyValuesAs(new TableModelEvent(model, 0));
	}
}
