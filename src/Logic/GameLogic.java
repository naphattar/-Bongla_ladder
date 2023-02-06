package Logic;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

import entity.BaseCounter;
import entity.Counter;
import entity.DiagonalCounter;
import entity.NeverGobackCounter;
import entity.card.BaseCard;
import gui.Ladder;
import gui.Snake;
import gui.myBoard;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
//
import javafx.scene.media.AudioClip;
public class GameLogic {
	static Counter player1counter = new DiagonalCounter(0,8), player2counter = new BaseCounter(0,8);

	public static AudioClip BGSong = new AudioClip(ClassLoader.getSystemResource("sound/bgmusic.wav").toString());
	public static AudioClip StartGameSound = new AudioClip(ClassLoader.getSystemResource("sound/startGame.wav").toString());
	
	private static int tempNumber;
	private static boolean isGameEnd;
	public static boolean isPlayer1Win;

	public static int Randomnum() {
		Random r = new Random();
		int tempNumber =  r.nextInt(6) + 1;
		return tempNumber;
	}

	public static int changePosToIndex(int xPos, int yPos) {
		// Change x,y position to BoardIndex
		yPos = 8 - yPos;
		if(yPos%2 == 0) {
			return (9*yPos+xPos+1);
		}
		return (9*yPos+9-xPos);
	}

	public static ArrayList<Integer> changeIndextoPos(int boardIndex) {
		// Change BoardIndex to x and y
		ArrayList<Integer> Position = new ArrayList<Integer>();
		int x = boardIndex % 9;
		if(x == 0) {
			x =9;
		}
		int y = (boardIndex -x)/9;
		y = 8-y;
		if(y%2 == 0) {
			x = x-1;
		}else {
			x = 9-x;
		}
		Position.add(x);
		Position.add(y);
		return Position;
	}
	
	public static void randomCard() {
		ArrayList<BaseCard> cards = myBoard.getCardlist();
		Random random = new Random();
		BaseCard card = cards.get(random.nextInt(cards.size()));

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("You got a card.");
		alert.setHeaderText(null);
		alert.setContentText("You got "+card.getName()+" Card. Do you accept the card?");
		alert.getButtonTypes().clear();
		
		ButtonType btn1 = new ButtonType("Accept");
		ButtonType btn2 = new ButtonType("Decline");
		alert.getButtonTypes().addAll(btn1,btn2);
		
		Optional<ButtonType> option = alert.showAndWait();
		int rollNumber = 0;
		if(option.get() == btn1) {
			if(myBoard.isPlayer1Playing()) {
				card.cardAction(player1counter);
				ArrayList<Snake> snakeList = gui.myBoard.getSnakelist();
				ArrayList<Ladder> ladderList = gui.myBoard.getLadderlist();
				if (!(GameLogic.getPlayer1Counter() instanceof NeverGobackCounter))
					for (Snake snake : snakeList) {
						if (snake.getHeadIndex() == GameLogic.getPlayer1Counter().getBoardIndex()) {
							GameLogic.getPlayer1Counter().setBoardIndex(snake.getTailIndex());
							ArrayList<Integer> Pos = GameLogic.changeIndextoPos(snake.getTailIndex());
							GameLogic.getPlayer1Counter().setxPos(Pos.get(0));
							GameLogic.getPlayer1Counter().setxPos(Pos.get(1));
						}
					}
				for (Ladder ladder : ladderList) {
					if (ladder.getLowerIndex() == GameLogic.getPlayer1Counter().getBoardIndex()) {
						GameLogic.getPlayer1Counter().setBoardIndex(ladder.getUpperIndex());
						ArrayList<Integer> Pos = GameLogic.changeIndextoPos(ladder.getUpperIndex());
						GameLogic.getPlayer1Counter().setxPos(Pos.get(0));
						GameLogic.getPlayer1Counter().setxPos(Pos.get(1));
					}
				}
			}else {
				card.cardAction(player2counter);
				ArrayList<Snake> snakeList = gui.myBoard.getSnakelist();
				ArrayList<Ladder> ladderList = gui.myBoard.getLadderlist();
				if (!(GameLogic.getPlayer2Counter() instanceof NeverGobackCounter))
					for (Snake snake : snakeList) {
						if (snake.getHeadIndex() == GameLogic.getPlayer2Counter().getBoardIndex()) {
							GameLogic.getPlayer2Counter().setBoardIndex(snake.getTailIndex());
							ArrayList<Integer> Pos = GameLogic.changeIndextoPos(snake.getTailIndex());
							GameLogic.getPlayer2Counter().setxPos(Pos.get(0));
							GameLogic.getPlayer2Counter().setxPos(Pos.get(1));
						}
					}
				for (Ladder ladder : ladderList) {
					if (ladder.getLowerIndex() == GameLogic.getPlayer2Counter().getBoardIndex()) {
						GameLogic.getPlayer2Counter().setBoardIndex(ladder.getUpperIndex());
						ArrayList<Integer> Pos = GameLogic.changeIndextoPos(ladder.getUpperIndex());
						GameLogic.getPlayer2Counter().setxPos(Pos.get(0));
						GameLogic.getPlayer2Counter().setxPos(Pos.get(1));
					}
				}
			}
			
		}else if(option.get() == btn2) {
			rollNumber = 2;
		}
	}

	public static void setPlayer1Counter(Counter t) {
		player1counter = t;
	}

	public static void setPlayer2Counter(Counter t) {
		player2counter = t;
	}

	public static Counter getPlayer1Counter() {
		return player1counter;
	}

	public static Counter getPlayer2Counter() {
		return player2counter;
	}

	public static void setTempNumber(int t) {
		tempNumber = t;
	}

	public static int gettempNumber() {
		return tempNumber;
	}

	public static void initializeGame() {
		isGameEnd = false;
	}
	public static void endgame(int i) {
		isGameEnd = true;
		myBoard.fromCard = true;
	}
	public static void endgame() {
		System.out.println("End Game!!!");
		isGameEnd = true;
	}
	public static boolean IsGameEnd() {
		return isGameEnd;
	}
	public static void setIsGameEnd(boolean b) {
		isGameEnd = b;
	}
}
