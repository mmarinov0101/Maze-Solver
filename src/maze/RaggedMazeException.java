package maze;

/**Custom exception class inheriting InvalidMazeException class, used for mazes that have different length of rows*/
public class RaggedMazeException extends InvalidMazeException{
	/**Constructor, calling the superclass constructor*/
	public RaggedMazeException(String message){
		super(message);
	}
}