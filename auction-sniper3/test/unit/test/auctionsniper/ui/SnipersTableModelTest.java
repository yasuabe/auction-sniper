package test.auctionsniper.ui;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.hamcrest.Matcher;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import test.auctionsniper.util.TestData;

import auctionsniper.snapshot.SniperSnapshot;
import auctionsniper.ui.Column;
import auctionsniper.ui.SnipersTableModel;
import auctionsniper.ui.StatusTexts;
import auctionsniper.util.Defect;
import auctionsniper.values.ItemId;
import auctionsniper.values.Amount;

public class SnipersTableModelTest {
	private final Mockery           context  = new Mockery();
	private TableModelListener      listener = context.mock(TableModelListener.class);
	private final SnipersTableModel model    = new SnipersTableModel();
	
	@Before public void attachModelListener() {
		model.addTableModelListener(listener);
	}
	@Test
	public void hasEnoughColumns() {
	    assertThat(model.getColumnCount(), equalTo(Column.values().length)); 
	}
	@Test
	public void setsSniperValuesInColumns() {
		SniperSnapshot joining = joining("item id"); 
		SniperSnapshot bidding = joining.bidding(Amount.fromInt(555), Amount.fromInt(666));
		
		context.checking(new Expectations(){{
			allowing(listener).tableChanged(with(anyInsertionEvent()));
			one(listener).tableChanged(with(aChangeInRow(0)));
		}});
		model.addSniperSnapshot(joining);
		model.sniperStateChanged(bidding);

		assertRowMatchesSnapshot(0, bidding);
	}
	private Matcher<TableModelEvent> aChangeInRow(int row) {
		return samePropertyValuesAs(new TableModelEvent(model, row));
	}
	Matcher<TableModelEvent> anyInsertionEvent() {
		return hasProperty("type", equalTo(TableModelEvent.INSERT));
	}
	@Test
	public void setsUpColumnHeadings() {
		for (Column column: Column.values()) {
			assertEquals(column.name, model.getColumnName(column.ordinal()));
		}
	}
	@Test public void notifiesListenersWhenAddingASniper() {
		SniperSnapshot joining = joining("item123");
		context.checking(new Expectations() {{
			one(listener).tableChanged(with(anyInsertionAtRow(0)));
		}});
		assertEquals(0, model.getRowCount());
		
		model.addSniperSnapshot(joining);
		
		assertEquals(1, model.getRowCount());
		assertRowMatchesSnapshot(0, joining);
	}
	@Test public void holdsSnipersInAdditionOrder() {
		context.checking(new Expectations() {{
			ignoring(listener);
		}});
		
		model.addSniperSnapshot(joining("item 0"));
		model.addSniperSnapshot(joining("item 1"));
		
		assertEqualItemId("item 0", 0);
		assertEqualItemId("item 1", 1);
	}
	@Test(expected = Defect.class)
	public void throwsDefectIfNoExistingSniperForAnUpdate() {
		model.sniperStateChanged(TestData.winningSnapshot("item 1", 123, 234));
	}
	@Test
	public void updatesCorrectRowForSniper() {
		context.checking(new Expectations() {{
			allowing(listener).tableChanged(with(anyInsertionEvent()));
			allowing(listener).tableChanged(with(aChangeInRow(1)));
		}});
		SniperSnapshot item1 = joining("item 1");
		model.addSniperSnapshot(joining("item 0"));
		model.addSniperSnapshot(item1);

		SniperSnapshot winning1 = item1.winning(Amount.fromInt(123));
		model.sniperStateChanged(winning1);
		
		assertRowMatchesSnapshot(1, winning1);
	}
	private void assertRowMatchesSnapshot(int row, SniperSnapshot snapshot) {
		assertEquals(snapshot.itemId, cellValue(row, Column.ITEM_IDENTIFIER));
		assertEquals(snapshot.lastPrice(), cellValue(row, Column.LAST_PRICE));
		assertEquals(snapshot.lastBid(), cellValue(row, Column.LAST_BID));
		assertEquals(StatusTexts.textFor(snapshot),
				cellValue(row, Column.SNIPER_STATE));
	}
	Matcher<TableModelEvent> anInsertionAtRow(final int row) {
		return samePropertyValuesAs(new TableModelEvent(model, row, row,
				TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
	}
	Matcher<TableModelEvent> anyInsertionAtRow(final int row) {
		return samePropertyValuesAs(new TableModelEvent(model, row, row,
				TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
	}
	private Object cellValue(int rowIndex, Column column) {
		return model.getValueAt(rowIndex, column.ordinal());
	}
	private static SniperSnapshot joining(String itemId) {
		return SniperSnapshot.joining(ItemId.fromString(itemId));		
	}
	private void assertEqualItemId(String expected, int rowIndex) {
		assertEquals(expected, cellValue(rowIndex, Column.ITEM_IDENTIFIER).toString());
	}
}
