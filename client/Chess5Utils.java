package client;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class Chess5Utils {
	public static void showAlert(String msg){
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText(null);
		alert.setContentText(msg);
		alert.show();
	}
	
	public static Optional<ButtonType> showClert(String msg) {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText(null);
		alert.setContentText(msg);
		return alert.showAndWait();
	}
}
