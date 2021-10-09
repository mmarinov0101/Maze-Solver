package maze;
import java.io.Serializable;

/**Class providing the maze container with objects to fill with*/
public class Tile implements Serializable{

	/**Specifies the type of the tile*/
	public enum Type{
		CORRIDOR, ENTRANCE, EXIT, WALL
	}

	/**Specifies whether the tile has ever been visited or not*/
	public enum State{
		CURRENT, VISITED
	}

	/**Instance to store the type of the tile*/
	private Type type;

	/**Instance to store the current state of the tile*/
	private State state;

	/**Constructor which only fills this.type, accepts one Type object*/
	private Tile(Type type){
		this.type = type;
	}

	/**Constructor which fills both this.type and this.state, accepts one Type and one State object*/
	private Tile(Type type, State state){
		this.type = type;
		this.state = state;
	}

	/**Static method which creates a Tile object based on the character which the method has been provided with*/
	protected static Tile fromChar(char character){
		if(character == 'e'){
			return new Tile(Type.ENTRANCE, null);
		} else if(character == 'x'){
			return new Tile(Type.EXIT, null);
		} else if(character == '#'){
			return new Tile(Type.WALL, null);
		} else if(character == '.'){
			return new Tile(Type.CORRIDOR, null);
		} else if(character == '*'){
			return new Tile(Type.CORRIDOR, State.VISITED);
		} else if(character == '!'){
			return new Tile(Type.CORRIDOR, State.CURRENT);
		}
		return null;
	}

	/**Gets the type of the Tile object*/
	public Type getType() {
		return this.type;
	}

	/**Gets the state of the Tile object*/
	public State getState(){
		return this.state;
	}

	/**Sets the type of the Tile object*/
	public void setType(Type type){
		this.type = type;
	}

	/**Sets the state of the Tile object*/
	public void setState(State state){
		this.state = state;
	}

	/**Returns true of false based on whether the Tile can be passed through or not*/
	public boolean isNavigable() {
		if(this.type == Type.WALL){
			return false;
		} else {
			return true;
		}
	}

	/**Gives a String representation of the Tile object*/
	public String toString() {
		if(this.type == Type.ENTRANCE){
			return "e";
		} else if(this.type == Type.EXIT){
			return "x";
		} else if(this.type == Type.WALL){
			return "#";
		} else if(this.type == Type.CORRIDOR && this.state == null){
			return ".";
		} else if(this.type == Type.CORRIDOR && this.state == State.CURRENT){
			return "!";
		} else if(this.type == Type.CORRIDOR && this.state == State.VISITED){
			return "*";
		}
		return null;
	}
}