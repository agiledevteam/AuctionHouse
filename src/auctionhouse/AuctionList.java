package auctionhouse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

class AuctionList {
	private Map<String, Auction> auctions = new HashMap<String, Auction>();

	public void add(String bidderId, Auction newAuction) {
		auctions.put(bidderId, newAuction);
	}

	public void sendPrice(int currentPrice, int increment, String winner) {
		Logger.getLogger("han").info(
				String.format("AuctionBucket.sendPrice(%d,%d,%s)",
						currentPrice, increment, winner));
		List<Auction> targets = new ArrayList<Auction>(auctions.values());
		Collections.shuffle(targets);
		for (Auction auction : targets) {
			auction.currentPrice(currentPrice, increment, winner);
		}
	}

	public void sendClose() {
		for (Auction auction : auctions.values()) {
			auction.auctionClosed();
		}
	}
}