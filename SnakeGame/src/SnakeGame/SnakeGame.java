package SnakeGame;

import javax.swing.JFrame;


public class SnakeGame extends JFrame {
	// initialize board object
	Board board;
	
	SnakeGame(){
		board = new Board();
		add(board);
		pack();
		setResizable(false);
		setVisible(true);
	}
	
	
	public static void main(String[] args) {
	
		SnakeGame snakeGame = new SnakeGame();
		
	}
}

