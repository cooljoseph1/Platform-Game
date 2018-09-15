package com.joseph.camacho;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;

public class Platform extends GameObject {

	public Platform(float x, float y, float width, float height) {
		super(x, y, width, height);
	}

	public Platform(float x, float y, float width, float height, BufferedImage img) {
		super(x, y, width, height, img);
	}

	@Override
	public void render(Graphics g) {
		if (img != null) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setPaint(new TexturePaint(img,new Rectangle(0,0,img.getWidth(),img.getHeight())));
			g2d.fillRect((int)x, (int)y, (int)width, (int)height);
		} else {
			g.setColor(Color.green);
			g.fillRect((int) x, (int) y, (int) width, (int) height);

			g.setColor(Color.black);
			g.drawRect((int) x, (int) y, (int) width, (int) height);
		}

	}

	@Override
	public void tick() {
	}

}
