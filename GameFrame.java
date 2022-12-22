import javax.swing.JFrame;

public class GameFrame extends JFrame
{
	GameFrame()
	{
		GamePanel panel = new GamePanel();
		this.add(panel);
		this.setTitle("Snake Game");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		// Fits JFrame around all components.
		this.pack();
		this.setVisible(true);
		// Sets frame location to middle of computer
		this.setLocationRelativeTo(null);
		
		
	}
}
