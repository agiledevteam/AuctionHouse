package endtoend;

import static org.hamcrest.Matchers.equalTo;

import javax.swing.JButton;
import javax.swing.JTextField;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import auctionhouse.ui.BidderPanel;
import auctionhouse.ui.MainWindow;

import com.objogate.wl.swing.AWTEventQueueProber;
import com.objogate.wl.swing.driver.JButtonDriver;
import com.objogate.wl.swing.driver.JFrameDriver;
import com.objogate.wl.swing.driver.JLabelDriver;
import com.objogate.wl.swing.driver.JTableDriver;
import com.objogate.wl.swing.driver.JTextFieldDriver;
import com.objogate.wl.swing.gesture.GesturePerformer;

public class ApplicationDriver extends JFrameDriver {

	@SuppressWarnings("unchecked")
	public ApplicationDriver() {
		super(new GesturePerformer(), topLevelFrame(
				named(MainWindow.AUCTION_HOUSE), showingOnScreen()),
				new AWTEventQueueProber(3000, 200));
	}

	@SuppressWarnings("unchecked")
	public void showsBidderStatus(String bidderId, int price) {
		new JTableDriver(this, named(MainWindow.BIDDER_TABLE))
				.hasCell(withBidder(bidderId, Integer.toString(price)));
	}

	@SuppressWarnings("unchecked")
	public void showsBidderStatus(String bidderId, String status) {
		new JTableDriver(this, named(MainWindow.BIDDER_TABLE))
				.hasCell(withBidder(bidderId, status));
	}

	private TypeSafeMatcher<BidderPanel> withBidder(final String bidderId,
			final String detail) {
		return new TypeSafeMatcher<BidderPanel>() {
			@Override
			public void describeTo(Description description) {
				description.appendText("Bidder(" + bidderId + ") with "
						+ detail);
			}

			@Override
			protected boolean matchesSafely(BidderPanel item) {
				return item.matches(bidderId, detail);
			}
		};
	}

	@SuppressWarnings("unchecked")
	public void showsStatus(String status) {
		new JLabelDriver(this, named(MainWindow.AUCTION_STATUS))
				.hasText(equalTo(status));
	}

	public void clickCloseButton() {
		closeButton().click();
	}

	public void clickStartButton() {
		startButton().click();
	}

	@SuppressWarnings("unchecked")
	private JButtonDriver closeButton() {
		return new JButtonDriver(this, JButton.class,
				named(MainWindow.CLOSE_BUTTON));
	}

	@SuppressWarnings("unchecked")
	private JButtonDriver startButton() {
		return new JButtonDriver(this, JButton.class,
				named(MainWindow.START_BUTTON));
	}

	@SuppressWarnings("unchecked")
	public void showsWinnerIs(String id) {
		new JLabelDriver(this, named(MainWindow.WINNER_LABEL))
				.hasText(equalTo(id));
	}

	public void clickAddFake() {
		new JButtonDriver(this, JButton.class, named(MainWindow.ADD_BUTTON))
				.click();
	}

	public void setIncrement(int increment) {
		JTextFieldDriver textField = new JTextFieldDriver(this, JTextField.class,
				named(MainWindow.FIELD_INCREMENT));
		textField.focusWithMouse();
		textField.replaceAllText(String
				.valueOf(increment));
	}

	public void setHost(String host) {
		JTextFieldDriver textField = new JTextFieldDriver(this, JTextField.class,
				named(MainWindow.FIELD_HOST));
		textField.focusWithMouse();
		textField.replaceAllText(host);	
	}
}
