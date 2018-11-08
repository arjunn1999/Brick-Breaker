import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;
import javax.swing.JPanel;
//Main class for gameplay
public class Gameplay extends JPanel  implements KeyListener,ActionListener{
	private boolean play = false;
	private int  score = 0;
	private int totalBricks = 21;
	private Timer timer;
	private int delay = 5;
	private int playerX = 310;
	private int ballposX = 120;
	private int ballposY = 350;
	private int ballXdir = -1;
	private int ballYdir = -2;
	private MapGenerator map; 
	
	public Gameplay()
	{
		map = new MapGenerator(3,7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay,this);
		timer.start(); 
	}
	public void paint(Graphics g)
	{
		//background
		g.setColor(Color.black);
		g.fillRect(1, 1,692,592);
		
		//drawing bricks
		map.draw((Graphics2D)g);
		//borders
		g.setColor(Color.YELLOW);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692,3);
		g.fillRect(682, 0, 3,592);
		//paddle
		g.setColor(Color.green); 
		g.fillRect(playerX,550,100,8);
		
		//ball
		g.setColor(Color.YELLOW); 
		g.fillOval(ballposX,ballposY,20,20);
		//score 
		g.setColor(Color.YELLOW);
		g.drawString(""+score, 590, 30);
		//
		if(totalBricks==0)
		{
			play=false;
			ballXdir = 0;
			ballYdir = 0; 
			g.setColor(Color.RED);
			g.setFont(new Font("serif",Font.BOLD,25));
			g.drawString("You Won",260,300);
			g.drawString("Press Enter to Restart",230,350);
		}
		if(ballposY>570) {
			play=false;
			ballXdir = 0;
			ballYdir = 0; 
			g.setColor(Color.RED);
			g.setFont(new Font("serif",Font.BOLD,25));
			g.drawString("Game over Score:"+score,190,300);
			g.drawString("Press Enter to Restart",230,350);
		}
		g.dispose();
	}
	
	
	
	@Override
	public void keyTyped(KeyEvent e){}
	
	@Override
	public void keyReleased(KeyEvent e){}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if(play)
		{
			/*if(new Rectangle(ballposX,ballposY,20,20).intersects(new Rectangle(playerX,550,100,8)))
			{
				ballYdir=-ballYdir;
			}*/
			for(int i=0;i<map.map.length;i++)
			{
				for(int j=0;j<map.map[0].length;j++)
				{
					if(map.map[i][j]>0)
					{
						int brickX = j*map.brickWidth+80;
						int brickY = i*map.brickHeight+50;
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight;
						Rectangle rect = new Rectangle(brickX,brickY,brickWidth,brickHeight);
						Rectangle ballRect = new Rectangle(ballposX,ballposY,20,20);
						if(ballRect.intersects(rect)) {
							map.setBrickValue(0, i,j);
							score+=5;
							totalBricks--;
							if(ballposX+19<=rect.x||ballposX+1>=rect.x+rect.width)
								ballXdir=-ballXdir;
							else
								ballYdir = -ballYdir;
						}
						
					}
				}
			}
			ballposX+=ballXdir;
			ballposY+=ballYdir;
			if(ballposX<0)
			{
				ballXdir=-ballXdir;
			}
			if(ballposY<0)
			{
				ballYdir=-ballYdir;
			}
			if(ballposX>670) {
				ballXdir=-ballXdir;
			}
			if(new Rectangle(ballposX,ballposY,20,20).intersects(new Rectangle(playerX,550,100,8)))
			{
				ballYdir=-ballYdir;
			}
		}
		repaint();
	}
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode()==KeyEvent.VK_RIGHT)
		{
			if(playerX>=592)
				playerX=600;
			else
				moveRight();
		}
		if(e.getKeyCode()==KeyEvent.VK_LEFT)
		{
			if(playerX<10)
				playerX=10; 
			else
				moveLeft();
		}
		if(e.getKeyCode()==KeyEvent.VK_ENTER)
		{
			if(!play) {
				play=true;
				ballposX=120;
				ballposY=350;
				ballXdir = -1;
				ballYdir = -2;
				playerX=310;
				score=0;
				totalBricks = 21;
				map = new MapGenerator(3,7);
				repaint();
			}
		}
	}
	
	
	public void moveRight() {
		play = true;
		playerX+=20;
	}
	
	
	public void moveLeft() {
		play = true;
		playerX-=20;
	}
	

}
