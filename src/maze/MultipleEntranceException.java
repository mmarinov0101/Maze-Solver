package maze;

/**Custom exception class inheriting InvalidMazeException class, used for mazes that have multiple entrances*/
public class MultipleEntranceException extends InvalidMazeException{
	/**Constructor, calling the superclass constructor*/
	public MultipleEntranceException(String message){
		super(message);
	}
}