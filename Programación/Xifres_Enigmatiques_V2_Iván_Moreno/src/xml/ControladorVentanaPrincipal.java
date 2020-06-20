package xml;

import java.io.IOException;

import application.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ControladorVentanaPrincipal {

	@FXML
	private Button cerrar;

	private Stage primaryStage;

	@FXML
	private void segur(ActionEvent event) {

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ControladorVentanaPrincipal.class.getResource("segur.fxml"));
			Scene scene = new Scene(loader.load());
			Stage stage = new Stage();
			stage.setTitle("Xifres Enigmatiques");
			stage.setScene(scene);
			stage.show();
			stage.setResizable(false);
			Stage stage2 = (Stage) cerrar.getScene().getWindow();
			stage2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	@FXML
	private void basic(ActionEvent event) {

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ControladorVentanaPrincipal.class.getResource("basic.fxml"));
			Scene scene = new Scene(loader.load());
			Stage stage = new Stage();
			stage.setTitle("Xifres Enigmatiques");
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
			Stage stage2 = (Stage) cerrar.getScene().getWindow();
			stage2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	public void cerrar(ActionEvent event) {
		Stage stage = (Stage) cerrar.getScene().getWindow();
		stage.close();
	}

}
