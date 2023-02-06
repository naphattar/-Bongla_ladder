package main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Logic.Direction;
import Logic.GameLogic;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
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
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
//
public class startScreenController implements Initializable {

	@FXML
	private AnchorPane backgroundpane;
	@FXML
	private Button playButton,exitButton,infoButton;
	@FXML
	private Canvas backgroundCanvas;
	private Image ladderImage = new Image(ClassLoader.getSystemResource("ladder2.png").toString());
	private Image snakeImage = new Image(ClassLoader.getSystemResource("snake5.png").toString());
	private int snakexPos = 75;
	private int snakeyPos = 100;
	private int ladderxPos = 210;
	private int ladderyPos = 150;
	private Direction ladderDirection = Direction.Bottom;
	private Direction snakedirection = Direction.Forward;
	private AnimationTimer snakeAnimation = null;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		GameLogic.BGSong.setCycleCount(AudioClip.INDEFINITE);
		GameLogic.BGSong.play();
		
		// TODO Auto-generated method stub
		GraphicsContext gc = backgroundCanvas.getGraphicsContext2D();
		gc.setFill(Color.POWDERBLUE);
		gc.fillRect(0, 0,backgroundCanvas.getWidth(), backgroundCanvas.getHeight());
		//gc.drawImage(ladderImage, 200, 100);
		gc.drawImage(snakeImage,snakexPos,snakeyPos);
		snakeAnimation = new AnimationTimer() {
			@Override
			public void handle(long arg0) {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(60);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				drawBackground(gc);
				
				if(ladderyPos == 140) {
					ladderDirection = Direction.Top;
				}if(ladderyPos == 150) {
					ladderDirection = Direction.Bottom;
				}
				if(ladderDirection == Direction.Top) {
					ladderyPos += 2;
					ladderxPos+=1;
				}else {
					ladderyPos -= 2;
					ladderxPos -=1;
				}
				gc.drawImage(new Image(ClassLoader.getSystemResource("ladder2.png").toString()), ladderxPos, ladderyPos);

				if(snakexPos == 65) {
					snakedirection = Direction.Backward;
				}
				if(snakexPos == 75) {
					snakedirection = Direction.Forward;
				}
				if(snakedirection == Direction.Forward){
//					    gc.clearRect(snakexPos, snakeyPos, snakeImage.getWidth(), snakeImage.getHeight());
					    //gc.drawImage(ladderImage, 200, 100);
//					    gc.fillRect(0, snakeyPos-1, backgroundCanvas.getWidth(), snakeImage.getHeight()+1);
					    //gc.drawImage(backgroundImage,10,200);
					    snakexPos = snakexPos -1;
					    gc.drawImage(snakeImage,snakexPos,snakeyPos);					  
						//System.out.println(snakexPos);
				}else {
//						gc.clearRect(snakexPos, snakeyPos, snakeImage.getWidth(), snakeImage.getHeight());
						//gc.drawImage(ladderImage, 200, 100);
//						gc.fillRect(0, snakeyPos-1, backgroundCanvas.getWidth(), snakeImage.getHeight()+1);
						//gc.drawImage(backgroundImage,10,200);
						snakexPos = snakexPos +1;
						gc.drawImage(snakeImage,snakexPos,snakeyPos);
						//System.out.println(snakexPos);
				}
				
			}
		};
		snakeAnimation.start();
	}
	
	public void startGame(ActionEvent event) throws IOException {
		snakeAnimation.stop();
		GameLogic.BGSong.setVolume(0);
		GameLogic.StartGameSound.play();
		Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void infoShow(ActionEvent event) throws IOException {
		snakeAnimation.stop();
		Parent root = FXMLLoader.load(getClass().getResource("HowToPlay1.fxml"));
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
