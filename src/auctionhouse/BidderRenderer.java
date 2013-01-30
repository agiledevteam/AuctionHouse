package auctionhouse;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class BidderRenderer implements TableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		BidderPanel panel = new BidderPanel((BidderSnapshot)value);
		table.setRowHeight(32);
		return panel;
	}

}
