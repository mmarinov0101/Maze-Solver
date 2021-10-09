import javafx.application.Application;
import maze.*;
import maze.routing.*;
import maze.visualisation.*;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.geometry.Pos;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

/**Class that runs the Maze application*/
public class MazeApplication extends Application {

	@Override
	public void start(Stage stage){
		try{
		Visualisator visualisator = new Visualisator();
		VBox screenContent = visualisator.screenVisualisator();
		Scene scene = new Scene(screenContent, 1200, 850);
		stage.setScene(scene);
 		stage.setTitle("Maze");
 		stage.show(); 
		}catch(InvalidMazeException e){
			e.printStackTrace();
		}
	}

	/**Main method, used to run the application*/
	public static void main(String[] args)
	{
		launch(args);
	}
}
