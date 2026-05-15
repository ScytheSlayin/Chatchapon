import java.io.*;
import java.net.*;
import java.util.Scanner;

public class GameClient {
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;

	public void connect(String username) {
		try {
			socket = new Socket("localhost", 12345);
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			// Prints out username to server 
			out.println(username);
		
			new Thread(this::listenForPackets).start();

			Scanner scanner = new Scanner(System.in);
			System.out.println("Logged in as " + username + ". Type 'MSG:your message' to chat:");

			while (true) {
				String input = scanner.nextLine();
				out.println(input);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void listenForPackets() {
		try {
			String line;
			while ((line = in.readLine()) != null) {
				System.out.println("\n[SERVER]: " + line);
			}
			System.out.println("\nDisconnected from server: Server shut down.");
		} catch (IOException e) {
			System.out.println("Connection to server lost.");
		} 
	}

	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Usage: java GameClient <username>");
			return;
		}
		new GameClient().connect(args[0]);
	}
}
