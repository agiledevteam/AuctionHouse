package endtoend;

import static org.junit.Assert.*;

import org.junit.Test;

import auctionhouse.MainWindow;

public class MainWindowTest {
	ApplicationDriver driver = new ApplicationDriver();
	
	@Test
	public void actionButtonInitialTextIsStart() throws Exception {
		driver.showsActionButton(MainWindow.ACTION_BUTTON_START);
	}

}
