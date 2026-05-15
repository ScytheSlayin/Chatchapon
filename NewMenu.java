import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NewMenu extends JPanel implements KeyListener {

	String[] options = {"FIGHT", "SHOP", "CONTROLS", "QUIT"};

	int selected = 0;


	String screen = "MENU"; // starting pos


	// case sysmte -- add as sprites if have 22:36

	String[] spinItems = {
		"COMMON - Faster Speed",
		"COMMON - Faster Speed",
		"COMMON - Faster Speed",
		"RARE - More Health",
		"RARE - More Health",
		"EPIC - Sheild",
		"LEGENDARY - Second Life"
	};

	String spinText = "Press X to roll a case, may the RNG help you";

	boolean isSpin = false;

	int spinCount = 0;

	Timer spinTimer;

	Random rand = new Random();

	ArrayList<String> inv = new ArrayList<String>();

	String currentPower = "None";


	public NewMenu(){
		setPreferredSize(new Dimension(900, 650));
		setBackground(Color.BLACK);
		setFocusable(true);
		addKeyListener(this);
	}


	// https://docs.oracle.com/javase/8/docs/api/java/awt/Window.html

	
	public void paintComponent(Graphics menu){
		super.paintComponent(menu);
		// https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/java/awt/Color.html

		// background 
		menu.setColor(new Color(20, 20, 30));
		menu.fillRect(0,0, getWidth(), getHeight());

		if(screen.equals("MENU")){
			drawMenu(menu);
		}
		if(screen.equals("CONTROLS")){
			drawControls(menu);
		}
		if(screen.equals("SHOP")){
			drawShop(menu);
		}
		if(screen.equals("FIGHT")){
			drawFight(menu);
		}
	}

		// title or splash screen
		// https://docs.oracle.com/javase/8/docs/api/java/awt/Font.html

	public void drawMenu(Graphics menu){
		menu.setColor(Color.WHITE);
		menu.setFont(new Font("Serif", Font.BOLD, 55));
		menu.drawString("Cuphead 41", 315, 120);

		// menu options
		menu.setFont(new Font("Arial", Font.BOLD, 30));

		// arrow smoothing

		for(int i = 0; i < options.length; i++){
			int y = 270 + i * 55;

			if(i == selected){
				menu.setColor(Color.BLUE);
				menu.fillRoundRect(300, y -35, 300, 45, 20, 20);

				menu.setColor(Color.BLACK);
				menu.drawString("> " + options[i], 370, y);
			}
			else{
				menu.setColor(Color.WHITE);
				menu.drawString(options[i], 405, y);
			}
		}

		// instructions

		menu.setColor(Color.LIGHT_GRAY);
		menu.setFont(new Font("Arial", Font.PLAIN, 18));
		menu.drawString("W/S | ENTER to select", 225, 560);
	}

public void drawControls(Graphics menu){
	menu.SetColor(Color.WHITE);
	menu.setFont(new Font("Serif", Font.BOLD, 50));
	menu.drawString("Controls", 340, 100);

	menu.setFont(new Font("Arial", Font.PLAIN, 26));


	// conbtrols
	menu.drawString("WASD - Movement", 250, 190);
	menu.drawString("X - Spin cases", 250, 240);
	menu.drawString("I - Use powerup", 250, 290);
	menu.drawString("T - Open chat", 250, 390);
	menu.drawString("ENTER - Select", 250, 390);
	menu.drawString("ESC - Back",250,440);

	menu.setColor(Color.LIGHT_GRAY);
	menu.setFont(new Font ("Arial", Font.PLAIN, 18));
	menu.drawString("Press ESC to go back", 360, 560);
}
public void keyPressed(KeyEvent e){

	// https://docs.oracle.com/javase/8/docs/api/java/awt/event/KeyEvent.html

	int key = e.getKeyCode();

	if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP){

		selected--;

		if(selected <0){
			selected = options.length - 1;
		}
	}

	if(key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN){
		selected++;

		if(selected >= options.length){
			selected = 0;
		}
	}

	if(key == KeyEvent.VK_ENTER){

		if(options[selected].equals("FIGHT")){
			// enter later
			System.out.println("start");
		}
		if(options[selected].equals("SHOP")){
			System.out.println("shoppin");
		}
		if(options[selected].equals("CONTROLS")){
			System.out.println("cont");
		}
		if(options[selected].equals("QUIT")){
			System.exit(0);
		}
	}

	repaint();
}

// https://docs.oracle.com/javase/8/docs/api/java/awt/event/KeyListener.html

public void keyReleased(KeyEvent e){
}

public void keyTyped(KeyEvent e){
}

public static void main(String[] args){

	JFrame window = new JFrame("Cuphead 41");


	NewMenu menuadd = new NewMenu();

	window.add(menuadd);
	window.pack();

	window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	window.setLocationRelativeTo(null);
	window.setVisible(true);

	menuadd.requestFocusInWindow();
}
}
