package com.joseph.camacho;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Coin extends GameObject {

	public Coin(float x, float y) {
		super(x, y, 20f, 20f);
	}
	
	public Coin(float x, float y, float width, float height) {
		super(x, y, width, height);
	}

	public Coin(float x, float y, float width, float height, BufferedImage img) {
		super(x, y, width, height, img);
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.yellow);
		g.fillOval((int)x, (int)y, (int)width, (int)height);

	}
	
	

	@Override
	public void tick() {
	}

}
