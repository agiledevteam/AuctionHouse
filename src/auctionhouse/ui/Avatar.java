package auctionhouse.ui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Avatar {
	static HashMap<String, BufferedImage> imageCache = new HashMap<String, BufferedImage>();
	static HashMap<String, Icon> smallIconCache = new HashMap<String, Icon>();
	static HashMap<String, Icon> largeIconCache = new HashMap<String, Icon>();

	public static Icon getIcon(String id) {
		return getIcon(id, smallIconCache, 80);
	}

	public static Icon getIconLarge(String id) {
		return getIcon(id, largeIconCache, 100);
	}

	public static Icon getIcon(String id, HashMap<String, Icon> cache, int size) {
		if (cache.containsKey(id)) {
			return cache.get(id);
		}

		BufferedImage image = getImage(id);
		image = makeRoundedCorner(image, size, size, 20);
		Icon icon = new ImageIcon(image);
		cache.put(id, icon);
		return icon;
	}

	private static BufferedImage getImage(String id) {
		if (imageCache.containsKey(id)) {
			return imageCache.get(id);
		}
		BufferedImage image = loadImage(id);
		imageCache.put(id, image);
		return image;
	}

	private static BufferedImage loadImage(String id) {
		try {
			return ImageIO.read(new File("images/avatars/" + id + ".jpg"));
		} catch (IOException e) {
			try {
				return ImageIO.read(new File("images/avatars/unknown.jpg"));
			} catch (IOException e1) {
				return new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
			}
		}
	}

	public static BufferedImage makeRoundedCorner(BufferedImage image, int w,
			int h, int cornerRadius) {
		BufferedImage output = new BufferedImage(w, h,
				BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2 = output.createGraphics();

		// This is what we want, but it only does hard-clipping, i.e. aliasing
		// g2.setClip(new RoundRectangle2D ...)

		// so instead fake soft-clipping by first drawing the desired clip shape
		// in fully opaque white with antialiasing enabled...
		g2.setComposite(AlphaComposite.Src);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.WHITE);
		g2.fill(new RoundRectangle2D.Float(0, 0, w, h, cornerRadius,
				cornerRadius));

		// ... then compositing the image on top,
		// using the white shape from above as alpha source
		g2.setComposite(AlphaComposite.SrcAtop);
		g2.drawImage(image, 0, 0, w, h, 0, 0, image.getWidth(),
				image.getHeight(), null);

		g2.dispose();

		return output;
	}

}
