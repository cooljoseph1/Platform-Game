package com.joseph.camacho;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

public class Player extends GameObject {

	private boolean inAir = true;

	private static final float MAX_VELY = 1000;

	private float velX = 0;
	private float velY = 0;

	// Tracks whether character is moving to the right (or was before the left key
	// was pressed)
	private boolean rightDown = false;
	private boolean leftDown = false;
	private boolean goingRight = false;
	// like above
	private boolean goingLeft = false;
	// speed when going right/left in pixels per second
	private float speed = 400;

	public Player(float x, float y) {
		super(x, y, 30f, 50f);
	}

	public Player(float x, float y, float width, float height) {
		super(x, y, width, height);
	}

	public Player(float x, float y, float width, float height, Image img) {
		super(x, y, width, height, img);
	}

	public Player(float x, float y, Image img) {
		super(x, y, 30f, 50f, img);
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

	public void startMovingRight() {
		goingRight = true;
		goingLeft = false;
		rightDown = true;
		velX = speed;
	}

	public void startMovingLeft() {
		goingLeft = true;
		goingRight = false;
		leftDown = true;
		velX = -speed;
	}

	public void stopMovingRight() {
		goingRight = false;
		rightDown = false;
		if (leftDown) {
			velX = -speed;
			goingLeft = true;
		} else {
			velX = 0;
		}
	}

	public void stopMovingLeft() {
		goingLeft = false;
		leftDown = false;
		if (rightDown) {
			velX = speed;
			goingRight = true;
		} else {
			velX = 0;
		}
	}

	public boolean onPlatform(Platform platform) {
		// tests if player is on the platform.
		return !(x >= platform.getRight() || y + height + velY / Game.UPS < platform.getY()
				|| x + width <= platform.getX() || y + height > platform.getY());
	}

	public boolean onEnemy(Enemy enemy) {
		// tests if player is on the platform.
		return !(x >= enemy.getRight() || y + height + velY / Game.UPS < enemy.getY() || x + width <= enemy.getX()
				|| y + height > enemy.getY());
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

	public boolean adjacentTo(Platform platform) {
		// tests if player is at the side of a platform.

		return !(x > platform.getX() + platform.getWidth() || y + height - velY / Game.UPS > platform.getY()
				|| x + width < platform.getX() || y + height < platform.getY());
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.blue);
		g.fillRect((int) x, (int) y, (int) width, (int) height);
		// TODO Auto-generated method stub

	}

	@Override
	public void tick() {
		x += velX / Game.UPS;
		y += velY / Game.UPS;
		if (goingRight) {
			velX = speed;
		} else if (goingLeft) {
			velX = -speed;
		}
		velY += Game.gravity;
		if (velY > MAX_VELY) {
			velY = MAX_VELY;
		}

		// TODO Auto-generated method stub

	}
}
