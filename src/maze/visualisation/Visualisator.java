package maze.visualisation;
import maze.*;
import maze.routing.*;
import maze.visualisation.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.geometry.Pos;
import java.util.List;
import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
/**Class which gives a better representation of the application*/
public class Visualisator extends Application{

	/**List to store the Rectangle objects that build the maze*/
	private ArrayList<ArrayList<Rectangle>> list = new ArrayList<ArrayList<Rectangle>>();

	@Override
	public void start(Stage stage){}

	/**Draws the maze from an array of different colours, depending on the map that the method has received*/
	public GridPane mazeVisualisator(RouteFinder finder){
		ArrayList<Rectangle> row = new ArrayList<Rectangle>();
		String map = finder.getMaze().toString();
		GridPane maze = new GridPane();
		int columnCounter = 0;
		int rowCounter = 0;
		Image tileImg;
		ImagePattern tileView;
		for(int i = 0 ; i < map.length(); i++){
			Rectangle tile = new Rectangle(50, 50, 50, 50);
			if(map.charAt(i) != '\n'){
				if(map.charAt(i) == '#'){
					tileImg = new Image("../src/maze/visualisation/wall.png");
      				tileView = new ImagePattern(tileImg);
      				tile.setFill(tileView);
				} else if(map.charAt(i) == '.'){
					tileImg = new Image("../src/maze/visualisation/path.png");
      				tileView = new ImagePattern(tileImg);
      				tile.setFill(tileView);
				} else if(map.charAt(i) == 'e'){
					tileImg = new Image("../src/maze/visualisation/door.png");
      				tileView = new ImagePattern(tileImg);
      				tile.setFill(tileView);
				}else if(map.charAt(i) == 'x'){
					tileImg = new Image("../src/maze/visualisation/door.png");
      				tileView = new ImagePattern(tileImg);
      				tile.setFill(tileView);
				}else if(map.charAt(i) == '!'){
					tile.setFill(Color.GREEN);
				}else if(map.charAt(i) == '*'){
					tile.setFill(Color.GRAY);
				}
				maze.add(tile, columnCounter, rowCounter);
				row.add(tile);
				columnCounter++;
			} else{
				this.list.add(row);
				row = new ArrayList<Rectangle>();
				columnCounter = 0;
				rowCounter++;
			}
		}
		return maze;

	}

