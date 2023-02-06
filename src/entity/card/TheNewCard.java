package entity.card;
//
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

import Logic.GameLogic;
import entity.BackwardCounter;
import entity.BaseCounter;
import entity.Counter;
import entity.DiagonalCounter;
import entity.DoubleDiceCounter;
import entity.NeverGobackCounter;
import gui.myBoard;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class TheNewCard extends BaseCard {

	public TheNewCard(float ratePercent) {
		super(ratePercent, "Your new buddy!!");
	}

	@Override
	public void cardAction(Counter counter) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Do you want this tier?");
		alert.setHeaderText(null);
		Random random = new Random();
		int rannum = random.nextInt(100);
		String text = "You got ";
		if(rannum <= 10) {
			text += "Backward Counter";
		}else if(rannum <= 40) {
			text += "Normal Counter";
		}else if(rannum <= 70) {
			text += "NeverGoBack Counter";
		}else if(rannum <= 90) {
			text += "DoubleDice Counter";
		}else if(rannum <= 100) {
			text += "Diagonal Counter";
		}
		int ranturn = random.nextInt(2)+2;
		text += " for "+ranturn+" turns";
		alert.setContentText(text);
		alert.getButtonTypes().clear();
		
		ButtonType btn1 = new ButtonType("Accept");
		ButtonType btn2 = new ButtonType("Decline");
		alert.getButtonTypes().addAll(btn1,btn2);
		
		Optional<ButtonType> option = alert.showAndWait();
		if(option.get() == btn1) {
			Counter c;
			if(rannum == 0) {
				c = new BackwardCounter(counter.getxPos(), counter.getyPos());
				c.setExpiredTurn(ranturn);
			}else if(rannum == 1) {
				c = new BaseCounter(counter.getxPos(), counter.getyPos());
				c.setExpiredTurn(ranturn);
				counter = c;
			}else if(rannum == 2) {
				c  = new DiagonalCounter(counter.getxPos(), counter.getyPos());
				c.setExpiredTurn(ranturn);
				counter = c;
			}else if(rannum == 3) {
				c = new DoubleDiceCounter(counter.getxPos(), counter.getyPos());
				c.setExpiredTurn(ranturn);
				counter = c;
			}else  {
				c = new NeverGobackCounter(counter.getxPos(), counter.getyPos());
				c.setExpiredTurn(ranturn);
				counter = c;
			}
			if(myBoard.isPlayer1Playing()) {
				GameLogic.setPlayer1Counter(c);
			}else {
				GameLogic.setPlayer2Counter(c);
			}
		}

	}

}
