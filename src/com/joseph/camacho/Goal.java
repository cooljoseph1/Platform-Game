package com.joseph.camacho;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;

public class Goal extends GameObject {

	public Goal(float x, float y, float width, float height) {
		super(x, y, width, height);
	}

	public Goal(float x, float y, float width, float height, BufferedImage img) {
		super(x, y, width, height, img);
	}

	@Override
	public void render(Graphics g) {
		if (img != null) {
			g.drawImage(img, (int) x, (int) y, (int) width, (int) height, null);
		} else {
			g.setColor(Color.blue);
			g.fillRect((int) x, (int) y, (int) width, (int) height);

			g.setColor(Color.black);
			g.drawRect((int) x, (int) y, (int) width, (int) height);

		}
	}

	@Override
	public void tick() {
	}

}
