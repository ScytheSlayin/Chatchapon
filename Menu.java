// Use if implementing sprites and X11 fowarding with XLaunc, terminal logic for now cause this is confusing

// import javax.swing.*;
// import java.awt.*;

// https://docs.oracle.com/javase/8/docs/api/javax/swing/JFrame.html
// https://www.geeksforgeeks.org/java/java-jframe/

import java.util.Scanner;

public class Menu{

	public static void main(String[] args){

		Scanner input = new Scanner(System.in);

		String[] options = {"FIGHT", "SHOP", "CONTROLS", "QUIT"};

		int selected = 0;

		// use for frame switching later
		boolean isRunning = true;

		while(isRunning){


			System.out.flush();

			System.out.println("========================================");
			System.out.println("|    Cuphead 41    |");
			//System.out.println("========================================");

			// make arrow on user selection

			for(int i = 0; i < options.length; i++){
				if(i == selected){
					System.out.println("|            > [ " + options[i] + " ]             |");
				}
				else{
					System.out.println("|              [ " + options[i] + " ]             |");
				}
			}


			// bottom half

			System.out.println("|                                      |");
			System.out.println("========================================");
			System.out.println();
			System.out.println("     W/S for Up & Down | E to Select    ");
			
			String choice = input.nextLine();

			




		}

	}
}
