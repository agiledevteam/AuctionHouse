package auctionhouse;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class BidderRenderer implements TableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		BidderSnapshot value2 = (BidderSnapshot)value;
		if (value2.bidderId.isEmpty()) {
			return BidderPanel.EMPTY; 
		}
		BidderPanel panel = new BidderPanel(value2);
		table.setRowHeight(80);
		return panel;
	}

}
