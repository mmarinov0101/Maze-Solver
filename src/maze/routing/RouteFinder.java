package maze.routing;
import maze.Tile;
import maze.Maze;
import java.util.List;
import java.util.Stack;
import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.IndexOutOfBoundsException;

/**Class, which functionality finds the route of a maze from its entrance to its exit, if possible*/
public class RouteFinder implements Serializable{
	private Maze maze;
	private Stack<Tile> route;
	private boolean finished;
	private boolean noPossibleRoute;

	/**Constructor which initializes this.route and fills this.maze, accepts one Maze object */
	public RouteFinder(Maze maze){
		route = new Stack<Tile>();
		this.maze = maze;
	}

	/**Constructor which fills this.maze, this.route and this.finished, accepts one Maze object, one Stack of Tiles and a boolean parameter*/
	public RouteFinder(Maze maze, Stack<Tile> route, boolean finished){
		this.maze = maze;
		this.route = route;
		this.finished = finished;
	}

	/**Returns true or false depending on whether the maze has a possible route from the entrance to the exit or not*/
	public boolean getNoPossibleRoute(){
		return this.noPossibleRoute;
	}

	/**Gets the maze instance of the RouteFinder object*/
	public Maze getMaze(){
		return this.maze;
	}

	/**Gets a list representation of the current route of the player*/
	public List<Tile> getRoute(){
		return this.route;
	}

	/**Returns true of false depending on whether the player has reached the exit tile*/
	public boolean isFinished(){
		return this.finished;
	}

	/**Static method which loads a maze map from a document, accepts a String representation of the path of that document*/
	public static RouteFinder load(String filePath){
		try {
            FileInputStream fileIn = new FileInputStream(filePath);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
 
            Object obj = objectIn.readObject();
 
            objectIn.close();
            return (RouteFinder)obj;
 
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
	}

	/**Saves the current status of the map and the route so far in a document, accepts a String representation of the desired path to that document*/
	public void save(String filePath){
		try {
        	FileOutputStream fileOut = new FileOutputStream(filePath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            RouteFinder obj = new RouteFinder(this.maze, this.route, this.finished);
            objectOut.writeObject(obj);
            objectOut.close();
 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}

	/**Moves the player around the board and updates the board accordingly*/
	public boolean step(){
		if(isFinished()){
			return true;
		} 
		try{
		if(this.noPossibleRoute){
			throw new NoRouteFoundException("There is no possible route to the exit.");
		}
		} catch(NoRouteFoundException e){
					e.printStackTrace();
				}
			Tile current = null;
			if(this.route.size() == 0){
				maze.getEntrance().setState(Tile.State.CURRENT);
				route.push(maze.getEntrance());
				return false;
			}else if(this.route.size() == 1){
				current = route.pop();
			}else {
				current = route.pop();
				Tile previous = route.pop();
				previous.setState(Tile.State.VISITED);
				route.push(previous);

			}
				if(maze.getAdjacentTile(current, Maze.Direction.NORTH) != null){
					if(maze.getAdjacentTile(current, Maze.Direction.NORTH).isNavigable() && maze.getAdjacentTile(current, Maze.Direction.NORTH).getState() != Tile.State.VISITED){
						Tile adjacent = maze.getAdjacentTile(current, Maze.Direction.NORTH);
						if(adjacent.getType() == Tile.Type.EXIT){
							adjacent.setState(Tile.State.CURRENT);
							current.setState(Tile.State.VISITED);
							route.push(current);
							route.push(adjacent);
							this.finished = true;
							return true;
						} else{
							adjacent.setState(Tile.State.CURRENT);
							current.setState(Tile.State.VISITED);
							route.push(current);
							route.push(adjacent);
							return false;
						}
					}
				}
				if(maze.getAdjacentTile(current, Maze.Direction.EAST) != null){
					if(maze.getAdjacentTile(current, Maze.Direction.EAST).isNavigable() && maze.getAdjacentTile(current, Maze.Direction.EAST).getState() != Tile.State.VISITED){
						Tile adjacent = maze.getAdjacentTile(current, Maze.Direction.EAST);
						if(adjacent.getType() == Tile.Type.EXIT){
							adjacent.setState(Tile.State.CURRENT);
							current.setState(Tile.State.VISITED);
							route.push(current);
							route.push(adjacent);
							this.finished = true;
							return true;
						} else{
							adjacent.setState(Tile.State.CURRENT);
							current.setState(Tile.State.VISITED);
							route.push(current);
							route.push(adjacent);
							return false;
						}
					}
				}
				if(maze.getAdjacentTile(current, Maze.Direction.SOUTH) != null){
					if(maze.getAdjacentTile(current, Maze.Direction.SOUTH).isNavigable() && maze.getAdjacentTile(current, Maze.Direction.SOUTH).getState() != Tile.State.VISITED){
						Tile adjacent = maze.getAdjacentTile(current, Maze.Direction.SOUTH);
						if(adjacent.getType() == Tile.Type.EXIT){
							adjacent.setState(Tile.State.CURRENT);
							current.setState(Tile.State.VISITED);
							route.push(current);
							route.push(adjacent);
							this.finished = true;
							return true;
						} else{
							adjacent.setState(Tile.State.CURRENT);
							current.setState(Tile.State.VISITED);
							route.push(current);
							route.push(adjacent);
							return false;
						}
					}
				}
				if(maze.getAdjacentTile(current, Maze.Direction.WEST) != null){
					if(maze.getAdjacentTile(current, Maze.Direction.WEST).isNavigable() && maze.getAdjacentTile(current, Maze.Direction.WEST).getState() != Tile.State.VISITED){
						Tile adjacent = maze.getAdjacentTile(current, Maze.Direction.WEST);
						if(adjacent.getType() == Tile.Type.EXIT){
							adjacent.setState(Tile.State.CURRENT);
							current.setState(Tile.State.VISITED);
							route.push(current);
							route.push(adjacent);
							this.finished = true;
							return true;
						} else{
							adjacent.setState(Tile.State.CURRENT);
							current.setState(Tile.State.VISITED);
							route.push(current);
							route.push(adjacent);
							return false;
						}
					}
				}
				Tile previous = null;
				if(this.route.size() == 0){
					maze.getEntrance().setState(Tile.State.VISITED);
					noPossibleRoute = true;
					return false;
				} else {
					previous = route.pop();
					previous.setState(Tile.State.CURRENT);
					current.setState(Tile.State.VISITED);
					route.push(previous);
					return false;
				}
	}

	/**Gives a String representation of the current state of the map*/
	public String toString(){
		String mazeMap = "";
		for(int i = 0 ; i < maze.getTiles().size() ; i++){
			for(int z = 0 ; z < maze.getTiles().get(i).size() ; z++){
				mazeMap += maze.getTiles().get(i).get(z).toString();
			}
			mazeMap += "\n";
		}
		return mazeMap;
	}

	/**Gives the current state of the route so far, in a Stack representation*/
	public Stack<Tile> getRouteStack(){
		return this.route;
	}

	/**Updates the RouteFinder object with new Maze object and new route*/
	public void updateRouteFinder(Maze maze, Stack<Tile> route){
		this.maze = maze;
		this.route = route;
	}
}