package auctionhouse;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class BidderTableModel extends AbstractTableModel {

	private static final int NUM_COLUMNS = 3;
	private static final long serialVersionUID = 5568967747069560297L;

	private List<BidderSnapshot> bidders = new ArrayList<BidderSnapshot>();

	@Override
	public int getRowCount() {
		return (bidders.size() + NUM_COLUMNS - 1) / NUM_COLUMNS;
	}

	@Override
	public int getColumnCount() {
		return NUM_COLUMNS;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		int index = rowIndex * NUM_COLUMNS + columnIndex;
		if (index < bidders.size()) {
			return bidders.get(index).bidderId;
		} else {
			return "";
		}
	}

	public void addBidder(BidderSnapshot bidderSnapshot) {
		bidders.add(bidderSnapshot);
		fireTableDataChanged();
	}

	
}
