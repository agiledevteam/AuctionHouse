package endtoend;

import auctionhouse.Main;

public class ApplicationRunner {

	private ApplicationDriver driver;

	public void startAuction() {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Main.main("localhost", "5222", "item-54321", "auction");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		thread.setDaemon(true);
		thread.start();
		driver = new ApplicationDriver();
	}

	public void showsStarted() {
		driver.showsStatus("Started");
	}

	public void showBidderJoined(String bidderId, int price) {
		driver.showsStatus("Joined");
	}

	public void closeAuction() {
		driver.clickCloseButton();
	}

	public void showBidderBidding(String bidderId, int price) {
		driver.showsStatus("Bidding");
	}

	public void showsWinnerIs(String id) {
		driver.showsWinnerIs(id);
	}

	public void stop() {
		if (driver != null) {
			driver.dispose();
		}
	}
}
