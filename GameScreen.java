import javax.swing.*;
import java.awt.*;
import java awt.event.*;
import java.util.ArrayList;
import java util.Random;

public class GameScren extends JPanel implements KeyListener, ActionListener{

	// https://docs.oracle.com/javase/8/docs/api/java/awt/event/KeyListener.html
	// https://docs.oracle.com/javase/8/docs/api/java/awt/event/ActionListener.html


	// sprites for loading
	Image background = new ImageIcon("background.png").getImage();
	Image playerSprite1 = new ImageIcon("player1.png").getImage();
	Image playerSprite2 = new ImageIcon("player2.png").getImage();
	Image bossSprite1 = new ImageIcon("kingdice1.png").getImage();
	Image bossSprite2 = new ImageIcon("kingdice2.png").getImage();
	Image bossBulletSprite = new ImageIcon("bossbullet.png").getImage();
	Image topBulletSprite = new ImageIcon("topbullet.png").getImage();
	Image groundBulletSprite = new ImageIcon("groundbullet.png").getImage();


	// player spawn and declerations

	int PlayerX = 120;
	int PlayerY = 430;

	int playerHealth = 100;
	int playerSpeed = 5;

	boolean up;
	boolean down;
	boolean left;
	boolean right;

	int pFrame = 0;

	// boss stuff

	int bossX = 620;
	int bossY = 220;

	int bossHealth = 300;
	int bossDir = 1;
	int bFrame = 0;

	// powerups if applicable

	String power = "None";

	boolean hasSecond = false;
	boolean hasSheild = false;


	// bullet sprites
	ArrayList<Bullet> enemyBullets = new ArrayList<Bullet>();
	ArrayList<Bullet> playerBullets = new ArrayList<Bullet>();

	Random rand = new Random();

	Timer timer;

	int frameCount = 0;

	boolean gameOver = false;
	boolean playerWon = false;

	public GameScreen(String powerupFromMenu) {

		powerup = powerupFromMenu;

		applyPowerup();

		setPreferredSize(new Dimension(900, 650));
		setFocusable(true);
		addKeyListener(this);

		timer = new Timer(16, this); // like 60 FPS
		timer.start();
	}
 void applyPowerup() {

	if (powerup.contains("Faster Speed")) {
		playerSpeed = 8;
	}

	if (powerup.contains("More Health")) {
		playerHealth = 150;
	}

	if (powerup.contains("Sheild")) {
		shield = true;
	}

	if (powerup.contains("Second Life")) {
		secondLife = true;
	}
}

public void actionPerformed(ActionEvent e) {

	if (gameOver == false) {

		updatePlayer();
		updateBoss();
		spawnEnemyBullets();
		updateBullets();
		isColliding();
		updateAnimation();
	}

	repaint();
}

public void updatePlayer() {
	// test this debug 
	if (up) {
		playerY -= playerSpeed;
	}

	if (down) {
		playerY += playerSpeed;
	}

	if (left) {
		playerX -= playerSpeed;
	}

	if (right) {
		playerX += playerSpeed;
	}
// bound
	if (playerX < 0) {
	playerX = 0;
	}

if (playerX > 840) {
	playerX = 840;
}

if (playerY < 0) {
	playerY = 0;
}

if (playerY > 570) {
	playerY = 570;
}
}


