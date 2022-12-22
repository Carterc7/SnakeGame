import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.util.Random;


public class GamePanel extends JPanel implements ActionListener
{
	// Setting dimensions of game panel
	static final int screen_width = 600;
	static final int screen_height = 600;
	static final int unit_size = 25;
	static final int game_units = (screen_width*screen_height)/unit_size;
	static final int delay = 110;
	// holds x coordinates of snake body
	final int[] x = new int[game_units];
	// holds y coordinates of snake body
	final int[] y = new int[game_units];
	int bodyParts = 6;
	// counter of apples eaten
	int applesEaten = 0;
	int appleX;
	int appleY;
	// R = right L = left U = up D = down
	char direction = 'R';
	boolean running = false;
	Timer timer;
	Random random;
	
	GamePanel()
	{
		random = new Random();
		this.setPreferredSize(new Dimension(screen_width,screen_height));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new myKeyAdapter());
		startGame();
		
		
		
	}
	public void startGame()
	{
		addApple();
		running = true;
	    timer = new Timer(delay,this);
		timer.start();
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		draw(g);
	}
	public void draw(Graphics g)
	{
		if(running) 
		{
		
		g.setColor(Color.red);
		g.fillOval(appleX, appleY, unit_size, unit_size);
		
		for(int i = 0; i < bodyParts; i++)
		{
			if(i == 0)
			{
				g.setColor(Color.green);
				g.fillRect(x[i], y[i], unit_size, unit_size);
			}
			else
			{
				g.setColor(new Color(45,180,0));
				g.fillRect(x[i], y[i], unit_size, unit_size);
			}
		}
		}
		else
		{
			gameOver(g);
		}
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free",Font.BOLD, 40));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Score: " + applesEaten, (screen_width - metrics.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());
	}
	public void addApple()
	{
		appleX = random.nextInt((int)(screen_width/unit_size))*unit_size;
		appleY = random.nextInt((int)(screen_height/unit_size))*unit_size;
	}
	public void move()
	{
		for(int i = bodyParts; i > 0; i--)
		{
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		switch(direction)
		{
		case 'U':
			y[0] = y[0] - unit_size;
			break;
		case 'D':
			y[0] = y[0] + unit_size;
			break;
		case 'L':
			x[0] = x[0] - unit_size;
			break;
		case 'R':
			x[0] = x[0] + unit_size;
			break;
		}
	}
	public void checkApple()
	{
		if((x[0] == appleX)&&(y[0] == appleY))
		{
			bodyParts++;
			applesEaten++;
			addApple();
		}
	}
	public void checkCollisions()
	{
		// checks if head collides with body
		for(int i = bodyParts; i > 0; i--)
		{
			if((x[0] == x[i]&& y[0] == y[i]))
			{
				running = false;
			}
		}
		// checks if head collides with left-border
		if(x[0] < 0)
		{
			running = false;
		}
		// checks if head collides with right-border
		if(x[0] > screen_width)
		{
			running = false;
		}
		// checks if head touches top-border
		if(y[0] < 0)
		{
			running = false;
		}
		// checks if head touches bottom-border
		if(y[0] > screen_height)
		{
			running = false;
		}
		if(!running)
		{
			timer.stop();
		}
		
	}
	public void gameOver(Graphics g)
	{
		// Score Text
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free",Font.BOLD, 40));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Score: " + applesEaten, (screen_width - metrics1.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());
		// Game Over Text
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free",Font.BOLD, 75));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Game Over", (screen_width - metrics2.stringWidth("Game Over"))/2, screen_height/2);
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(running)
		{
			move();
			checkApple();
			checkCollisions();
		}
		repaint();
	}
	public class myKeyAdapter extends KeyAdapter
	{
		@Override
		public void keyPressed(KeyEvent e)
		{
			switch(e.getKeyCode())
			{
			case KeyEvent.VK_LEFT:
				if(direction != 'R')
				{
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'L')
				{
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction != 'D')
				{
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction != 'U')
				{
					direction = 'D';
				}
				break;
			}
		}
	}
	
}
