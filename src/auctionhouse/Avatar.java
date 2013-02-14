package auctionhouse;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Avatar {
	static HashMap<String, Icon> cache = new HashMap<String, Icon>();

	public static JLabel getLabel(String id) {
		return new JLabel(getIcon(id));
	}

	public static Icon getIcon(String id) {
		if (cache.containsKey(id)) {
			return cache.get(id);
		}
		Icon icon = loadIcon(id);
		cache.put(id, icon);
		return icon;
	}

	private static Icon loadIcon(String id) {
		BufferedImage image;
		try {
			image = ImageIO.read(new File("images/avatars/" + id + ".jpg"));
		} catch (IOException e) {
			try {
				image = ImageIO.read(new File("images/avatars/unknown.jpg"));
			} catch (IOException e1) {
				return new ImageIcon();
			}
		}
		return new ImageIcon(image);
	}

}
