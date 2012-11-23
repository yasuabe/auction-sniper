package test.auctionsniper.ui;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import javax.swing.event.TableModelListener;

import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import auctionsniper.ui.Column;
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
}
