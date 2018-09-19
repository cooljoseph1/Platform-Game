package com.joseph.camacho;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener {
	Game game;
	long downKeyTime = -1;
	public KeyInput(Game game) {
		this.game = game;
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		switch (keyCode) {
		case (KeyEvent.VK_UP):
			if (!game.player.getInAir()) {
				game.player.setVelY(-830f);
			}
			break;
		case (KeyEvent.VK_DOWN):
			//tapping twice on the down key in quick succession makes the player fall through the platform they are on.
			long currentTime = System.currentTimeMillis();
			if(downKeyTime==-1) {
				downKeyTime=System.currentTimeMillis();
			} else if(currentTime-downKeyTime<Player.getDownKeySensitivity()) {
				game.player.setVelY(800f);
				game.player.setY(game.player.getY()+1);
				downKeyTime=-1;
				
			} else {
				downKeyTime=System.currentTimeMillis();
			}
			break;
		case (KeyEvent.VK_RIGHT):
			// start moving the player right
			game.player.startMovingRight();
			break;
		case (KeyEvent.VK_LEFT):
			// start moving the player left.
			game.player.startMovingLeft();
			break;
		}
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		int keyCode = e.getKeyCode();
		switch (keyCode) {
		case (KeyEvent.VK_UP):
			break;
		case (KeyEvent.VK_DOWN):
			break;
		case (KeyEvent.VK_RIGHT):
			game.player.stopMovingRight();
			break;
		case (KeyEvent.VK_LEFT):
			game.player.stopMovingLeft();
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
