package map;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class MyPanel extends JPanel {
	
	private Image img;
	
	{
		setImage("C:\\Users\\Nick Fast\\IdeaProjects\\BattleShips\\res\\images\\bombed.png");
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				g.drawImage(img, j * 50, i * 50, this);
			}
		}
	}
	
	public void setImage(String path) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(path));
		} catch (IOException e) {
			System.out.println("stupid\n");
		}
		this.img = image;
	}
}