	/**Displays the visual representation of the maze and the three buttons: load/save/step*/
	public VBox screenVisualisator() throws InvalidMazeException{
		Maze mazeLayout = Maze.fromTxt("../resources/mazes/maze2.txt");
    	RouteFinder finder = new RouteFinder(mazeLayout);
		Button save = new Button();
		Button load = new Button();
		Button step = new Button();
		GridPane maze = null;
		save.setMinSize(305, 97);
		save.setMaxSize(305, 97);
		save.setStyle("-fx-font-size: 30px; ");
		save.setOnAction(actionEvent -> {
			if(!finder.isFinished() && !finder.getNoPossibleRoute()){
				finder.save("../src/object");
			}
		});
		Image img = new Image("../src/maze/visualisation/saveButton.png");
      	ImageView view = new ImageView(img);
      	save.setGraphic(view);
		load.setMinSize(305, 97);
		load.setMaxSize(305, 97);
		load.setStyle("-fx-font-size: 30px; ");
		load.setOnAction(actionEvent -> {
			if(!finder.isFinished() && !finder.getNoPossibleRoute()){
				RouteFinder newFinder = RouteFinder.load("../src/object");
				finder.updateRouteFinder(newFinder.getMaze(), newFinder.getRouteStack());
				String newMap = finder.getMaze().toString();
				int rowCounter = 0;
				int colCounter = 0;
				for(int i = 0 ; i < newMap.length() ; i++){
					if(newMap.charAt(i) == '\n'){
						rowCounter++;
						colCounter = 0;
					} else if(newMap.charAt(i) == '*'){
						list.get(rowCounter).get(colCounter).setFill(Color.GRAY);
					} else if(newMap.charAt(i) == '!'){
						list.get(rowCounter).get(colCounter).setFill(Color.GREEN);
					} else if(newMap.charAt(i) == '.'){
						Image tileImgg = new Image("../src/maze/visualisation/path.png");
      					ImagePattern tileVieww = new ImagePattern(tileImgg);
						list.get(rowCounter).get(colCounter).setFill(tileVieww);
					}
					if(newMap.charAt(i) != '\n'){
						colCounter++;
					}
				}
			}
		});
		Image img2 = new Image("../src/maze/visualisation/loadButton.png");
      	ImageView view2 = new ImageView(img2);
      	load.setGraphic(view2);
		step.setMinSize(305, 97);
		step.setMaxSize(305, 97);
		step.setStyle("-fx-font-size: 30px; ");
		step.setOnAction(actionEvent ->  {
			if(!finder.getNoPossibleRoute()){
			int stackItemCounterBeforeStep = finder.getRouteStack().size();
			Tile previousTile = null;
			if(stackItemCounterBeforeStep > 0){
				previousTile = finder.getRouteStack().pop();
				finder.getRouteStack().push(previousTile);
				
			}
			finder.step();
			if(finder.getRouteStack().size() == 0){
				Maze.Coordinate entranceCoords = finder.getMaze().getTileLocation(finder.getMaze().getEntrance());
				entranceCoords = finder.getMaze().coordinateConverter(entranceCoords);
				this.list.get(entranceCoords.getY()).get(entranceCoords.getX()).setFill(Color.GRAY);
			} else{
				Tile current = null;
				if(stackItemCounterBeforeStep > finder.getRouteStack().size()){
					current = finder.getRouteStack().pop();
					Maze.Coordinate previousTileCoords = finder.getMaze().getTileLocation(previousTile);
					previousTileCoords = finder.getMaze().coordinateConverter(previousTileCoords);
					Maze.Coordinate currentCoords = finder.getMaze().getTileLocation(current);
					currentCoords = finder.getMaze().coordinateConverter(currentCoords);
					list.get(previousTileCoords.getY()).get(previousTileCoords.getX()).setFill(Color.GRAY);
					list.get(currentCoords.getY()).get(currentCoords.getX()).setFill(Color.GREEN);
					finder.getRouteStack().push(current);
				}else{
					current = finder.getRouteStack().pop();
					if(finder.getRouteStack().size() != 0){
						Tile previous = finder.getRouteStack().pop();
						Maze.Coordinate previousCoords = finder.getMaze().getTileLocation(previous);
						previousCoords = finder.getMaze().coordinateConverter(previousCoords);
						list.get(previousCoords.getY()).get(previousCoords.getX()).setFill(Color.GRAY);
						finder.getRouteStack().push(previous);
					}
					Maze.Coordinate currentCoords2 = finder.getMaze().getTileLocation(current);
					currentCoords2 = finder.getMaze().coordinateConverter(currentCoords2);
					list.get(currentCoords2.getY()).get(currentCoords2.getX()).setFill(Color.GREEN);
					finder.getRouteStack().push(current);
				}
			}
		}
		});
		Image img3 = new Image("../src/maze/visualisation/stepButton.png");
      	ImageView view3 = new ImageView(img3);
      	step.setGraphic(view3);
		maze = mazeVisualisator(finder);
		maze.setAlignment(Pos.CENTER);
		HBox buttons = new HBox();
		buttons.getChildren().addAll(save, step, load);
		buttons.setAlignment(Pos.BOTTOM_CENTER);
		buttons.setSpacing(50);
		VBox root = new VBox();
		root.getChildren().addAll(maze, buttons);
		root.setAlignment(Pos.CENTER);
		root.setSpacing(150);
		return root;
	}


	/**Main method, used to run the application*/
	public static void main(String[] args)
	{
		launch(args);
	}
}
