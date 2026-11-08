import java.util.Arrays;

import java.io.File;                  // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.util.Scanner;             // Import the Scanner class to read text files

/**
 * Goal Set 1: Build the data structure
 * This program takes a string of chars representing a map and creates two 2D-arrays
 * One to store the paths, walls, start, and end (what can be visited)
 * Another to store wether or not a location has been visited
 * finderMap
 * 
 * @author Alex Michelman (Code)
 * @author Tess Ramsey (Documentation)
 * @version 1.0
 */
public class finderMap {
   
    private char[][] map; //    This is what the map looks like with start, end, walls, unexplored
    private int [][] mapExplored; //    This is a new map keeping track where we have explored
    int stepsTakenFinal = 0; // count of steps taken to get to end
    boolean STATE_hitEnd = false; //    used in map traversal so we stop once hitting the end
    

    /**
     * Constructor (takes a inputed string and maps it to a 2d array)
     * @param map // string of chars representing a grid, each row ends in \n
     */
    public finderMap(String map) {
        //  First scrub the input so all lower and no spaces
        map = map.replace(" ", "");

        //  seperate string into rows ()
        String[] rows = map.split("\n");

        //  initialize both arrays
        this.map = new char[rows.length][rows[0].length()];
        this.mapExplored = new int[rows.length][rows[0].length()];
        

        //  checks if all user inputs contain proper values
        for (int i = 0; i < map.length(); i++) {
            char current = map.charAt(i);
            //  Edge case checker seeing if any invalid characters entered
            if (current != '#' && current != '!' && current != 'p' && current != 'x' && current != '\n' && //TODO should make this look better
                current != '0' && current != '1' && current != '2' && current != '3' && current != '4' &&
                current != '5' && current != '6' && current != '7' && current != '8' && current != '9' &&
                current != 'A' && current != 'B' && current != 'C' && current != 'D' && current != 'E' &&
                current != 'F') {
                System.out.println("Invalid entry: Inputed String must only contain #, !, p, x, and a number \\n \n Invalid input: " + current); 
            }
        }

        //  populate map: loop through row/col and set values in our array
        for(int row = 0; row < rows.length; row++){
            for(int col = 0; col < rows[0].length(); col++) {
                char curr = rows[row].charAt(col);
                this.map[row][col] = curr;
                    
            } 
        }
    }

    /**
     * Checks if (x, y) coord is a path or not
     * @param x // x coord
     * @param y // y coord
     * @return // true if path
     */
    public boolean getPath(int x, int y) {
        // "x" is a wall
        if(this.map[x][y] == 'x') {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Checks if an (x, y) coord has been visisted
     * @param x // x coord
     * @param y // y coord
     * @return // true if visited
     */
    public int getVisited(int x, int y) { 
        return this.mapExplored[x][y];
    }

    /**
     * Sets (x, y) coord of mapExplored to true when visited
     * @param x // x coord
     * @param y // y coord
     * @param visitedNum // what step your at in your travle ()
     * @return // true if has be visited
     */
    public boolean setVisited(int x, int y, int visitedNum) { 
        if(getPath(x, y) == true){
            this.mapExplored[x][y] = visitedNum + 1;
            return true;
        } else {
            return false;
        }
        
    }

    /**
     * Gets current state of our map, marking off visited locations as 'v'
     * @return // a string representing the values in our current map
     */
    public String getString() {
        
        StringBuilder currentMap = new StringBuilder();
        for(int row = 0; row < this.map.length; row++) {
            for(int col = 0; col < this.map[row].length; col++) {
                
                if(this.mapExplored[row][col] != 0) { // We are using 'v' to indicate a visited space on new string
                    currentMap.append(mapExplored[row][col]);
                    
                } else {
                    currentMap.append('x'); //if not visited just return current space
                }
            }
            currentMap.append('\n'); // after each row add '\n' to indent down to next col
            
        }
        return String.valueOf(currentMap);
    }

    /**
     * Flood fills the map
     * @param x // x coord
     * @param y // y coord
     * @return // t/f is program continues
     */
    public boolean floodFill(int x, int y, int steps) { //int currCost
        // check if out of bound of our maps
        if (x < 0 || x >= this.map.length || y < 0 || y >= this.map[0].length) {
            return false;
        }

        // return if a wall
        if (!getPath(x, y)) {
            return false;
        }
        
        // return if already visited
        if (getVisited(x, y) != 0) {
            return false;
        }

        // mark visited
        setVisited(x, y, steps); 

        //  testing getCost
        System.out.println("Curr Cost at " + x + " : " + y + " = " + getCost(x, y));

        // fill up
		if(floodFill(x - 1, y, steps + 1)) return true; 

		// fill down
		if(floodFill(x + 1, y, steps + 1)) return true;

		// fill left
		if(floodFill(x, y - 1, steps + 1)) return true;

		// fill right
		if(floodFill(x, y + 1, steps + 1)) return true;

        if(this.map[x][y] != '!' && STATE_hitEnd == false) { // We have a bool to check if it hits the end so it doesnt remove anything once at end
            steps--;
            this.mapExplored[x][y] = 0; //  This erases any space we backtraced
        }
        if(this.map[x][y] == '!') {
            stepsTakenFinal = steps + 1; 
            STATE_hitEnd = true;
        }

        return false;
    }

    /**
     * Gets cost in space the map
     * @param x // x coord
     * @param y // y coord
     * @return // cost at current x, y cord
     */
    public int getCost(int x, int y) {
        if(this.map[x][y] == '!' || this.map[x][y] == '#') {
            return 0;
        }
        char temp = this.map[x][y];
        int cost = Integer.valueOf(String.valueOf(temp), 16);
        return cost;
    }

    //  Small helper method which will find '#' in dataFile.txt and set that as our starting point
    public void mapTraversal() {
    for (int row = 0; row < this.map.length; row++) {
        for (int col = 0; col < this.map[row].length; col++) {
            if (this.map[row][col] == '#') {

                //  will call floodFill and start there
                floodFill(row, col, 0);
                System.out.println("STEPS TAKEN: " + stepsTakenFinal + "\n");
                //  Stop search after finding start
                return;
                }
            }
        }
    }

    /**
     * Main method currently used for testing map data
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        System.out.println("Working directory: " + System.getProperty("user.dir"));
        File myObj = new File("datafile.txt");
        StringBuilder maze = new StringBuilder();

        // try-with-resources: Scanner will be closed automatically
        try (Scanner myReader = new Scanner(myObj)) {
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            System.out.println(data);
            
            //  this will add a /n after each line read in .txt file  
            maze.append(data);
            maze.append("\n");
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
         
        finderMap testMap = new finderMap(maze.toString());

        //  print original map
        System.out.println("\nUnsolved Map:");
        System.out.println(testMap.getString());

        //  Helper func which finds '#' to start program
        testMap.mapTraversal(); //---------------------------WHERE PROGRAM BEGINS REALLY--------------------
        System.out.println("Flood-filled Map:");
        System.out.println(testMap.getString());

    }
}
