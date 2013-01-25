package endtoend;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class Config {
	public static String host = "localhost";
	public static String auctionId = "auction-item-54321@localhost";
	public static String auctionPassword = "auction";
	public static String bidder1Id = "bidder-1@localhost";
	public static String bidder1Password = "bidder";
	public static String bidder2Id = "bidder-2@localhost";
	public static String bidder2Password = "bidder";

	static {
		Configuration config;
		try {
			config = new PropertiesConfiguration("test.config");
			host = config.getString("xmpp.host");
			auctionId = config.getString("auction.id");
			auctionPassword = config.getString("auction.password");
			bidder1Id = config.getString("bidder1.id");
			bidder1Password = config.getString("bidder1.password");
			bidder2Id = config.getString("bidder2.id");
			bidder2Password = config.getString("bidder2.password");
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}
}
