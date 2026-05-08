import java.awt.Rectangle;

public class Player {
	public int id;
	public String username;
	public float x, y;
	public int width = 50, height = 50;

	public Player(int id, String username, float x, float y) {
		this.id = id;
		this.username = username;
		this.x = x;
		this.y = y;
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, width, height);
	}
}
