package maze;

/**Custom exception class inheriting InvalidMazeException class, used for mazes that have multiple exits*/
public class MultipleExitException extends InvalidMazeException{
	/**Constructor, calling the superclass constructor*/
	public MultipleExitException(String message){
		super(message);
	}
}