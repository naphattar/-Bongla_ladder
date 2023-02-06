package main;

import java.io.File;
import java.io.IOException;

import Logic.GameLogic;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		GameLogic.initializeGame();
		launch();
	}

	@Override
	public void start(Stage arg0) throws Exception {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("StartScreen.fxml"));
//			Parent root = FXMLLoader.load(getClass().getResource("StartScreen.fxml"));
			arg0.setScene(new Scene(root));
			arg0.setTitle("Bongla ladder");
			arg0.show();
		}catch(Exception e ) {
			System.out.println(e);
		}
	}

}
