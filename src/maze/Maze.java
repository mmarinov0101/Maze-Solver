package maze;
import java.util.List;
import java.util.ArrayList;
import java.io.File; 
import java.io.FileNotFoundException; 
import java.util.Scanner;
import java.lang.IndexOutOfBoundsException;
import java.io.Serializable;
/** Class that stores the structure of the maze 
*	@author Martin Marinov
*	@version April 2021 
*/
public class Maze implements Serializable{
	private Tile entrance;
	private Tile exit;
	private List<List<Tile>> tiles;

	public enum Direction{
		NORTH, SOUTH, EAST, WEST
	}

	/** Stores two-dimensional coordinates X and Y in the format (x, y) */
	public class Coordinate implements Serializable{
		private int x;
		private int y;

		/**Constructor with parameters, fills the values of the coordinates 
		*	@param x: Used to fill the class instance x
		*	@param y: Used to fill the class instance y
		*/
		public Coordinate(int x, int y){
			this.x = x;
			this.y = y;
		}

		/**Gets the value of instance x
		*	@return The value which is stored in the instance x
		*/
		public int getX() {
			return this.x;
		}

		/**Gets the value of instance y
		* @return The value which is stored in the instance y
		*/
		public int getY() {
			return this.y;
		}

		/** Gives a string representation of the two coordinates x and y
		* @return Coordinates in the format (x, y)
		*/
		public String toString() {
			return "(" + this.x + ", " + this.y + ")";
		}
	}

	/**Constructor, used to initialize the ArrayList of Tile objects for the class*/
	private Maze() {
		this.tiles = new ArrayList<List<Tile>>();
	}

	/** Reads a string representation of a maze and creates a Maze object, filled according to the given string
	*	@param file: A string from which the method reads to create a corresponding Maze version of it
	*	@return A filled Maze object
	*/
	public static Maze fromTxt(String filePath) throws InvalidMazeException {
		String data = "";
    	try{
    		File file = new File(filePath);
    		Scanner myReader = new Scanner(file);
    		while(myReader.hasNextLine()){
    			data += myReader.nextLine();
    			data += "\n";
    		}
    	} catch (FileNotFoundException e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
		}
		Maze maze = new Maze();
		List<Tile> row = new ArrayList<Tile>();
		int numOfColumns = 0;
		boolean firstRowIteration = true;
		int entranceCounter = 0;
		int exitCounter = 0;
		Tile potentialExit = null;
		Tile potentialEntrance = null;
		for(int i = 0 ; i < data.length(); i++){
			if(data.charAt(i) != '\n'){
				Tile item = Tile.fromChar(data.charAt(i)); 
				row.add(item);
					if(data.charAt(i) == 'e'){
						potentialEntrance = item;
						entranceCounter++;
					} else if(data.charAt(i) == 'x'){
						potentialExit = item;
						exitCounter++;
					} else if(data.charAt(i) != '.' && data.charAt(i) != '#' && data.charAt(i) != '!' && data.charAt(i) != '*'){
						throw new InvalidMazeException("Invalid character for maze!");
					}
				if(firstRowIteration){
					numOfColumns++;
				}else continue;
			} else{
				maze.tiles.add(row);
				if(row.size() != numOfColumns){
					throw new RaggedMazeException("The maze is ragged!");
				}
				row = new ArrayList<Tile>();
				firstRowIteration = false;
			}
		}
		if(entranceCounter == 0){
			throw new NoEntranceException("There is no entrance!");
		} else if(entranceCounter > 1){
			throw new MultipleEntranceException("Too many entrances!");
		} else if(entranceCounter == 1){
			maze.setEntrance(potentialEntrance);
		}
		if(exitCounter == 0){
			throw new NoExitException("There is no exit!");
		} else if(exitCounter > 1){
			throw new MultipleExitException("Too many exits!");
		} else if(exitCounter == 1){
			maze.setExit(potentialExit);
		}
		return maze;
	}

	/** Gives a neighboring tile
	*	@param tile: The tile around which the tile we are looking for is located
	*	@param direction: The direction in which we are looking for from the original tile
	*	@return A new tile, which is next to the original tile
	*/
	public Tile getAdjacentTile(Tile tile, Direction direction){
		Coordinate coords = getTileLocation(tile);
		try{
			if(direction == Direction.NORTH){
				return getTileAtLocation(new Coordinate(coords.getX(), coords.getY() + 1));
			} else if(direction == Direction.WEST){
				return getTileAtLocation(new Coordinate(coords.getX() - 1, coords.getY()));
			} else if(direction == Direction.EAST){
				return getTileAtLocation(new Coordinate(coords.getX() + 1, coords.getY()));
			} else {
				return getTileAtLocation(new Coordinate(coords.getX(), coords.getY() - 1));
			}
		}catch (IndexOutOfBoundsException e){
			return null;
		}
	}

	/** @return The tile which is an entrance of the maze */
	public Tile getEntrance() {
		return this.entrance;
	}

	/** @return The tile which is an exit of the maze */
	public Tile getExit() {
		return this.exit;
	}

	/** Searching for a tile based on unique coordinates
	*	@param coordinates: The coordinates which are used to search for the tile
	* 	@return The tile which is on the coordinates given 
	*/
	public Tile getTileAtLocation(Coordinate coordinates){
		return tiles.get(this.tiles.size()-coordinates.getY()-1).get(coordinates.getX());
	}

	/** Given a specific tile, returns its coordinates
	*	@param tile: The tile which coordinates are being looked for
	*	@return The coordinates of the given tile
	*/
	public Coordinate getTileLocation(Tile tile) {
		for(int i = 0 ; i < tiles.size() ; i++){
			for(int z = 0 ; z < tiles.get(i).size() ; z++){
				if(tiles.get(i).get(z) == tile){
					return new Coordinate(z, this.tiles.size()-i-1);
				}
			}
		}
		return null;
	}

	/** @return All tiles in a two dimensional ArrayList*/
	public List<List<Tile>> getTiles() {
		return this.tiles;
	}

	/**Sets the value to the attribute entrance*/
	private void setEntrance(Tile entrance) throws MultipleEntranceException {
		if(this.entrance == null){
			boolean belongs = false;
			for(int i = 0 ; i < tiles.size() ; i++){
				for(int z = 0 ; z < tiles.get(i).size() ; z++){
					if(tiles.get(i).get(z) == entrance){
						belongs = true;
					}
				}
			}
			if(belongs){this.entrance = entrance;}
		}
		else throw new MultipleEntranceException("Too many entrances!");
	}

	/**Sets the value to the attribute exit*/
	private void setExit(Tile exit) throws MultipleExitException {
		if(this.exit == null){
			boolean belongs = false;
			for(int i = 0 ; i < tiles.size() ; i++){
				for(int z = 0 ; z < tiles.get(i).size() ; z++){
					if(tiles.get(i).get(z) == exit){
						belongs = true;
					}
				}
			}
			if(belongs){this.exit = exit;}
		}
		else throw new MultipleExitException("Too many exits!");
	}

	/**Gives a string representation of the current state of the maze*/
	public String toString() {
		String maze = "";
		for(int i = 0 ; i < tiles.size() ; i++){
			for(int z = 0 ; z < tiles.get(i).size() ; z++){
				maze += tiles.get(i).get(z).toString();
			}
			maze += "\n";
		}
		return maze;
	}

	/**Converts coordinates from the way we want them, to the way the maze is structured in the ArrayList so we can access the correct element*/
	public Coordinate coordinateConverter(Coordinate coordinates){
		return new Coordinate(coordinates.getX(), this.tiles.size()-coordinates.getY()-1);
	}
}