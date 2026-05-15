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

	public void backgroundSet(Graphics menu){
		super.backgroundSet(menu)
