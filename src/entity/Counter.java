package entity;

import java.util.ArrayList;

import Logic.Direction;//
import Logic.GameLogic;
import gui.Ladder;
import gui.Snake;
import gui.myBoard;

public abstract class Counter {
	private int xPos;
	private int yPos;
	private int boardIndex;
	private Direction direction;
	private int expiredTurn;

	// Initial Constructor
	public Counter() {
		this.setxPos(0);
		this.setyPos(8);
		this.setBoardIndex(1); // set start index to 1
		this.setDirection(Direction.Forward);
		this.expiredTurn = -1;
	}

	// Constructor
	public Counter(int xPos, int yPos) {
		this.setxPos(xPos);
		this.setyPos(yPos);
		this.setBoardIndex(Logic.GameLogic.changePosToIndex(xPos, yPos));
		this.setDirection(Direction.Forward);
	}

	public int getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
		if (xPos < 0)
			this.xPos = 0;
		if (xPos > 8)
			this.xPos = 8;
	}

	public int getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
		if (yPos < 0)
			this.yPos = 0;
		if (yPos > 8)
			this.yPos = 8;
	}

	public int getBoardIndex() {
		return boardIndex;
	}

	public void setBoardIndex(int boardIndex) {
		if(boardIndex > 81) {
			boardIndex = 81;
		}
		if(boardIndex < 1) {
			boardIndex = 1;
		}
		this.boardIndex = boardIndex;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	// Method
	public abstract BaseCounter roll();

	public void setExpiredTurn(int t) {
		this.expiredTurn = t;
	}

	public Counter CheckAction() {
		// TODO check that new Tile is a snake or ladder or card
		// ...
		ArrayList<Snake> snakeList = gui.myBoard.getSnakelist();
		ArrayList<Ladder> ladderList = gui.myBoard.getLadderlist();
		if ((myBoard.isPlayer1Playing() && !(GameLogic.getPlayer1Counter() instanceof NeverGobackCounter))
				|| (!myBoard.isPlayer1Playing() && !(GameLogic.getPlayer2Counter() instanceof NeverGobackCounter)))
			for (Snake snake : snakeList) {
				if (snake.getHeadIndex() == this.boardIndex) {
					this.boardIndex = snake.getTailIndex();
					ArrayList<Integer> Pos = GameLogic.changeIndextoPos(this.boardIndex);
					this.xPos = Pos.get(0);
					this.yPos = Pos.get(1);
				}
			}
		for (Ladder ladder : ladderList) {
			if (ladder.getLowerIndex() == this.boardIndex) {
				this.boardIndex = ladder.getUpperIndex();
				ArrayList<Integer> Pos = GameLogic.changeIndextoPos(this.boardIndex);
				this.xPos = Pos.get(0);
				this.yPos = Pos.get(1);
			}
		}
		ArrayList<Integer> cardList = myBoard.getSpecialIndex();
		boolean isInCardRoom = false;
		for (int i : cardList) {
			if (i == this.getBoardIndex()) {
				isInCardRoom = true;
			}
		}
		if (isInCardRoom) {
			GameLogic.randomCard();
		}
		if (expiredTurn != -1) {
			expiredTurn -= 1;
			if (expiredTurn == 0) {
				
				return new BaseCounter(this.getxPos(), this.getyPos());
			}
		}
//		System.out.println("---------------------------print from checkAction");
//		System.out.println("Player 1 counter ->" + GameLogic.getPlayer1Counter() + "for "
//				+ GameLogic.getPlayer1Counter().expiredTurn);
//		System.out.println("Player 2 counter ->" + GameLogic.getPlayer2Counter() + "for "
//				+ GameLogic.getPlayer2Counter().expiredTurn);

		return null;
	}

}
