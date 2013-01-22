package endtoend;

import auctionhouse.Main;

public class ApplicationRunner {

	private ApplicationDriver driver;

	public void startAuction() {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Main.main("danielkang-01", "5222", "item-54321", "auction");
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

	public void receivedJoinMessage() {
		driver.showsStatus("Joined");
	}

	public void closeAuction() {
		driver.clickCloseButton();
	}

}
