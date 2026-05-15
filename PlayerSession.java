import java.io.*;
import java.net.*;

public class PlayerSession implements Runnable {
	public int id;
	public String username;
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	private GameServer server;

	public PlayerSession(Socket s, int id, GameServer server) {
		this.socket = s;
		this.id = id;
		this.server = server;
	}

	@Override
	public void run() {
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			// Get username
			this.username = in.readLine();
			if (this.username != null) {
				System.out.println("Player authenticated: " + this.username + " (ID: " + id + ")");
				server.addPlayer(this);
			}

			String line;
			while ((line = in.readLine()) != null) {
				if (line.startsWith("MSG:")) {
					String chatContent = line.substring(4);
					server.sendChat(username + ": " + line.substring(4));
				}
			}
		} catch (IOException e) {
			System.out.println(username + " disconnected.");
		}
	}

	public void sendTCP(String data) { out.println(data); }
}
