import maze.*;
import maze.routing.*;
import java.io.File; 
import java.io.FileNotFoundException; 
import java.util.Scanner;
import java.util.List;

/**Class which is mainly used to test the functionality of the application*/
public class MazeDriver {
    /**Main method*/
    public static void main(String args[]) {
    	Maze maze=null;
    	try{
    	maze = Maze.fromTxt("../resources/mazes/maze1.txt");
    	RouteFinder finder = new RouteFinder(maze); 
        while(!finder.isFinished()){
            finder.step();
            System.out.println(finder.getMaze().toString());
            System.out.println("------------------------------------------\n\n");
        }
        System.out.println("Game Over!");
    	}catch(InvalidMazeException e){
    		e.printStackTrace();
    	}
    }
}
