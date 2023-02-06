package entity;

import java.util.ArrayList;

import Logic.GameLogic;
import gui.Snake;
import gui.myBoard;

//
public class NeverGobackCounter extends Counter {

	public NeverGobackCounter() {
		super();
	}
	
	public NeverGobackCounter(int xPos, int yPos) {
		super(xPos, yPos);
	}
	
	public BaseCounter roll() {
		//-------------------------------------//
		
			//This is for testing only
			//Random r = new Random();
			//int diceNumber =  r.nextInt(6) + 1;
		//-------------------------------------//
		int diceNumber = Logic.GameLogic.gettempNumber();
		int boardIndex = this.getBoardIndex();
		boardIndex = boardIndex+diceNumber;
		if(boardIndex >= 81) {
			// TODO Player Win
			GameLogic.endgame();
		}else {
			ArrayList<Integer> newPos = Logic.GameLogic.changeIndextoPos(boardIndex);
			this.setxPos(newPos.get(0));
			this.setyPos(newPos.get(1));
			this.setBoardIndex(boardIndex);
		}
		ArrayList<Snake> snakeList = myBoard.getSnakelist();
		boolean isGoOnSnake = false;
		for (Snake snake : snakeList) {
			if(snake.getHeadIndex() == this.getBoardIndex()) {
				isGoOnSnake = true;
				break;
			}
		}
		return null;
	}
	

}
