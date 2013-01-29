package unittest;


import static org.junit.Assert.assertNotNull;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.junit.Test;

public class PropertiesTest {

	@Test
	public void mustHaveTestConfigFile() throws ConfigurationException {
		Configuration config = new PropertiesConfiguration("test.config");
		assertNotNull(config.getString("xmpp.host"));
	}

}
