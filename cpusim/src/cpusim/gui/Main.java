package cpusim.gui;

import cpusim.components.Processor;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		
		Group root = new Group();
		Scene scene = new Scene(root, 800, 600, Color.CORNFLOWERBLUE);
		stage.setScene(scene);
		stage.setTitle("CPU Simulator");
		stage.show();
		
	}
	
	public static void main(String[] args) {
		Processor processor = new Processor();
		processor.runProgram(args[0], args[1]);
		
		//launch();
	}

}
