package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

import javax.naming.Context;

import Logic.Direction;
import Logic.GameLogic;
import entity.BackwardCounter;
import entity.BaseCounter;
import entity.Counter;
import entity.DiagonalCounter;
import entity.DoubleDiceCounter;
import entity.NeverGobackCounter;
import entity.card.BaseCard;
import entity.card.ForwardOrBackwardCard;
import entity.card.GoNextRoomCard;
import entity.card.GuessWhatCard;
import entity.card.RestartGameCard;
import entity.card.StealerCard;
import entity.card.TheNewCard;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class myBoard implements Initializable {

	private static int turn = -1;
	public static double startTime = System.nanoTime();
	private int lastDrawP1 = 1, lastDrawP2 = 1;
	private static ArrayList<Snake> snakelist;
	private static ArrayList<Ladder> ladderlist;
	private static ArrayList<BaseCard> cardlist;
	private int boardNumber;
	private final int[] board1snake = { 200, 190, 50, 30, 130, 650, 50, 300 };
	private final double[] board1degree = { 30, 0, -60, 10 };
	private final int[] boardladder = { 115, 100, 180, 370, 130, 530, 530, 0 };
	private final double[] boardladderdegree = { 0, 30, -30, 30 };
	private final int[] board2snake = { 180, 0, 365, 0, 30, 50, 200, 340 };
	private final double[] board2degree = { 50, -10, -40, -20 };
	private final int[] board1snakeIndex = { 7, 35, 43, 72, 12, 45, 52, 61 };
	private final int[] board2snakeIndex = { 5, 53, 9, 51, 72, 76, 50, 63 };
	private final int[] boardladderIndex = { 58, 75, 3, 33, 13, 32, 44, 64 };
	private static ArrayList<Integer> specialIndex; // keep all index which is a card
	private static ArrayList<Integer> usedIndex;
	public static boolean fromCard;
	public static boolean isreversefromCard;
	@FXML
	private Button rollButton;
	@FXML
	private Label numberLabel;
	@FXML
	private Button endButton;
	@FXML
	private HBox p1bg, p2bg;
	@FXML
	private Canvas boardCanvas,p1canvas,p2canvas;
	@FXML
	private Text P1name,P2name;
	
	public static ArrayList<Integer> getSpecialIndex() {
		return specialIndex;
	}

	public static ArrayList<Snake> getSnakelist() {
		return snakelist;
	}

	public void setSnakelist(ArrayList<Snake> snakelist) {
		this.snakelist = snakelist;
	}

	public static ArrayList<Ladder> getLadderlist() {
		return ladderlist;
	}

	public void setLadderlist(ArrayList<Ladder> ladderlist) {
		this.ladderlist = ladderlist;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		P1name.setText("P1 (Base Counter)");
		P2name.setText("P2 (Base Counter)");
		turn = -1;
		GameLogic.setTempNumber(0);
		GameLogic.setIsGameEnd(false);
		GraphicsContext a = boardCanvas.getGraphicsContext2D();
		// Draw an initial board
		drawinitialBoardOnCanvas(a);
		rollButton.setDisable(true);
		numberLabel.setText("-");

		// TODO draw an initialize board on Canva
		snakelist = new ArrayList<Snake>();
		ladderlist = new ArrayList<Ladder>();
		specialIndex = new ArrayList<Integer>();
		usedIndex = new ArrayList<Integer>();
		isreversefromCard = false;
		// random a board
		Random r = new Random();
		this.setBoardNumber(r.nextInt(2));

		// set snake for 4 and ladder for 4
		if (this.getBoardNumber() == 0) {
			for (int i = 0; i < 4; i++) {
				snakelist.add(new Snake(board1snakeIndex[i * 2 + 1], board1snakeIndex[i * 2]));
				ladderlist.add(new Ladder(boardladderIndex[i * 2], boardladderIndex[i * 2 + 1]));
			}
			for (int i = 0; i < 8; i++) {
				usedIndex.add(board1snakeIndex[i]);
				usedIndex.add(boardladderIndex[i]);
			}
		} else if (this.getBoardNumber() == 1) {
			for (int i = 0; i < 4; i++) {
				snakelist.add(new Snake(board2snakeIndex[i * 2 + 1], board2snakeIndex[i * 2]));
				ladderlist.add(new Ladder(boardladderIndex[i * 2], boardladderIndex[i * 2 + 1]));
			}
			for (int i = 0; i < 8; i++) {
				usedIndex.add(board2snakeIndex[i]);
				usedIndex.add(boardladderIndex[i]);
			}

		}
		// set the cardIndex for 9

		int cardIndex = 1;
		for (int i = 0; i < 9; i++) {
			if(i == 0) {
				cardIndex = 9 * i + (2 + r.nextInt(9));
				
			}else if(i == 8) {
				cardIndex = 9 * i + (1 + r.nextInt(8));
			}
			else {
				cardIndex = 9 * i + (1 + r.nextInt(9));
			}
			while (!checkExist(cardIndex)) {
				cardIndex += 1;
			}
			specialIndex.add(cardIndex);
		}
		// Draw card Index
		drawCard(a);
		// Draw a snake on board
		for (int i = 0; i < 4; i++) {
			drawSnake(a, i, this.getBoardNumber());
		}

		// Draw a ladder on board
		for (int i = 0; i < 4; i++) {
			drawLadder(a, i, this.getBoardNumber());
		}
		// set up cardList
		float rate1 = 0.16f;
		float rate2 = 0.16f;
		float rate3 = 0.16f;
		float rate4 = 0.16f;
		float rate5 = 0.16f;
		float rate6 = 0.1f;
		cardlist = new ArrayList<BaseCard>();
		cardlist.add(new ForwardOrBackwardCard(rate1));
		cardlist.add(new GoNextRoomCard(rate2));
		cardlist.add(new GuessWhatCard(rate3));
		cardlist.add(new RestartGameCard(rate4));
		cardlist.add(new StealerCard(rate5));
		cardlist.add(new TheNewCard(rate6));
		for (int i = 0; i < cardlist.get(0).getRatePercent() * 100; i++) {
			cardlist.add(new ForwardOrBackwardCard(rate1));
		}
		for (int i = 0; i < cardlist.get(1).getRatePercent() * 100; i++) {
			cardlist.add(new GoNextRoomCard(rate2));
		}
		for (int i = 0; i < cardlist.get(2).getRatePercent() * 100; i++) {
			cardlist.add(new GuessWhatCard(rate3));

		}
		for (int i = 0; i < cardlist.get(3).getRatePercent() * 100; i++) {
			cardlist.add(new RestartGameCard(rate4));
		}
		for (int i = 0; i < cardlist.get(4).getRatePercent() * 100; i++) {
			cardlist.add(new StealerCard(rate5));

		}
		for (int i = 0; i < cardlist.get(5).getRatePercent() * 100; i++) {
			cardlist.add(new TheNewCard(rate6));
		}
//		updateBoard(a);
		try {
			UpdateUtil();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// check position index return true if the index is not already a snake or
	// ladder otherwise return false
	public boolean checkExist(int index) {
		return (!(usedIndex.contains(index)));
	}
	
	public Snake isSnakewarped(int index ,Counter counter) {
		if(counter instanceof NeverGobackCounter) {
			return null;
		}
		for(Snake snake : snakelist) {
			if(counter.getBoardIndex() == snake.getTailIndex() ) {
				if(counter == Logic.GameLogic.getPlayer1Counter()) {
					if(counter instanceof DiagonalCounter) {
						DiagonalCounter dcounter = (DiagonalCounter) counter;
						ArrayList<Integer> path = dcounter.getPathIndex();
						if(path.size() >=1) {
						if(snake.getHeadIndex() == path.get(path.size()-1)) {
							return snake;
						}
						}
					}else if(lastDrawP1+Logic.GameLogic.gettempNumber() == snake.getHeadIndex()) {
						return snake;
					}else if(counter instanceof BackwardCounter || myBoard.isreversefromCard) {
						if(lastDrawP1-Logic.GameLogic.gettempNumber() == snake.getHeadIndex()) {
							myBoard.isreversefromCard = false;
							return snake;
						}
					}
				}
				else {
					if(counter instanceof DiagonalCounter) {
						DiagonalCounter dcounter = (DiagonalCounter) counter;
						ArrayList<Integer> path = dcounter.getPathIndex();
						if(path.size()>=1) {
						if(snake.getHeadIndex() == path.get(path.size()-1)) {
							return snake;
						}
						}
					}else if(lastDrawP2+Logic.GameLogic.gettempNumber() == snake.getHeadIndex()) {
						return snake;
					}else if(counter instanceof BackwardCounter || myBoard.isreversefromCard) {
						if(lastDrawP2-Logic.GameLogic.gettempNumber() == snake.getHeadIndex()) {
							myBoard.isreversefromCard = false;
							return snake;
						}
					}
				}
			}
		}
		return null;
	}
	
	public Ladder isLadderwarped(int index ,Counter counter) {
		for(Ladder ladder : ladderlist) {
			if(counter == Logic.GameLogic.getPlayer1Counter()) {
				if(counter instanceof DiagonalCounter) {
					DiagonalCounter dcounter = (DiagonalCounter) counter;
					ArrayList<Integer> path = dcounter.getPathIndex();
					if(path.size()>=1) {
						if(ladder.getLowerIndex() == path.get(path.size()-1)) {
							return ladder;
						}
					}
				}else if(lastDrawP1+Logic.GameLogic.gettempNumber() == ladder.getLowerIndex()) {
					return ladder;
				}else if(counter instanceof BackwardCounter || myBoard.isreversefromCard) {
					if(lastDrawP1-Logic.GameLogic.gettempNumber() == ladder.getLowerIndex()) {
						myBoard.isreversefromCard = false;
						return ladder;
					}
				}
			}
			else {
				if(counter instanceof DiagonalCounter) {
					DiagonalCounter dcounter = (DiagonalCounter) counter;
					ArrayList<Integer> path = dcounter.getPathIndex();
					if(path.size()>=1) {
					if(ladder.getLowerIndex() == path.get(path.size()-1)) {
						return ladder;
					}
					}
				}else if(lastDrawP2+Logic.GameLogic.gettempNumber() == ladder.getLowerIndex()) {
					return ladder;
				}else if(counter instanceof BackwardCounter || myBoard.isreversefromCard) {
					if(lastDrawP2-Logic.GameLogic.gettempNumber() == ladder.getLowerIndex()) {
						myBoard.isreversefromCard = false;
						return ladder;
					}
				}
			}
		}
		return null;
	}
	

	public void drawinitialBoardOnCanvas(GraphicsContext context) {
		// clear an drawing on this Canva
		context.clearRect(0, 0, 720, 720);
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				// finds the y position of the cell, by multiplying the row number by 50, which
				// is the height of a row // in pixels
				// then adds 2, to add some offset
				int position_y = row * 80 + 2;

				// finds the x position of the cell, by multiplying the column number by 50,
				// which is the width of a // column in pixels
				// then add 2, to add some offset
				int position_x = col * 80 + 2;

				// defines the width of the square as 46 instead of 50, to account for the 4px
				// total of blank space // caused by the offset
				// as we are drawing squares, the height is going to be the same
				int width = 76;

				// set the fill color to white (you could set it to whatever you want)
				if ((row + col) % 2 == 0) {
					context.setFill(Color.ROYALBLUE);
				} else {
					context.setFill(Color.MEDIUMSEAGREEN);
				}
				if(row == 0 && col == 8) {
					context.setFill(Color.RED);
				}
				// draw a rounded rectangle with the calculated position and width. The last two
				// arguments specify the // rounded corner arcs width and height.
				// Play around with those if you want.
				context.fillRoundRect(position_x, position_y, width, width, 10, 10);
				Font font = Font.font(30);
				context.setFont(font);
				context.setLineWidth(2.0);
				context.setFill(Color.LAVENDERBLUSH);
				context.setStroke(Color.BLACK);
				double xText = (double) position_x;
				double yText = (double) position_y;
				int numberIndex = Logic.GameLogic.changePosToIndex(col, row);
				if (numberIndex < 10) {
					context.fillText("" + numberIndex, xText + 30, yText + 48);
				} else {
					context.fillText("" + numberIndex, xText + 20, yText + 48);
				}
			}
		}
	}

	public void drawSnake(GraphicsContext context, int i, int boardNumber) {
		int[] snakePos = null;
		;
		double[] degreelist = null;
		;
		double degree = 0;
		int x = 0;
		int y = 0;
		if (boardNumber == 0) {
			snakePos = board1snake;
			degreelist = board1degree;
		} else if (boardNumber == 1) {
			snakePos = board2snake;
			degreelist = board2degree;
		}
		String imageurl = "Error image url";
		if (i == 0) {
			degree = degreelist[0];
			x = snakePos[0];
			y = snakePos[1];
			imageurl = ClassLoader.getSystemResource("snake5.png").toString();;
		} else if (i == 1) {
			degree = degreelist[1];
			x = snakePos[2];
			y = snakePos[3];
			imageurl = ClassLoader.getSystemResource("snake2.png").toString();;
		} else if (i == 2) {
			degree = degreelist[2];
			x = snakePos[4];
			y = snakePos[5];
			imageurl = ClassLoader.getSystemResource("snake3.png").toString();;
		} else if (i == 3) {
			degree = degreelist[3];
			x = snakePos[6];
			y = snakePos[7];
			imageurl = ClassLoader.getSystemResource("snake4.png").toString();;

		}
		Image image = new Image(imageurl);
		context.rotate(degree);
		context.drawImage(image, x, y);
	}

	public void drawLadder(GraphicsContext context, int i, int boardNumber) {
		int[] ladderPos = null;
		double[] degreeladder = null;
		double[] degreesnake = null;
		double degree = 0;
		int x = 0;
		int y = 0;
		ladderPos = boardladder;
		degreeladder = boardladderdegree;
		if (boardNumber == 0) {
			degreesnake = board1degree;
		} else if (boardNumber == 1) {
			degreesnake = board2degree;

		}
		String imageurl = "Error image url";
		if (i == 0) {
			degree = degreeladder[0];
			x = ladderPos[0];
			y = ladderPos[1];
			imageurl = ClassLoader.getSystemResource("ladder1.png").toString();;
		} else if (i == 1) {
			degree = degreeladder[1];
			x = ladderPos[2];
			y = ladderPos[3];
			imageurl = ClassLoader.getSystemResource("ladder2.png").toString();;
		} else if (i == 2) {
			degree = degreeladder[2];
			x = ladderPos[4];
			y = ladderPos[5];
			imageurl = ClassLoader.getSystemResource("ladder1.png").toString();;
		} else if (i == 3) {
			degree = degreeladder[3];
			x = ladderPos[6];
			y = ladderPos[7];
			imageurl = ClassLoader.getSystemResource("ladder2.png").toString();;
		}
		Image image = new Image(imageurl);
		context.rotate(degree);
		context.drawImage(image, x, y);
		if (i == 3) {
			double disdegree = 0;
			for (int j = 0; j < 4; j++) {
				disdegree += (-1) * degreesnake[j] + (-1) * degreeladder[j];
			}
			context.rotate(disdegree);
		}
	}

	public void drawCard(GraphicsContext context) {
		// start drawing
		for (int i = 0; i < specialIndex.size(); i++) {
			int x = Logic.GameLogic.changeIndextoPos(specialIndex.get(i)).get(0);
			int y = Logic.GameLogic.changeIndextoPos(specialIndex.get(i)).get(1);
			int position_y = y * 80 + 2;
			int position_x = x * 80 + 2;
			int width = 76;
			context.setFill(Color.GOLD);
			context.fillRoundRect(position_x, position_y, width, width, 10, 10);
			Font font = Font.font(30);
			context.setFont(font);
			context.setLineWidth(2.0);
			context.setFill(Color.LAVENDERBLUSH);
			context.setStroke(Color.BLACK);
			double xText = (double) position_x;
			double yText = (double) position_y;
			context.fillText("?", xText + 30, yText + 48);
		}

	}

	public void drawcounter(GraphicsContext context, Counter counter, int playerNumber, int x, int y) {
		// Draw BaseCounter
		int xPos = 80 * x + 2;
		int yPos = 80 * y + 2;
		if (playerNumber == 1) {
			xPos = xPos + 10;
			yPos = yPos + 10;
			context.setFill(Color.FUCHSIA);
			p1canvas.getGraphicsContext2D().setFill(Color.FUCHSIA);
			p1canvas.getGraphicsContext2D().setLineWidth(3.0);
			p1canvas.getGraphicsContext2D().fillOval(0, 0, 20, 20);
			p1canvas.getGraphicsContext2D().strokeOval(0, 0, 20, 20);			
		} else {
			xPos = xPos + 50;
			yPos = yPos + 50;
			context.setFill(Color.RED);
			p2canvas.getGraphicsContext2D().setFill(Color.RED);
			p2canvas.getGraphicsContext2D().setLineWidth(3.0);
			p2canvas.getGraphicsContext2D().fillOval(0, 0, 20, 20);
			p2canvas.getGraphicsContext2D().strokeOval(0, 0, 20, 20);			
		}
		context.setLineWidth(3.0);
		context.fillOval(xPos, yPos, 20, 20);
		context.strokeOval(xPos, yPos, 20, 20);			
		if(counter instanceof BackwardCounter) {
			context.drawImage(new Image(ClassLoader.getSystemResource("groot.png").toString()), xPos, yPos,20,20);
			if(playerNumber==1)
				p1canvas.getGraphicsContext2D().drawImage(new Image(ClassLoader.getSystemResource("groot.png").toString()),0, 0,20,20);
			else
				p2canvas.getGraphicsContext2D().drawImage(new Image(ClassLoader.getSystemResource("groot.png").toString()),0, 0,20,20);
		}else if(counter instanceof DiagonalCounter) {
			context.drawImage(new Image(ClassLoader.getSystemResource("captain.png").toString()), xPos, yPos,20,20);
			if(playerNumber==1)
				p1canvas.getGraphicsContext2D().drawImage(new Image(ClassLoader.getSystemResource("captain.png").toString()),0, 0,20,20);
			else
				p2canvas.getGraphicsContext2D().drawImage(new Image(ClassLoader.getSystemResource("captain.png").toString()),0, 0,20,20);
		}else if(counter instanceof NeverGobackCounter) {
			context.drawImage(new Image(ClassLoader.getSystemResource("ironman.png").toString()), xPos, yPos,20,20);
			if(playerNumber==1)
				p1canvas.getGraphicsContext2D().drawImage(new Image(ClassLoader.getSystemResource("ironman.png").toString()),0, 0,20,20);
			else
				p2canvas.getGraphicsContext2D().drawImage(new Image(ClassLoader.getSystemResource("ironman.png").toString()),0, 0,20,20);
		}else if(counter instanceof DoubleDiceCounter) {
			context.drawImage(new Image(ClassLoader.getSystemResource("spiderman.png").toString()), xPos, yPos,20,20);
			if(playerNumber==1)
				p1canvas.getGraphicsContext2D().drawImage(new Image(ClassLoader.getSystemResource("spiderman.png").toString()),0, 0,20,20);
			else
				p2canvas.getGraphicsContext2D().drawImage(new Image(ClassLoader.getSystemResource("spiderman.png").toString()),0, 0,20,20);
		}

	}
	
	

	public void updateBoard(GraphicsContext context, ArrayList<Integer> Pos1, ArrayList<Integer> Pos2) {

		drawinitialBoardOnCanvas(context);
		// Draw card
		drawCard(context);
		// Draw a snake on board
		for (int i = 0; i < 4; i++) {
			drawSnake(context, i, this.getBoardNumber());
		}

		// Draw a ladder on board
		for (int i = 0; i < 4; i++) {
			drawLadder(context, i, this.getBoardNumber());
		}
		Counter c1 = Logic.GameLogic.getPlayer1Counter();
		Counter c2 = Logic.GameLogic.getPlayer2Counter();
		drawcounter(context, c1, 1, Pos1.get(0), Pos1.get(1));
		drawcounter(context, c2, 2, Pos2.get(0), Pos2.get(1));
	}

	public void endTurn(ActionEvent event) throws Exception {
		if (isPlayer1Playing()) {
			GameLogic.getPlayer1Counter().roll();
		} else {
			GameLogic.getPlayer2Counter().roll();
		}
//		updateBoard(this.boardCanvas.getGraphicsContext2D());
//		UpdateUtil();
		if (isPlayer1Playing()) {
			Counter c = GameLogic.getPlayer1Counter().CheckAction();
			if (c != null)
				GameLogic.setPlayer1Counter(c);
		} else {
			Counter c = GameLogic.getPlayer2Counter().CheckAction();
			if (c != null)
				GameLogic.setPlayer2Counter(c);
		}
		
		if(GameLogic.getPlayer1Counter() instanceof BackwardCounter) {
			P1name.setText("P1 (Backward Counter)");
		}else if(GameLogic.getPlayer1Counter() instanceof DiagonalCounter) {
			P1name.setText("P1 (Diagonal Counter)");
		}else if(GameLogic.getPlayer1Counter() instanceof NeverGobackCounter) {
			P1name.setText("P1 (Never-go-back Counter)");
		}else if(GameLogic.getPlayer1Counter() instanceof BaseCounter) {
			P1name.setText("P1 (Base Counter)");
		}else if(GameLogic.getPlayer1Counter() instanceof DoubleDiceCounter) {
			P1name.setText("P1 (Double Dice Counter)");
		}
		if(GameLogic.getPlayer2Counter() instanceof BackwardCounter) {
			P2name.setText("P2 (Backward Counter)");
		}else if(GameLogic.getPlayer2Counter() instanceof DiagonalCounter) {
			P2name.setText("P2 (Diagonal Counter)");
		}else if(GameLogic.getPlayer2Counter() instanceof NeverGobackCounter) {
			P2name.setText("P2 (Never-go-back Counter)");
		}else if(GameLogic.getPlayer2Counter() instanceof BaseCounter) {
			P2name.setText("P2 (Base Counter)");
		}else if(GameLogic.getPlayer2Counter() instanceof DoubleDiceCounter) {
			P2name.setText("P2 (Double Dice Counter)");
		}
		
		
		turn += 1;
		rollButton.setDisable(false);
		endButton.setDisable(true);
		if (turn == 0) {
			endButton.setText("End turn");
		}
		if (isPlayer1Playing()) {
			p1bg.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, null)));
			p2bg.setBackground(new Background(new BackgroundFill(null, null, null)));

		} else {
			p1bg.setBackground(new Background(new BackgroundFill(null, null, null)));
			p2bg.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, null)));

		}
		UpdateUtil();
		if (GameLogic.IsGameEnd()) {
			if(myBoard.isPlayer1Playing())
				GameLogic.isPlayer1Win = true;
			else
				GameLogic.isPlayer1Win = false;
			this.initialize(null, null);
			GameLogic.setPlayer1Counter(new BaseCounter(0, 8));
			GameLogic.setPlayer2Counter(new BaseCounter(0, 8));
			Parent root = FXMLLoader.load(getClass().getResource("EndGameScreen.fxml"));
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
	}

	private void UpdateUtil() throws Exception {
		final GraphicsContext gc = this.boardCanvas.getGraphicsContext2D();
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				if (!isPlayer1Playing()) {
					// if the Counter1 was warped by snake 
					if(isSnakewarped(GameLogic.getPlayer1Counter().getBoardIndex(),GameLogic.getPlayer1Counter()) != null) {
						Snake snake = isSnakewarped(GameLogic.getPlayer1Counter().getBoardIndex(),GameLogic.getPlayer1Counter());
						// Slowly walk to the snake head
						for (int i = lastDrawP1; i <= snake.getHeadIndex(); i++) {
							final int k = i;
							Platform.runLater(new Runnable() {
	
								@Override
								public void run() {
									// TODO Auto-generated method stub
									updateBoard(gc, GameLogic.changeIndextoPos(k),
											GameLogic.changeIndextoPos(GameLogic.getPlayer2Counter().getBoardIndex()));
								}
							});
							try {
								Thread.sleep(200);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						//Warped to the snake tail
					   final int k = GameLogic.getPlayer1Counter().getBoardIndex();
					   Platform.runLater(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								updateBoard(gc, GameLogic.changeIndextoPos(k),
										GameLogic.changeIndextoPos(GameLogic.getPlayer2Counter().getBoardIndex()));

							}
						});
						try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						//}
					}
					// if the Counter1 was warped by ladder
					if(isLadderwarped(GameLogic.getPlayer1Counter().getBoardIndex(),GameLogic.getPlayer1Counter()) != null) {
						Ladder ladder = isLadderwarped(GameLogic.getPlayer1Counter().getBoardIndex(),GameLogic.getPlayer1Counter());
						// check if Counter1 enter the ladder lower first
						//if(ladder.getLowerIndex() == lastDrawP1) {
						// Slowly walk to the ladder lowerIndex
						for (int i = lastDrawP1; i <= ladder.getLowerIndex(); i++) {
							final int k = i;
							Platform.runLater(new Runnable() {
	
								@Override
								public void run() {
									// TODO Auto-generated method stub
									updateBoard(gc, GameLogic.changeIndextoPos(k),
											GameLogic.changeIndextoPos(GameLogic.getPlayer2Counter().getBoardIndex()));

								}
							});
							try {
								Thread.sleep(200);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						//Warped to the ladder upperIndex
					   final int k = GameLogic.getPlayer1Counter().getBoardIndex();
					   Platform.runLater(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								updateBoard(gc, GameLogic.changeIndextoPos(k),
										GameLogic.changeIndextoPos(GameLogic.getPlayer2Counter().getBoardIndex()));

							}
						});
						try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						//}
					}
					else if(GameLogic.getPlayer1Counter().getDirection() == Direction.Forward) {
						for (int i = lastDrawP1; i <= GameLogic.getPlayer1Counter().getBoardIndex(); i++) {
							final int k = i;
							Platform.runLater(new Runnable() {
	
								@Override
								public void run() {
									// TODO Auto-generated method stub
									updateBoard(gc, GameLogic.changeIndextoPos(k),
											GameLogic.changeIndextoPos(GameLogic.getPlayer2Counter().getBoardIndex()));
	
								}
							});
							try {
								Thread.sleep(200);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
					}else if(GameLogic.getPlayer1Counter().getDirection() == Direction.Backward) {
						for (int i = lastDrawP1; i >= GameLogic.getPlayer1Counter().getBoardIndex(); i--) {
							final int k = i;
							Platform.runLater(new Runnable() {
								@Override
								public void run() {
									// TODO Auto-generated method stub
									updateBoard(gc, GameLogic.changeIndextoPos(k),
											GameLogic.changeIndextoPos(GameLogic.getPlayer2Counter().getBoardIndex()));
	
								}
							});
							try {
								Thread.sleep(200);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}else if(GameLogic.getPlayer1Counter() instanceof DiagonalCounter) {
						DiagonalCounter dcounter1 = (DiagonalCounter) GameLogic.getPlayer1Counter();
						ArrayList<Integer> pathIndex = dcounter1.getPathIndex();
						for (int i = 0; i < pathIndex.size(); i++) {
							final int k = pathIndex.get(i);
							Platform.runLater(new Runnable() {
								@Override
								public void run() {
									// TODO Auto-generated method stub
									updateBoard(gc, GameLogic.changeIndextoPos(k),
											GameLogic.changeIndextoPos(GameLogic.getPlayer2Counter().getBoardIndex()));
	
								}
							});
							try {
								Thread.sleep(200);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}else {
					// if the Counter2 was warped by snake 
					if(isSnakewarped(GameLogic.getPlayer2Counter().getBoardIndex(),GameLogic.getPlayer2Counter()) != null) {
						Snake snake = isSnakewarped(GameLogic.getPlayer2Counter().getBoardIndex(),GameLogic.getPlayer2Counter());
						// check if Counter2 enter the snake head first
						//if(snake.getHeadIndex() == lastDrawP2) {
						// Slowly walk to the snake head
						for (int i = lastDrawP2; i <= snake.getHeadIndex(); i++) {
							final int k = i;
							Platform.runLater(new Runnable() {
	
								@Override
								public void run() {
									// TODO Auto-generated method stub
									updateBoard(gc,
											GameLogic.changeIndextoPos(GameLogic.getPlayer1Counter().getBoardIndex()),
											GameLogic.changeIndextoPos(k));

								}
							});
							try {
								Thread.sleep(200);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						//Warped to the snake tail
					   final int k = GameLogic.getPlayer2Counter().getBoardIndex();
					   Platform.runLater(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								updateBoard(gc,
										GameLogic.changeIndextoPos(GameLogic.getPlayer1Counter().getBoardIndex()),
										GameLogic.changeIndextoPos(k));

							}
						});
						try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						//}
					}
					// if the Counter2 was warped by ladder
					if(isLadderwarped(GameLogic.getPlayer2Counter().getBoardIndex(),GameLogic.getPlayer2Counter()) != null) {
						Ladder ladder = isLadderwarped(GameLogic.getPlayer2Counter().getBoardIndex(),GameLogic.getPlayer2Counter());
						// check if Counter2 enter the ladder lower first
						//if(ladder.getLowerIndex() == lastDrawP2) {
						// Slowly walk to the ladder lowerIndex
						for (int i = lastDrawP2; i <= ladder.getLowerIndex(); i++) {
							final int k = i;
							Platform.runLater(new Runnable() {
	
								@Override
								public void run() {
									// TODO Auto-generated method stub
									updateBoard(gc,
											GameLogic.changeIndextoPos(GameLogic.getPlayer1Counter().getBoardIndex()),
											GameLogic.changeIndextoPos(k));

								}
							});
							try {
								Thread.sleep(200);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						//Warped to the ladder upperIndex
					   final int k = GameLogic.getPlayer2Counter().getBoardIndex();
					   Platform.runLater(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								updateBoard(gc,
										GameLogic.changeIndextoPos(GameLogic.getPlayer1Counter().getBoardIndex()),
										GameLogic.changeIndextoPos(k));

							}
						});
						try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						//}
					}
					else if(GameLogic.getPlayer2Counter().getDirection() == Direction.Forward) {
						for (int i = lastDrawP2; i <= GameLogic.getPlayer2Counter().getBoardIndex(); i++) {
							final int k = i;
							Platform.runLater(new Runnable() {
								@Override
								public void run() {
									// TODO Auto-generated method stub
									updateBoard(gc,
											GameLogic.changeIndextoPos(GameLogic.getPlayer1Counter().getBoardIndex()),
											GameLogic.changeIndextoPos(k));
								}
							});
							try {
								Thread.sleep(200);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}else if(GameLogic.getPlayer2Counter().getDirection() == Direction.Backward) {
						for (int i = lastDrawP2; i >= GameLogic.getPlayer2Counter().getBoardIndex(); i--) {
							final int k = i;
							Platform.runLater(new Runnable() {
								@Override
								public void run() {
									// TODO Auto-generated method stub
									updateBoard(gc,
											GameLogic.changeIndextoPos(GameLogic.getPlayer1Counter().getBoardIndex()),
											GameLogic.changeIndextoPos(k));
								}
							});
							try {
								Thread.sleep(200);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}else if(GameLogic.getPlayer2Counter() instanceof DiagonalCounter) {
						DiagonalCounter dcounter2 = (DiagonalCounter) GameLogic.getPlayer2Counter();
						ArrayList<Integer> pathIndex = dcounter2.getPathIndex();
						for (int i = 0; i < pathIndex.size(); i++) {
							final int k = pathIndex.get(i);
							Platform.runLater(new Runnable() {
								@Override
								public void run() {
									// TODO Auto-generated method stub
									updateBoard(gc, 
											GameLogic.changeIndextoPos(GameLogic.getPlayer1Counter().getBoardIndex()),
											GameLogic.changeIndextoPos(k));
	
								}
							});
							try {
								Thread.sleep(200);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
				lastDrawP1 = GameLogic.getPlayer1Counter().getBoardIndex();
				lastDrawP2 = GameLogic.getPlayer2Counter().getBoardIndex();
			}
		});
		t.start();
	}

	public void rollBoard() {
		int j = GameLogic.Randomnum();
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {

				endButton.setDisable(true);
				// TODO Auto-generated method stub
				for (int i = 0; i < 7; i++) {
					int k = GameLogic.Randomnum();
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							numberLabel.setText("" + k);
						}
					});
					try {
						Thread.sleep(50 * i);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						numberLabel.setText("" + j);
					}
				});
				endButton.setDisable(false);
			}
		});
		t.start();
		rollButton.setDisable(true);
		endButton.setDisable(false);
		GameLogic.setTempNumber(j);

	}

	public static boolean isPlayer1Playing() {
		return turn % 2 == 0;
	}

	public static ArrayList<BaseCard> getCardlist() {
		return cardlist;
	}

	public void setCardlist(ArrayList<BaseCard> cardlist) {
		this.cardlist = cardlist;
	}

	public void setSpecialIndex(ArrayList<Integer> specialIndex) {
		this.specialIndex = specialIndex;
	}

	public int getBoardNumber() {
		return boardNumber;
	}

	public void setBoardNumber(int boardNumber) {
		this.boardNumber = boardNumber;
	}

}
