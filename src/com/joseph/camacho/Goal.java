package com.joseph.camacho;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

public class Goal extends GameObject {

	public Goal(float x, float y, float width, float height) {
		super(x, y, width, height);
	}

	public Goal(float x, float y, float width, float height, Image img) {
		super(x, y, width, height, img);
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.blue);
		g.fillRect((int)x, (int)y, (int)width, (int)height);

		g.setColor(Color.black);
		g.drawRect((int)x, (int)y, (int)width, (int)height);

	}
	
	

	@Override
	public void tick() {
	}

}
