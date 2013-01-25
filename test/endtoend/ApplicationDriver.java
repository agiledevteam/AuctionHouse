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


;
public class ApplicationDriver extends JFrameDriver {

	@SuppressWarnings("unchecked")
	public ApplicationDriver() {
		super(new GesturePerformer(), topLevelFrame(named(MainWindow.AUCTION_HOUSE),
				showingOnScreen()), new AWTEventQueueProber(3000, 200));
	}

	@SuppressWarnings("unchecked")
	public void showsStatus(String status) {
		new JLabelDriver(this, named(MainWindow.AUCTION_STATUS))
				.hasText(equalTo(status));
	}

	public void clickCloseButton() {
		closeButton().click();
	}

	@SuppressWarnings("unchecked")
	private JButtonDriver closeButton() {
		return new JButtonDriver(this, JButton.class, named(MainWindow.CLOSE_BUTTON));
	}

	@SuppressWarnings("unchecked")
	public void showsWinnerIs(String id) {
		new JLabelDriver(this, named(MainWindow.WINNER_LABEL)).hasText(equalTo(id));
	}
}
