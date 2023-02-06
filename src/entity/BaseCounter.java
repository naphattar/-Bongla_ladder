package entity;
//
import java.util.ArrayList;
import java.util.Random;

import Logic.GameLogic;

public class BaseCounter extends Counter {
    
	public BaseCounter() {
		super();
		
	
	}
	public BaseCounter(int xPos, int yPos) {
		super(xPos, yPos);
		
	}

	
	@Override
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

		return null;
	}
	
	
	
	
	

}
