package entity;
//
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

import Logic.Direction;
import Logic.GameLogic;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class DiagonalCounter extends Counter{
	private ArrayList<Integer> pathIndex;

	public DiagonalCounter(int xPos, int yPos) {
		super(xPos, yPos);
		ArrayList<Integer> path = new ArrayList<Integer>();
		this.setPathIndex(path);
	}
	
	public ArrayList<Integer> getPathIndex() {
		return pathIndex;
	}

	public void setPathIndex(ArrayList<Integer> pathIndex) {
		this.pathIndex = pathIndex;
	}

	public BaseCounter roll() {
		ArrayList<Integer> fpath = new ArrayList<Integer>();
		fpath.add(this.getBoardIndex());
		this.setPathIndex(fpath);
		int diceNumber = Logic.GameLogic.gettempNumber();
		int boardIndex = this.getBoardIndex();
		int xPos = this.getxPos();
		int yPos = this.getyPos();
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Choose the direction");
		alert.setHeaderText(null);
		alert.setContentText("Choose the Counter's Direction for this turn");
		alert.getButtonTypes().clear();
		
		ButtonType forward = new ButtonType("Go Forward");
		ButtonType backward = new ButtonType("Go Backward");
		ButtonType topward = new ButtonType("Go Topward");
		ButtonType bottomward = new ButtonType("Go Bottomward");
		ButtonType topleft = new ButtonType("Go Topleft");
		ButtonType topright = new ButtonType("Go Topright");
		ButtonType bottomleft = new ButtonType("Go Bottomleft");
		ButtonType bottomright = new ButtonType("Go Bottomright");
		alert.getButtonTypes().addAll(forward,backward,topward,bottomward,topleft,topright,bottomleft,bottomright);
		
		Optional<ButtonType> option = alert.showAndWait();
		if(option.get() == forward) {
			this.setDirection(Direction.Forward);
			boardIndex = boardIndex+diceNumber;
			this.setBoardIndex(boardIndex);
		}else if(option.get() == backward) {
			this.setDirection(Direction.Backward);
			boardIndex = Math.max(0, boardIndex-diceNumber);
			this.setBoardIndex(boardIndex);
		}else if(option.get() == topward) {
			ArrayList<Integer> path = new ArrayList<Integer>();
			int x = this.getxPos();
			int y = this.getyPos();
			this.setDirection(Direction.Top);
			this.setyPos(Math.max(0,this.getyPos()-diceNumber));
			for(int i = y;i>=this.getyPos();i--) {
				path.add(Logic.GameLogic.changePosToIndex(x, i));
			}
			this.setPathIndex(path);
		}else if(option.get() == bottomward) {
			ArrayList<Integer> path = new ArrayList<Integer>();
			int x = this.getxPos();
			int y = this.getyPos();
			this.setDirection(Direction.Bottom);
			this.setyPos(Math.min(8,this.getyPos()+diceNumber));
			for(int i = y;i<=this.getyPos();i++) {
				path.add(Logic.GameLogic.changePosToIndex(x, i));
			}
			this.setPathIndex(path);
		}else if(option.get() == topleft) {
			ArrayList<Integer> path = new ArrayList<Integer>();
			int x = this.getxPos();
			int y = this.getyPos();
			this.setDirection(Direction.Topleft);
			int diff = Math.min(this.getxPos(), this.getyPos());
			diff = Math.min(diff, diceNumber);
			this.setxPos(Math.max(0,this.getxPos()-diff));
			this.setyPos(Math.max(0,this.getyPos()-diff));
			for(int i = y,j = x;i>=this.getyPos() && j>= this.getxPos();i--,j--) {
				path.add(Logic.GameLogic.changePosToIndex(j, i));
			}
			this.setPathIndex(path);
		}else if(option.get() == topright) {
			ArrayList<Integer> path = new ArrayList<Integer>();
			int x = this.getxPos();
			int y = this.getyPos();
			this.setDirection(Direction.TopRight);
			int diff = Math.min(8-this.getxPos(), this.getyPos());
			diff = Math.min(diff, diceNumber);
			this.setxPos(Math.min(8,this.getxPos()+diff));
			this.setyPos(Math.max(0,this.getyPos()-diff));
			for(int i = y,j = x;i>=this.getyPos() && j<= this.getxPos();i--,j++) {
				path.add(Logic.GameLogic.changePosToIndex(j, i));
			}
			this.setPathIndex(path);
		}else if(option.get() == bottomleft) {
			ArrayList<Integer> path = new ArrayList<Integer>();
			int x = this.getxPos();
			int y = this.getyPos();
			this.setDirection(Direction.BottomLeft);
			int diff = Math.min(8-this.getyPos(), this.getxPos());
			diff = Math.min(diff, diceNumber);
			this.setxPos(Math.max(0,this.getxPos()-diff));
			this.setyPos(Math.min(8,this.getyPos()+diff));
			for(int i = y,j = x;i<=this.getyPos() && j>= this.getxPos();i++,j--) {
				path.add(Logic.GameLogic.changePosToIndex(j, i));
			}
			this.setPathIndex(path);
		}else if(option.get() == bottomright) {
			ArrayList<Integer> path = new ArrayList<Integer>();
			int x = this.getxPos();
			int y = this.getyPos();
			this.setDirection(Direction.BottomRight);
			int diff = Math.min(8-this.getyPos(), 8-this.getxPos());
			diff = Math.min(diff, diceNumber);
			this.setxPos(Math.min(8,this.getxPos()+diff));
			this.setyPos(Math.min(8,this.getyPos()+diff));
			for(int i = y,j = x;i<=this.getyPos() && j<= this.getxPos();i++,j++) {
				path.add(Logic.GameLogic.changePosToIndex(j, i));
			}
			this.setPathIndex(path);
		}
		if(this.getDirection() == Direction.Backward || this.getDirection()== Direction.Forward) {
			ArrayList<Integer> newPos = Logic.GameLogic.changeIndextoPos(boardIndex);
			this.setxPos(newPos.get(0));
			this.setyPos(newPos.get(1));
			this.setBoardIndex(boardIndex);
		}else {
			this.setBoardIndex(Logic.GameLogic.changePosToIndex(this.getxPos(), this.getyPos()));
		}
		if(boardIndex >= 81) {
			// TODO Player Win
			GameLogic.endgame();
		}
		return null;
	}
	
	
	

}
