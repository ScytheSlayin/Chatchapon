import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NewMenu extends JPanel implements KeyListener {

	String[] options = {"FIGHT", "SHOP", "CONTROLS", "QUIT"};

	int selected = 0;

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

		// title or splash screen
		// https://docs.oracle.com/javase/8/docs/api/java/awt/Font.html

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
