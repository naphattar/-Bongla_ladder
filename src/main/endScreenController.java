package main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import Logic.GameLogic;
import gui.myBoard;
import javafx.animation.AnimationTimer;
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
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class endScreenController implements Initializable {
	@FXML
	private Button playButton,exitButton;
	@FXML
	private Canvas backgroundCanvas;
	@FXML
	private Label showPlayerWinLabel,timePlayLabel;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if(!GameLogic.isPlayer1Win) {
			showPlayerWinLabel.setText("P1 win !!");
		}else {
			showPlayerWinLabel.setText("P2 win !!");
		}
		if(myBoard.fromCard) {
			myBoard.fromCard = false;
			showPlayerWinLabel.setText("Draw because of card's effect. Bye bye.");
		}
		long totalTime = (long) (System.nanoTime() - myBoard.startTime);
		long totalTimeInDay = TimeUnit.DAYS.convert(totalTime,TimeUnit.NANOSECONDS);
		totalTime -= TimeUnit.NANOSECONDS.convert(totalTimeInDay,TimeUnit.DAYS);
		long totalTimeInHour = TimeUnit.HOURS.convert(totalTime, TimeUnit.NANOSECONDS);
		totalTime -= TimeUnit.NANOSECONDS.convert(totalTimeInHour, TimeUnit.HOURS);
		long totalTimeInMinute = TimeUnit.MINUTES.convert(totalTime,TimeUnit.NANOSECONDS);
		totalTime -= TimeUnit.NANOSECONDS.convert(totalTimeInMinute,TimeUnit.MINUTES);
		long totalTimeInSec = TimeUnit.SECONDS.convert(totalTime, TimeUnit.NANOSECONDS);
		totalTime -= TimeUnit.NANOSECONDS.convert(totalTimeInSec, TimeUnit.SECONDS);
		String timeShow = "Total time played : ";
		if(totalTimeInDay != 0) timeShow += totalTimeInDay + " days ";
		if(totalTimeInHour != 0) timeShow += totalTimeInHour + " hr. ";
		if(totalTimeInMinute!=0) timeShow += totalTimeInMinute + " min. ";
		if(totalTimeInSec != 0)  timeShow += totalTimeInSec + " s.";
		timePlayLabel.setText(timeShow);
		GraphicsContext gc = backgroundCanvas.getGraphicsContext2D();
		
		AnimationTimer t =  new AnimationTimer() {
			double time = System.nanoTime();
			@Override
			public void handle(long arg0) {
				drawBackground(backgroundCanvas.getGraphicsContext2D());
				gc.drawImage(new Image(ClassLoader.getSystemResource("55jq.gif").toString()),295,539,100,100);
				//											  time			distance
				double diftime = (((System.nanoTime() - time )/1E8)%(gc.getCanvas().getWidth()/15)) * 2;
//			gc.clearRect(posX,gc.getCanvas().getHeight()-670,50,500);
				if(diftime <=gc.getCanvas().getWidth()/15)
					gc.drawImage(new Image(ClassLoader.getSystemResource("groot.png").toString()),180,gc.getCanvas().getHeight()-650+diftime,50,50);
				else 
					gc.drawImage(new Image(ClassLoader.getSystemResource("groot.png").toString()),180,gc.getCanvas().getHeight()-630-diftime+(gc.getCanvas().getWidth()/10),50,50);		
				
				diftime = (((System.nanoTime() - time+0.15E9 )/1E8)%(gc.getCanvas().getWidth()/15)) * 2;
//			gc.clearRect(posX,gc.getCanvas().getHeight()-670,50,500);
				if(diftime <=gc.getCanvas().getWidth()/15)
					gc.drawImage(new Image(ClassLoader.getSystemResource("captain.png").toString()),280,gc.getCanvas().getHeight()-650+diftime,50,50);
				else 
					gc.drawImage(new Image(ClassLoader.getSystemResource("captain.png").toString()),280,gc.getCanvas().getHeight()-630-diftime+(gc.getCanvas().getWidth()/10),50,50);
			
				diftime = (((System.nanoTime() - time+0.3E9 )/1E8)%(gc.getCanvas().getWidth()/15)) * 2;
//			gc.clearRect(posX,gc.getCanvas().getHeight()-670,50,500);
				if(diftime <=gc.getCanvas().getWidth()/15)
					gc.drawImage(new Image(ClassLoader.getSystemResource("spiderman.png").toString()),380,gc.getCanvas().getHeight()-650+diftime,50,50);
				else 
					gc.drawImage(new Image(ClassLoader.getSystemResource("spiderman.png").toString()),380,gc.getCanvas().getHeight()-630-diftime+(gc.getCanvas().getWidth()/10),50,50);
				diftime = (((System.nanoTime() - time+0.45E9 )/1E8)%(gc.getCanvas().getWidth()/15)) * 2;
//			gc.clearRect(posX,gc.getCanvas().getHeight()-670,50,500);
				if(diftime <=gc.getCanvas().getWidth()/15)
					gc.drawImage(new Image(ClassLoader.getSystemResource("ironman.png").toString()),480,gc.getCanvas().getHeight()-650+diftime,50,50);
				else 
					gc.drawImage(new Image(ClassLoader.getSystemResource("ironman.png").toString()),480,gc.getCanvas().getHeight()-630-diftime+(gc.getCanvas().getWidth()/10),50,50);

			}
		};
		
		t.start();
	}
	
	public void startGame(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("StartScreen.fxml"));
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public void exitGame(ActionEvent event) {
		((Stage)((Node)event.getSource()).getScene().getWindow()).close();
	}
	public void drawBackground(GraphicsContext context) {
		context.clearRect(0, 0, context.getCanvas().getWidth(),context.getCanvas().getHeight());
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				// finds the y position of the cell, by multiplying the row number by 50, which
				// is the height of a row // in pixels
				// then adds 2, to add some offset
				int position_y = (int) (row * context.getCanvas().getWidth()/9 + 2);

				// finds the x position of the cell, by multiplying the column number by 50,
				// which is the width of a // column in pixels
				// then add 2, to add some offset
				int position_x = (int) (col * context.getCanvas().getWidth()/9 + 2);

				// defines the width of the square as 46 instead of 50, to account for the 4px
				// total of blank space // caused by the offset
				// as we are drawing squares, the height is going to be the same
				int width = (int) (context.getCanvas().getWidth()/9 - 4);

				// set the fill color to white (you could set it to whatever you want)
				context.setGlobalAlpha(0.2);
				context.setEffect(new BoxBlur(15,15,10));
				if ((row + col) % 2 == 0) {
					context.setFill(Color.ROYALBLUE);
				} else {
					context.setFill(Color.MEDIUMSEAGREEN);
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
				context.setEffect(null);
				context.setGlobalAlpha(1);
			}
		}
	}
}
