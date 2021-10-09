package maze;

/**Custom exception class inheriting the main Exception class, used to combine all the custom exception classes created for this application*/
public class InvalidMazeException extends Exception{
	/**Constructor, calling the superclass constructor*/
	public InvalidMazeException(String message){
		super(message);
	}
}