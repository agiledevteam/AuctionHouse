package auctionhouse.ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import auctionhouse.BidderSnapshot;
import auctionhouse.Defect;

public class BidderTableModel extends AbstractTableModel {

	private static final int NUM_COLUMNS = 3;
	private static final long serialVersionUID = 5568967747069560297L;

	private List<BidderSnapshot> bidders = new ArrayList<BidderSnapshot>();
	private BidderSnapshot nullBidderSnapshot = new BidderSnapshot("", "");

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
			return bidders.get(index);
		} else {
			return nullBidderSnapshot;
		}
	}

	public void addBidder(BidderSnapshot bidderSnapshot) {
		bidders.add(bidderSnapshot);
		fireTableDataChanged();
	}

	public void setBidder(BidderSnapshot bidderSnapshot) {
		int index = findBidder(bidderSnapshot.bidderId);
		bidders.set(index, bidderSnapshot);
		fireTableDataChanged();
	}

	private int findBidder(String bidderId) {
		for (int i = 0; i < bidders.size(); i++) {
			if (bidderId.equals(bidders.get(i).bidderId)) {
				return i;
			}
		}
		throw new Defect("bidder not found");
	}

}
