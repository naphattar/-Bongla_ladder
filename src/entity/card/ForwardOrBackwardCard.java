package entity.card;
//
import java.util.ArrayList;
import java.util.Optional;

import Logic.Direction;
import Logic.GameLogic;
import entity.Counter;
import gui.myBoard;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class ForwardOrBackwardCard extends BaseCard {

	public ForwardOrBackwardCard(float ratePercent) {
		super(ratePercent, "Borward?");
	}

	@Override
	public void cardAction(Counter counter) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Choose a number");
		alert.setHeaderText(null);
		alert.setContentText("choose a number for the move");
		alert.getButtonTypes().clear();
		
		ButtonType btn1 = new ButtonType("1");
		ButtonType btn2 = new ButtonType("2");
		ButtonType btn3 = new ButtonType("3");
		ButtonType btn4 = new ButtonType("4");
		ButtonType btn5 = new ButtonType("5");
		ButtonType btn6 = new ButtonType("6");
		alert.getButtonTypes().addAll(btn1,btn2,btn3,btn4,btn5,btn6);
		
		Optional<ButtonType> option = alert.showAndWait();
		int rollNumber = 0;
		if(option.get() == btn1) {
			rollNumber = 1;
		}else if(option.get() == btn2) {
			rollNumber = 2;
		}else if(option.get() == btn3) {
			rollNumber = 3;
		}else if(option.get() == btn4) {
			rollNumber = 4;
		}else if(option.get() == btn5) {
			rollNumber = 5;
		}else if(option.get() == btn6) {
			rollNumber = 6;
		}
		Logic.GameLogic.setTempNumber(rollNumber);
		boolean isForward = GameLogic.Randomnum() %2 == 0;
		if(isForward) {
			counter.setBoardIndex(rollNumber+counter.getBoardIndex());
			ArrayList<Integer> Pos = GameLogic.changeIndextoPos(counter.getBoardIndex());
			counter.setxPos(Pos.get(0));
			counter.setyPos(Pos.get(1));
		}else {
			myBoard.isreversefromCard = true;
			counter.setBoardIndex(counter.getBoardIndex()-rollNumber);
			ArrayList<Integer> Pos = GameLogic.changeIndextoPos(counter.getBoardIndex());
			counter.setxPos(Pos.get(0));
			counter.setyPos(Pos.get(1));
		}
	}

}
