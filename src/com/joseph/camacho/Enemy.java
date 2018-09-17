package com.joseph.camacho;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Enemy extends GameObject {
	private static final float MAX_VELY = 1000;

	private float velX = 0;
	private float velY = 0;

	private boolean goingRight = false;
	// speed when going right/left in pixels per second
	private float speed = 200;
	// Does the enemy fall off platforms?
	private boolean fallOff = false;
	// is the enemy currently falling?
	private boolean inAir;

	public Enemy(float x, float y) {
		super(x, y, 30f, 30f);
	}

	public Enemy(float x, float y, float width, float height) {
		super(x, y, width, height);
	}

	public Enemy(float x, float y, float width, float height, BufferedImage img) {
		super(x, y, width, height, img);
	}

	public Enemy(float x, float y, BufferedImage img) {
		super(x, y, img.getWidth(), img.getHeight(), img);
	}

	public void setVelX(float velX) {
		this.velX = velX;
	}

	public void setVelY(float velY) {
		this.velY = velY;
	}

	public float getVelX() {
		return velX;
	}

	public float getVelY() {
		return velY;
	}

	public boolean getFallOff() {
		return fallOff;
	}

	public void setFallOff(boolean bool) {
		fallOff = bool;
	}

	public void setDirection(boolean bool) {
		// true is going right, false is going left;.
		goingRight = bool;
		velX = goingRight ? speed : -speed;
	}

	public boolean onPlatform(Platform platform) {
		// tests if player is on the platform.
		return !(x >= platform.getRight() || y + height + velY / Game.UPS < platform.getY()
				|| x + width <= platform.getX() || y + height > platform.getY());
	}

	public boolean onRightEdgeOfPlatform(Platform platform) {
		return (x + width + velX / Game.UPS > platform.getRight() && x <= platform.getRight());
	}

	public boolean onLeftEdgeOfPlatform(Platform platform) {
		return (x + velX / Game.UPS < platform.getX() && x + width >= platform.getX());
	}

	public boolean adjacentLeftTo(Platform platform) {
		// tests if player is at the left side of the platform.
		return !(x + width + velX / Game.UPS < platform.getX() || x + width > platform.getX()
				|| y >= platform.getBottom() || y + height <= platform.getY());
	}

	public boolean adjacentRightTo(Platform platform) {
		// tests if player is at the left side of the platform.
		return !(x < platform.getRight() || x + velX / Game.UPS > platform.getRight() || y >= platform.getBottom()
				|| y + height <= platform.getY());
	}

	public boolean getInAir() {
		return inAir;
	}

	public void setInAir(boolean bool) {
		inAir = bool;
	}

	@Override
	public void render(Graphics g) {
		if (img != null) {
			g.drawImage(img, (int) x, (int) y, (int) width, (int) height, null);
		} else {
			g.setColor(Color.red);
			g.fillRect((int) x, (int) y, (int) width, (int) height);
		}

	}

	@Override
	public void tick() {
		if (inAir) {
			// if the enemy is falling, stop velX
			velX = 0;
		} else {
			velX = goingRight ? speed : -speed;
		}

		x += velX / Game.UPS;
		y += velY / Game.UPS;

		velY += Game.gravity / Game.UPS;
		if (velY > MAX_VELY) {
			velY = MAX_VELY;
		}

		// TODO Auto-generated method stub

	}
}
