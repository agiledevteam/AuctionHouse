package auctionhouse;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Avatar {

	public static JLabel get(String bidderId) {
		BufferedImage image;
		try {
			image = ImageIO.read(new File("images/avatars/" + bidderId + ".jpg"));
		} catch (IOException e) {
			try {
				image = ImageIO.read(new File("images/avatars/unknown.jpg"));
			} catch (IOException e1) {
				return new JLabel(bidderId);
			}
		}
		return new JLabel(new ImageIcon(image));
	}

}
