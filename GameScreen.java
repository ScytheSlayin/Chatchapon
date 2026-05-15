import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class GameScreen extends JPanel implements KeyListener, ActionListener {

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
	Image playerBulletSprite = new ImageIcon("playerbullet.png").getImage();

	// player spawn and declerations

	int playerX = 120;
	int playerY = 430;

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

	String powerup = "None";

	boolean secondLife = false;
	boolean sheild = false;


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
		sheild = true;
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

	bossY += bossDir * 3;

	if (bossY < 80) {
		bossDir = 1;
	}

	if (bossY > 390) {
		bossDir = -1;
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

// install endgame
public void paintComponent(Graphics game) {
	super.paintComponent(game);
	// draw backgroundgame.drawImage(background, 0, 0, 900, 650, null);
	if (gameOver == true) {
		drawEnding(game);
		// make love ending things
		return;
	}
	drawPlayer(game);
	drawBoss(game);
	drawBullets(game);
	drawUI(game);
}
public void drawPlayer(Graphics game) {
	if (pFrame == 0) {
		game.drawImage(playerSprite1, playerX, playerY, 70, 80, null);
	}
	else {
		game.drawImage(playerSprite2, playerX, playerY, 70, 80, null);
	}
}
public void drawBoss(Graphics game) {
	if (bFrame == 0) {
		game.drawImage(bossSprite1, bossX, bossY, 170, 180, null);
	}
	else {
		game.drawImage(bossSprite2, bossX, bossY, 170, 180, null);
	}
}
public void drawBullets(Graphics game) {
	for (Bullet b : enemyBullets) {
		if (b.type.equals("BOSS")) {
			game.drawImage(bossBulletSprite, b.x, b.y, 35, 35, null);
			game.setColor(Color.RED);
		}
		else if (b.type.equals("TOP")) {
			game.drawImage(topBulletSprite, b.x, b.y, 35, 35, null);
			game.setColor(Color.ORANGE); // holder since Image
		}
		else {
			game.drawImage(groundBulletSprite, b.x, b.y, 35, 35, null);
			game.setColor(Color.PINK);
		}
	}
	for (Bullet b : playerBullets) {
		game.drawImage(playerBulletSprite, b.x, b.y, 35, 25, null);
	}
}
public void drawUI(Graphics game) {
	game.setColor(Color.WHITE);
	game.setFont(new Font("Arial", Font.BOLD, 18));
	game.drawString("Player Health: " + playerHealth, 20, 30);
	game.drawString("Boss Health: " + bossHealth, 650, 30);
	game.drawString("Powerup: " + powerup, 20, 60);
	game.drawString("WASD = move | SPACE = shoot | ESC = quit", 270, 620);
}
public void drawEnding(Graphics game) {

	// love ending or sum

	game.setColor(Color.WHITE);
	game.setFont(new Font("Serif", Font.BOLD, 42));
	if (playerWon == true) {
		game.drawString("You Won, But at what cost?", 240, 120);
		game.setFont(new Font("Arial", Font.PLAIN, 22));
		game.drawString("After the battle, you asked King Dice on a date.", 190, 220);
		game.drawString("Since you won, he had no choice but to accept..", 160, 260);
		game.drawString("He said he thinks your cool, then you guys get married on the spot.", 145, 300);
		game.drawString("You guys lived happily ever after.", 130, 340);
	}
	else {
		game.drawString("Game Over! Try again!", 350, 120);
		game.setFont(new Font("Arial", Font.PLAIN, 22));
		game.drawString("Even after loosing, you asked King Dice on a date.", 255, 230);
		game.drawString("He laughed at your face", 210, 270);
		game.drawString("Maybe if you win…", 330, 310);
	}
	game.setFont(new Font("Arial", Font.PLAIN, 18));
	game.drawString("Press ESC to exit.", 375, 560);
}


public void keyPressed(KeyEvent e) {

	int key = e.getKeyCode();

	// backend controls

	if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
		up = true;
	}

	if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
		down = true;
	}

	if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
		left = true;
	}

	if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
		right = true;
	}


	// spawn bullet in front of player
	if (key == KeyEvent.VK_SPACE) {
	playerBullets.add(new Bullet(playerX + 65, playerY + 35, 10, 0, "PLAYER"));
	}

if (key == KeyEvent.VK_ESCAPE) {
	System.exit(0);
}
}

public void keyReleased(KeyEvent e) {

	int key = e.getKeyCode();

	if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
		up = false;
	}

	if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
		down = false;
	}
	
	if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
		left = false;
	}

	if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
		right = false;
	}
}

public void keyTyped(KeyEvent e) {
}

public static void main(String[] args) {

	JFrame window = new JFrame("Cuphead 41 Fight");

	// debug purpose to spawn with powerups
	// GameScreen game = new GameScreen("LEGENDARY - Second Life");

	window.add(game);
	window.pack();

	window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	window.setLocationRelativeTo(null);
	window.setVisible(true);

	game.requestFocusInWindow();
}
}

class Bullet {

	int x;
	int y;

	int dx;
	int dy;

	String type;

	public Bullet(int x, int y, int dx, int dy, String type) {

		this.x = x;
		this.y = y;

		this.dx = dx;
		this.dy = dy;

		this.type = type;
	}
} 

