package SnakeGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener{

	
	int maxDots = 1600;
	int dotSize = 10;
	int dots;
	int x[] = new int[maxDots];
	int y[] = new int[maxDots];
	
	// initialize apple coordinates
	int appleX,appleY; 
	
	// Initialize images
	Image head,body,apple;
	
	Timer timer;
	int delay = 150;
	
	// initialize direction
	boolean leftDirection=true;
	boolean rightDirection=false;
	boolean upDirection=false;
	boolean downDirection=false;
	
	
	boolean game = true; 

	
	Board(){
		TAdapter tAdapter = new TAdapter();
		addKeyListener(tAdapter);
		setFocusable(true);
		setPreferredSize(new Dimension(400,400));
		setBackground(Color.BLACK);
		setFocusable(true);
		intGame();
		loadImages();
	}

	// Initialize game
	public void intGame() {
		dots = 3;
		//Initialize snake position
		x[0] = 250;
		y[0] = 250;
		for(int i=1;i<dots;i++) {
			x[i] = x[0]+dotSize*i;
			y[i] = y[0];
		}
		// apple position
		locateapple();
		
		timer = new Timer(delay,this);
		timer.start();
	}
	
	// load images from resources folder to image object 
	public void loadImages() {
		
		ImageIcon headicon = new ImageIcon("src/SnakeGame/resource/head.png");
		head = headicon.getImage();
		
		ImageIcon bodyicon = new ImageIcon("src/SnakeGame/resource/dot.png");
		body = bodyicon.getImage();
		
		ImageIcon appleicon = new ImageIcon("src/SnakeGame/resource/apple.png");
		apple = appleicon.getImage();
	}
	
	
	//draw images at snakes and apple's position
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		doDrawing(g);
	}
	//draw image
	public void doDrawing(Graphics g) {
		if(game) {
		g.drawImage(apple, appleX, appleY, this);
		for(int i=0;i<dots;i++) {
			if(i==0) 
				g.drawImage(head, x[i], y[i], this);
			else
				g.drawImage(body, x[i], y[i], this);
		}
	}
		else {
			timer.stop();
			gameOver(g);
	}
	}
	// randomize apple position
	public void locateapple() {
		appleX = ((int)(Math.random()*39))*dotSize;
		appleY = ((int)(Math.random()*39))*dotSize;
	}
	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		
		if(game) {
			checkapple();
			collisoncheck();
			move();
		}
		
		repaint();
	}
	// make snake move
	public void move() {
		for(int i=dots-1;i>0;i--) {
			x[i]=x[i-1];
			y[i] = y[i-1];
		}
		if(leftDirection) {
			x[0]-=dotSize;
		}
		if(rightDirection) {
			x[0]+=dotSize;
		}
		if(upDirection) {
			y[0]-=dotSize;
		}
		if(downDirection) {
			y[0]+=dotSize;
		}
	}
	
	//Check Collisions with Border and body
	public void collisoncheck(){
		//Collisions with Body
		for(int i=1;i<dots;i++) {
			if(i>4 && x[0]==x[i] && y[0]==y[i])
				game = false;
		}
		//Collisions with Border
		if(x[0]<0 || y[0]<0 || x[0]>=400 || y[0]>=400)
			game = false;
	}
	// make snake eat apple 
	public void checkapple() {
		if(appleX==x[0] && appleY==y[0]) {
			dots++;
			locateapple();
		}
	}
	 //Display Game over 
	   public void gameOver(Graphics g){
	        String msg = "Game Over";
	        int score = (dots-3)*100;
	        String scoremsg = "Score:"+Integer.toString(score);
	        Font small = new Font("Helvetica", Font.BOLD, 20);
	        FontMetrics fontMetrics = getFontMetrics(small);

	        g.setColor(Color.WHITE);
	        g.setFont(small);
	        g.drawString(msg, 150 , 175);
	        g.drawString(scoremsg,150 ,215);
	    }
	   //Implement Controls
	private class TAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent keyEvent) {
			int key  = keyEvent.getKeyCode();
			if(key==keyEvent.VK_LEFT && !rightDirection) {
				leftDirection = true;
				upDirection = false;
				downDirection = false;
				
				
			}
			if(key==keyEvent.VK_RIGHT && !leftDirection) {
				rightDirection = true;
				upDirection = false;
				downDirection = false;
			}
			if(key==keyEvent.VK_UP&& !downDirection) {
				leftDirection = false;
				rightDirection = false;
				upDirection = true;
				
			}
			if(key==keyEvent.VK_DOWN && !upDirection) {
				leftDirection = false;
				rightDirection = false;
				downDirection = true;
			}
			
				
			
		}
		
		
	}
	
}

