
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class GameServer {
	private int tcpPort = 12345;
	private int udpPort = 12346;

	private java.util.Map<Integer, PlayerSession> players = new ConcurrentHashMap<>();
	private java.net.DatagramSocket udpSocket;

	public void start() {
		try {
			udpSocket = new java.net.DatagramSocket(udpPort);
			new Thread(this::listenUDP).start();

			ServerSocket serverSocket = new ServerSocket(tcpPort);
			System.out.println("Server running: TCP " + tcpPort + " | UDP " + udpPort);
			int idCounter = 1;
			while (true) {
				Socket clientSocket = serverSocket.accept();
				System.out.println("Connection received from: " + clientSocket.getInetAddress());
				PlayerSession session = new PlayerSession(clientSocket, idCounter++, this);
				new Thread(session).start();
			}
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
	}

	public void sendChat(String message) {
		//Server terminal print 
		System.out.println("[BROADCAST]: " + message);
		//Player/Client terminal print 
		for (PlayerSession p : players.values()) {
			p.sendTCP("CHAT:" + message);
		}
	}
	
	// UDP Packets for Movement and Projectiles, stuff that 
	// doesn't have to be loaded in order

	private void listenUDP() {
		byte[] buffer = new byte[1024];
		while (true) {
			try {
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				udpSocket.receive(packet);
			} catch (IOException e) { e.printStackTrace(); }
		}
	}

	public void addPlayer(PlayerSession p) { players.put(p.id, p); }

	public static void main(String[] args) { new GameServer().start(); }

}
