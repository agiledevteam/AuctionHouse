package endtoend;

import auctionhouse.Main;

public class ApplicationRunner {

	private ApplicationDriver driver;

	public void startAuction() {
		startMain(new String[]{});
	}

	private void startMain(final String[] args ) {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Main.main(args);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		thread.setDaemon(true);
		thread.start();
		driver = new ApplicationDriver();
		driver.setIncrement(50);
		driver.clickStartButton();
	}

	public void showsStarted() {
		driver.showsStatus("Started");
	}

	public void showBidderJoined(String bidderId) {
		driver.showsBidderStatus(bidderId, "Joined");
	}

	public void closeAuction() {
		driver.clickCloseButton();
	}

	public void showBidderBidding(String bidderId, int price) {
		driver.showsBidderStatus(bidderId, price);
	}

	public void showsWinnerIs(String id) {
		driver.showsWinnerIs(id);
	}

	public void stop() {
		if (driver != null) {
			driver.dispose();
		}
	}

	public void startTestMode() {
		startMain(new String[]{"-test"});
	}

	public void addFakeBidder() {
		driver.clickAddFake();
	}

}
