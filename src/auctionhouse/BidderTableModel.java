package auctionhouse;

import javax.swing.table.AbstractTableModel;

public class BidderTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 5568967747069560297L;

	@Override
	public int getRowCount() {
		return 1;
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return "bidder-" + columnIndex;
	}


}
