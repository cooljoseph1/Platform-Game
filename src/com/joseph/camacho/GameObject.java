package com.joseph.camacho;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

public abstract class GameObject {
	protected float x;
	protected float y;
	protected float width;
	protected float height;

	protected BufferedImage img;

	public GameObject(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

	}
	
	public GameObject(float x, float y, float width, float height, BufferedImage img) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.img=img;

	}

	
	public BufferedImage getImage() {
		return img;
	}

	public void setImage(BufferedImage img) {
		this.img = img;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	public float getRight() {
		return x+width;
	}

	public float getBottom() {
		return y+height;
	}
	
	public float getLeft() {
		return x;
	}
	public float getTop() {
		return y;
	}

	public void setRight(float r) {
		//sets right side of object by changing width accordingly
		width=r-x;
	}

	public void setBottom(float b) {
		//sets bottom of object by changing height accordingly
		height=b-y;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public static boolean intersectRect(GameObject gameObject1, GameObject gameObject2) {
		// Returns whether the rects of two game objects intersect.
		// returns true if they share and edge.
		return !(gameObject1.getX() > gameObject2.getX() + gameObject2.getWidth()
				|| gameObject1.getY() > gameObject2.getY() + gameObject2.getHeight()
				|| gameObject1.getX() + gameObject1.getWidth() < gameObject2.getX()
				|| gameObject1.getY() + gameObject1.getHeight() < gameObject2.getY());
	}

	public abstract void render(Graphics g);

	public abstract void tick();
	
}
