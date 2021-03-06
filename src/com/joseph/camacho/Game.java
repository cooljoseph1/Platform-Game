package com.joseph.camacho;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Game extends JPanel {
	public final static int WIDTH = 800;
	public final static int HEIGHT = 500;
	public static double UPS;
	public static double FPS;
	public static final Dimension windowDimension = new Dimension(WIDTH, HEIGHT);

	public static BufferedImage platformTile;
	public static BufferedImage lavaTile;
	public static BufferedImage playerImage;
	public static BufferedImage coinImage;
	public static BufferedImage goalImage;
	public static BufferedImage enemyImage;
	{

		try {
			platformTile = ImageIO.read(new File("lib\\PlatformTile.png"));
			lavaTile = ImageIO.read(new File("lib\\LavaTile2.png"));
			playerImage = ImageIO.read(new File("lib\\Person.png"));
			coinImage = ImageIO.read(new File("lib\\Coin.png"));
			goalImage = ImageIO.read(new File("lib\\Goal.png"));
			enemyImage = ImageIO.read(new File("lib\\Enemy.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static float gravity = 1800f;

	private Window window;
	private boolean running;

	public LinkedList<GameObject> objects = new LinkedList<GameObject>();
	public Player player;
	public Goal goal;
	public LinkedList<Platform> platforms = new LinkedList<Platform>();
	public LinkedList<Enemy> enemies = new LinkedList<Enemy>();
	public LinkedList<Lava> lavaFields = new LinkedList<Lava>();
	public LinkedList<Coin> coins = new LinkedList<Coin>();
	public Dimension screenPosition = new Dimension(0, 0);

	private int lastLevel = new File("lib").list().length; // Set a default number of levels as the number of levels in
															// the lib. Note that this will almost certainly include
															// other items such as images, but we can ignore those.

	private int currentLevel = 1;

	private int score = 0;

	public Game() {

		// initialize window size.
		// perhaps I should consider making it resizable later.
		setPreferredSize(windowDimension);
		setMaximumSize(windowDimension);
		setMinimumSize(windowDimension);

		// Add the game to a window
		window = new Window(this);

		KeyInput keyInput = new KeyInput(this);
		addKeyListener(keyInput);

		this.setFocusable(true);
		this.requestFocus();

	}

	public void loadLevel(String file) {
		BufferedReader reader = null;
		reset();
		try {
			reader = new BufferedReader(new FileReader(file));
			loadLevelHelper(reader);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
			}
		}
	}

	public void loadLevel(File file) {
		reset();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			loadLevelHelper(reader);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
			}
		}
	}

	public void reset() {
		objects = new LinkedList<GameObject>();
		player = null;
		goal = null;
		lavaFields = new LinkedList<Lava>();
		platforms = new LinkedList<Platform>();
		enemies = new LinkedList<Enemy>();
		coins = new LinkedList<Coin>();
	}

	public void loadLevelHelper(BufferedReader reader) throws IOException {
		String text = null;
		while ((text = reader.readLine()) != null) {
			String[] objectInfo = text.split("[(),;]");
			switch (objectInfo[0]) {
			case ("Coin"): {
				Coin coin = new Coin(Float.parseFloat(objectInfo[1]), Float.parseFloat(objectInfo[2]), coinImage);
				coins.add(coin);
				objects.add(coin);
				break;
			}
			case ("Enemy"): {
				Enemy enemy = new Enemy(Float.parseFloat(objectInfo[1]), Float.parseFloat(objectInfo[2]), enemyImage);
				enemies.add(enemy);
				objects.add(enemy);
				break;
			}
			case ("Platform"): {
				Platform platform = new Platform(Float.parseFloat(objectInfo[1]), Float.parseFloat(objectInfo[2]),
						Float.parseFloat(objectInfo[3]), Float.parseFloat(objectInfo[4]), platformTile);
				platforms.add(platform);
				objects.add(platform);
				break;
			}
			case ("Lava"): {
				Lava lava = new Lava(Float.parseFloat(objectInfo[1]), Float.parseFloat(objectInfo[2]),
						Float.parseFloat(objectInfo[3]), Float.parseFloat(objectInfo[4]), lavaTile);
				lavaFields.add(lava);
				platforms.add(lava); // Treat lava like a platform. You can walk on it, but it will kill you.
				objects.add(lava);
				break;
			}
			case ("Player"): {
				player = new Player(Float.parseFloat(objectInfo[1]), Float.parseFloat(objectInfo[2]), playerImage);
				objects.add(player);
				break;
			}
			case ("Goal"): {
				goal = new Goal(Float.parseFloat(objectInfo[1]), Float.parseFloat(objectInfo[2]),
						Float.parseFloat(objectInfo[3]), Float.parseFloat(objectInfo[4]), goalImage);
				objects.add(goal);
				break;
			}
			default:
				break;
			}

		}
	}

	public void run() {
		// A lot of this loop is taken from
		// https://stackoverflow.com/questions/18283199/java-main-game-loop
		UPS = 500;
		FPS = 60;

		long initialTime = System.nanoTime();
		long endTime = initialTime;

		double timeU = (1000000000 / UPS);
		double timeF = (1000000000 / FPS);

		double deltaU = 0;
		double deltaF = 0;

		// main game loop
		while (running) {

			long timeDifference = (System.nanoTime() - initialTime);
			initialTime = System.nanoTime();

			deltaU += timeDifference;
			deltaF += timeDifference;

			if (deltaU >= timeU) {
				update();
				deltaU %= timeU;
			}
			if (deltaF >= timeF) {
				this.paintImmediately(0, 0, WIDTH, HEIGHT);
				deltaF %= timeF;
			}

			endTime = System.nanoTime();
			long timeToSleep = (long) (initialTime + timeU - endTime) / 2;

			try {
				Thread.sleep(Math.max((timeToSleep / 1000000), 0), Math.max((int) timeToSleep % 1000000, 0));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	public boolean isRunning() {
		return running;
	}

	public void stopRunning() {
		running = false;
		this.paintImmediately(0, 0, WIDTH, HEIGHT);
	}

	public void startRunning() {
		running = true;
		run();
	}

	public void setRunning(boolean bool) {
		running = bool;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		render(g);
	}

	public void render(Graphics g) {
		screenPosition = new Dimension(((int) player.x - WIDTH / 2 + (int) player.width / 2),
				((int) player.y - HEIGHT / 2));
		g.translate(-screenPosition.width, -screenPosition.height);

		g.setColor(Color.white);
		g.fillRect(screenPosition.width, screenPosition.height, WIDTH, HEIGHT);

		g.setColor(Color.black);
		g.drawString("Score:  " + Integer.toString(score), 10 + screenPosition.width, 10 + screenPosition.height);

		for (GameObject obj : objects) {
			obj.render(g);
		}
	}

	public void update() {

		if (GameObject.intersectRect(player, goal)) {
			File nextLevel = this.nextLevel();
			if (nextLevel != null) {
				this.loadLevel(nextLevel);
			} else {
				this.stopRunning();
				return;
			}
		}

		player.setInAir(true); // gets disabled if on a platform
		enemies.forEach((enemy) -> enemy.setInAir(true)); // gets disabled if on a platform
		LinkedList<Enemy> enemiesToRemove = new LinkedList<Enemy>();

		LinkedList<Platform> platformsPlayerIsOn = new LinkedList<Platform>();

		for (Platform platform : platforms) {
			// deal with player stuff about platforms
			
			if (player.onPlatform(platform)) {
				if (platform instanceof Lava) {
					this.stopRunning();
					return;
				}
				platformsPlayerIsOn.add(platform);
			}
			if (player.adjacentLeftTo(platform)) {
				player.setVelX(min(0, player.getVelX()));
				player.setX(platform.x - player.width);
			}
			if (player.adjacentRightTo(platform)) {
				player.setVelX(max(0, player.getVelX()));
				player.setX(platform.x + platform.width);
			}
			if(platform instanceof Lava) {
				if(player.adjacentBottomTo(platform)) {
					player.setVelY(0);
					player.setY(platform.getBottom());
				}
			}
			// deal with enemy stuff about platforms
			for (Enemy enemy : enemies) {
				if (enemy.onPlatform(platform)) {
					enemy.setInAir(false);
					enemy.setVelY((float) min(0, enemy.getVelY()));
					enemy.setY(platform.y - enemy.height);
					if (enemy.onLeftEdgeOfPlatform(platform)) {
						enemy.setDirection(true);
					}
					if (enemy.onRightEdgeOfPlatform(platform)) {
						enemy.setDirection(false);
					}
				}
				if (enemy.adjacentLeftTo(platform)) {
					enemy.setDirection(false);
				}
				if (enemy.adjacentRightTo(platform)) {
					enemy.setDirection(true);
				}

			}
		}
		if (platformsPlayerIsOn.size() > 0) {

			player.setVelY((float) min(0, player.getVelY()));

			player.setInAir(false);
			float minY = Float.MAX_VALUE;
			for (Platform p : platformsPlayerIsOn)
				minY = minY < p.getY() ? minY : p.getY();
			player.setY(minY - player.getHeight());
		}
		for (Enemy enemy : enemies) {
			if (player.onEnemy(enemy)) {
				enemiesToRemove.add(enemy);
				score += 10;
			} else if (GameObject.intersectRect(player, enemy)) {
				this.stopRunning();
				return;
			}
		}

		LinkedList<Coin> coinsToRemove = new LinkedList<Coin>();
		for (Coin coin : coins) {
			if (GameObject.intersectRect(coin, player)) {
				coinsToRemove.add(coin);
				score += 1;
			}
		}
		coins.removeAll(coinsToRemove);
		objects.removeAll(coinsToRemove);

		objects.removeAll(enemiesToRemove);
		enemies.removeAll(enemiesToRemove);
		for (GameObject obj : objects) {
			obj.tick();
		}

	}

	private File nextLevel() {
		currentLevel += 1;

		return getLevel();
	}

	private File getLevel() {

		File nextFile = null;
		boolean levelExists = false;
		while (currentLevel <= lastLevel) {
			nextFile = new File("lib", "Level" + currentLevel + ".txt");
			levelExists = nextFile.exists();
			if (levelExists) {
				break;
			} else {
				currentLevel += 1;
			}
		}
		// TODO Auto-generated method stub
		if (levelExists) {
			return nextFile;
		} else {
			return null;
		}
	}

	private static float min(float a, float b) {
		return a < b ? a : b;
	}

	private static float max(float a, float b) {
		return a > b ? a : b;
	}

	public static void main(String args[]) {

		Game game = new Game();

		switch (args.length) {
		case (0):
			break;
		case (1):
			game.currentLevel = Integer.parseInt(args[0]);
			break;
		case (2):
			game.currentLevel = Integer.parseInt(args[0]);
			game.lastLevel = Integer.parseInt(args[1]);
			break;
		default:
			game.currentLevel = Integer.parseInt(args[0]);
			game.lastLevel = Integer.parseInt(args[1]);
			break;
		}
		game.loadLevel(game.getLevel());
		game.startRunning();
	}

}
