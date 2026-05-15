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

	public GameScreen(String powerupFromSpinny) {

		powerup = powerupFromSpinny;

		applyPowerup();

		setPreferredSize(new Dimension(900, 650));
		setFocusable(true);
		addKeyListener(this);

		timer = new Timer(16, this); // like 60 FPS
		timer.start();
	}
 public void applyPowerup() {

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
		spawnBossesBullets();
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


// boss spawn and directions


public void updateBoss() {

	bossY += bossDirection * 3;

	if (bossY < 80) {
		bossDirection = 1;
	}

	if (bossY > 390) {
		bossDirection = -1;
	}
}

public void spawnBossesBullets() {

	frameCount++;

	// boss shoots lef
	if (frameCount % 35 == 0) {
	enemyBullets.add(new Bullet(bossX, bossY + 80, -7, 0, "BOSS"));
	}

// bullets from topbulle
if (frameCount % 45 == 0) {
int randomX = rand.nextInt(850);
enemyBullets.add(new Bullet(randomX, 0, 0, 6, "TOP"));
}

// bullets coming up from ground like cuphead

if (frameCount % 70 == 0) {
	int randomX = rand.nextInt(850);
	enemyBullets.add(new Bullet(randomX, 650, 0, -7, "GROUND"));
}
}

public void updateBullets() {

	// enemy bullets
	for (int i = 0; i < enemyBullets.size(); i++) {

		Bullet b = enemyBullets.get(i);

		b.x += b.dx;
		b.y += b.dy;

		if (b.x < -50 || b.x > 950 || b.y < -50 || b.y > 700) {
			enemyBullets.remove(i);
			i--;
		}
	}

	// player bullets
	for (int i = 0; i < playerBullets.size(); i++) {

		Bullet b = playerBullets.get(i);

		b.x += b.dx;
		b.y += b.dy;

		if (b.x > 950) {
			playerBullets.remove(i);
			i--;
		}
	}
}

public void isColliding() {

	Rectangle playerB = new Rectangle(playerX, playerY, 55, 70);
	Rectangle bossB= new Rectangle(bossX, bossY, 160, 170);

	// enemy bullet hits playerY
	for (int i = 0; i < enemyBullets.size(); i++) {

		Bullet b = enemyBullets.get(i);

		Rectangle bulletBox = new Rectangle(b.x, b.y, 30, 30);

		if (playerB.intersects(bulletBox)) {

			enemyBullets.remove(i);
			i--;

			if (sheild == true) {
				sheild = false;
			}
			else {
				playerHealth -= 15;
			}

			if (playerHealth <= 0) {

				if (secondLife == true) {
					secondLife = false;
					playerHealth = 75;
				}
				else {
					gameOver = true;
					playerWon = false;
				}
			}
		}
	}

	// player bullet hits bossss
	for (int i = 0; i < playerBullets.size(); i++) {

		Bullet b = playerBullets.get(i);

		Rectangle bulletBox = new Rectangle(b.x, b.y, 25, 15);

		if (bossB.intersects(bulletBox)) {

			playerBullets.remove(i);
			i--;

			bossHealth -= 10;

			if (bossHealth <= 0) {
				gameOver = true;
				playerWon = true;
			}
		}
	}
}

public void updateAnimation() {

	if (frameCount % 20 == 0) {

		if (pFrame == 0) {
			pFrame = 1;
		}
		else {
			pFrame = 0;
		}

		if (bFrame == 0) {
			bFrame = 1;
		}
		else {
			bFrame = 0;
		}
	}
}

