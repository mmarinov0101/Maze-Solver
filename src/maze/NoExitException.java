package maze;

/**Custom exception class inheriting InvalidMazeException class, used for mazes with no exit*/
public class NoExitException extends InvalidMazeException{
	/**Constructor, calling the superclass constructor*/
	public NoExitException(String message){
		super(message);
	}
}