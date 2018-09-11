package com.joseph.camacho;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

public class Lava extends GameObject {

	public Lava(float x, float y, float width, float height) {
		super(x, y, width, height);
	}

	public Lava(float x, float y, float width, float height, Image img) {
		super(x, y, width, height, img);
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect((int)x, (int)y, (int)width, (int)height);

		g.setColor(Color.black);
		g.fillRect((int)x, (int)(y+3*height/4), (int)width, (int)(height/4 + 1));

	}
	
	

	@Override
	public void tick() {
	}

}
