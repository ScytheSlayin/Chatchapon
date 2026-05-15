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
