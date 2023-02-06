package entity;
//
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

import Logic.Direction;
import Logic.GameLogic;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class BackwardCounter extends Counter{
	
	public BackwardCounter(int xPos, int yPos) {
		super(xPos, yPos);
	}
	
	public BaseCounter roll() {
		//Choose Direction
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Choose the direction");
		alert.setHeaderText(null);
		alert.setContentText("Choose the Counter's Direction for this turn");
		alert.getButtonTypes().clear();
		
		ButtonType fw = new ButtonType("Go Forward");
		ButtonType bw = new ButtonType("Go Backward");
		alert.getButtonTypes().addAll(fw,bw);
		
		Optional<ButtonType> option = alert.showAndWait();
		
		if(option.get() == fw) {
			this.setDirection(Direction.Forward);;
		}else if(option.get() == bw) {
			this.setDirection(Direction.Backward);
		}
		
		//Start Rolling
		int diceNumber = Logic.GameLogic.gettempNumber();
				
		int boardIndex = this.getBoardIndex();
		if(this.getDirection().equals(Direction.Forward)) {
			boardIndex = boardIndex+diceNumber;
			boardIndex = Math.min(81,boardIndex);
		}else if(this.getDirection().equals(Direction.Backward)){
			boardIndex = boardIndex-diceNumber;
			boardIndex = Math.max(1, boardIndex);
		}
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
