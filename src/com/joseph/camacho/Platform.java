package com.joseph.camacho;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

public class Platform extends GameObject {

	public Platform(float x, float y, float width, float height) {
		super(x, y, width, height);
	}

	public Platform(float x, float y, float width, float height, Image img) {
		super(x, y, width, height, img);
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.green);
		g.fillRect((int)x, (int)y, (int)width, (int)height);

	}
	
	

	@Override
	public void tick() {
	}

}
