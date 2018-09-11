package com.joseph.camacho;

import javax.swing.JFrame;

public class Window extends JFrame{
	public Window(Game g) {
		this(g, "Platformer");
	}
	public Window(Game g, String windowTitle) {
		setTitle(windowTitle);
		add(g);
		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
