package entity;
//
import java.util.ArrayList;
import java.util.Optional;

import Logic.Direction;
import Logic.GameLogic;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class DoubleDiceCounter extends Counter{

	public DoubleDiceCounter(int xPos, int yPos) {
		super(xPos, yPos);
	}
	// TODO fix roll function
	public BaseCounter roll() {
		int diceNumber1 = Logic.GameLogic.gettempNumber();
		int diceNumber2 = Logic.GameLogic.Randomnum();
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Choose the number");
		alert.setHeaderText(null);
		alert.setContentText("Choose your number for this turn");
		alert.getButtonTypes().clear();
		
		ButtonType fw = new ButtonType(""+diceNumber1);
		ButtonType bw = new ButtonType(""+diceNumber2);
		alert.getButtonTypes().addAll(fw,bw);
		
		Optional<ButtonType> option = alert.showAndWait();
		int diceNumber = diceNumber1;
		
		if(option.get() == fw) {
			diceNumber = diceNumber1;
			GameLogic.setTempNumber(diceNumber1);
		}else if(option.get() == bw) {
			diceNumber = diceNumber2;
			GameLogic.setTempNumber(diceNumber2);
		}
		
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
