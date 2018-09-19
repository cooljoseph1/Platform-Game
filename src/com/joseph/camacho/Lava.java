package com.joseph.camacho;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Lava extends Platform {

	public Lava(float x, float y, float width, float height) {
		super(x, y, width, height);
	}

	public Lava(float x, float y, float width, float height, BufferedImage img) {
		super(x, y, width, height, img);
	}

	@Override
	public void render(Graphics g) {
		if (img != null) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setPaint(texturePaint);
			g2d.fillRect((int) x, (int) y, (int) width, (int) height);
		} else {
			g.setColor(Color.red);
			g.fillRect((int) x, (int) y, (int) width, (int) height);

			g.setColor(Color.black);
			g.fillRect((int) x, (int) (y + 3 * height / 4), (int) width, (int) (height / 4 + 1));
		}
	}

	@Override
	public void tick() {
	}

}
