package endtoend;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.containsString;

import javax.swing.JButton;
import javax.swing.JTextArea;

import auctionhouse.MainWindow;

import com.objogate.wl.swing.AWTEventQueueProber;
import com.objogate.wl.swing.driver.JButtonDriver;
import com.objogate.wl.swing.driver.JFrameDriver;
import com.objogate.wl.swing.driver.JLabelDriver;
import com.objogate.wl.swing.driver.JTextComponentDriver;
import com.objogate.wl.swing.gesture.GesturePerformer;

public class ApplicationDriver extends JFrameDriver {

	@SuppressWarnings("unchecked")
	public ApplicationDriver() {
		super(new GesturePerformer(), topLevelFrame(
				named(MainWindow.AUCTION_HOUSE), showingOnScreen()),
				new AWTEventQueueProber(3000, 200));
	}

	public void showsStatus(String status) {
		new JLabelDriver(this, named(MainWindow.AUCTION_STATUS))
				.hasText(equalTo(status));
	}

	public void showsActionButton(String label) {
		new JButtonDriver(this, JButton.class, named(MainWindow.ACTION_BUTTON))
				.hasText(equalTo(label));
	}

	public void clickActionButton() {
		actionButton().click();
	}

	private JButtonDriver actionButton() {
		return new JButtonDriver(this, JButton.class,
				named(MainWindow.ACTION_BUTTON));
	}

	public void showsBidderLog(String bidderId, String status, int price) {
		new JTextComponentDriver(this, JTextArea.class,
				named(MainWindow.AUCTION_LOG))
				.hasText(containsString(String.format(
						MainWindow.AUCTION_LOG_FORMAT, bidderId, status, price)));
	}
}
