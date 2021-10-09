package maze;

/**Custom exception class inheriting InvalidMazeException class, used for mazes with no entrance*/
public class NoEntranceException extends InvalidMazeException{
	/**Constructor, calling the superclass constructor*/
	public NoEntranceException(String message){
		super(message);
	}
}