package main;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Logic.GameLogic;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class infoController implements Initializable {

	private static int indexPage = 0;
	private String[] listPage = {"HowToPlay1.fxml","HowToPlay2.fxml","HowToPlay3.fxml"};
	@FXML
	private Canvas canvas1,canvas2,canvas3;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public void goToNextPage(ActionEvent event) throws IOException {
//		System.out.println("pressed"+indexPage+" "+listPage.length);
		
		indexPage += 1;
		if(indexPage >= listPage.length) {
			indexPage = 0;
		}

		Parent root = FXMLLoader.load(getClass().getResource(listPage[indexPage]));
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}
	public void goToPrevPage(ActionEvent event) throws IOException  {
//		System.out.println("pressed"+indexPage+" "+listPage.length);
		
		indexPage -= 1;
		if(indexPage < 0) {
			indexPage = listPage.length-1;
		}

		Parent root = FXMLLoader.load(getClass().getResource(listPage[indexPage]));
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}
	public void goToStartPage(ActionEvent event) throws IOException {
//		System.out.println("pressed"+indexPage+" "+listPage.length);
		indexPage = 0;
		Parent root = FXMLLoader.load(getClass().getResource("startScreen.fxml"));
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

}
