package maze.routing;

/**Custom exception class inheriting the main Exception class, used for mazes that do not have a route from entrance to exit*/
public class NoRouteFoundException extends Exception{
	/**Constructor, calling the superclass constructor*/
	public NoRouteFoundException(String message){
		super(message);
	}
}