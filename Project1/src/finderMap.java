

import java.io.InputStream;
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

    /**
     p = unexplored space
     x = wall/ block that you need to go around
     # = start position
     ! = end position

    +---+---+---+---+---+
    | S |   | ■ |   |   |
    +---+---+---+---+---+
    | ■ |   | ■ |   | ■ |
    +---+---+---+---+---+
    |   |   |   |   | ■ |
    +---+---+---+---+---+
    |   | ■ | ■ |   |   |
    +---+---+---+---+---+
    |   |   |   |   | E |
    +---+---+---+---+---+
    //  String "# p x p p \n x p x p x \n p p p p x \n p x x p p \n p p p x !"
    //  length 29
    **/
   /** 
    * //    hard coded map
    private char[][] map = {
        {'#', 'p', 'x', 'p', 'p'},
        {'x', 'p', 'x', 'p', 'x'},
        {'p', 'p', 'p', 'p', 'x'},
        {'p', 'x', 'x', 'p', 'x'},
        {'p', 'p', 'p', 'x', '!'}
    };
    //  hard coded explored
    private boolean[][] mapExplored = {
        {false, false, false, false, false},
        {false, false, false, false, false},
        {false, false, false, false, false},
        {false, false, false, false, false},
        {false, false, false, false, false}
    };
    **/
   
    private char[][] map; //    This is what the map looks like with start, end, walls, unexplored
    private boolean [][] mapExplored; //    This is a new map keeping track where we have explored

    /**
     * Constructor (takes a inputed string and maps it to a 2d array)
     * @param map // string of chars representing a grid, each row ends in \n
     */
    public finderMap(String map) {
        //  First scrub the input so all lower and no spaces
        map = map.toLowerCase();
        map = map.replace(" ", "");

        //  seperate string into rows ()
        String[] rows = map.split("\n");

        //  initialize both arrays
        this.map = new char[rows.length][rows[0].length()];
        this.mapExplored = new boolean[rows.length][rows[0].length()];

        //  checks if all user inputs contain proper values
        for (int i = 0; i < map.length(); i++) {
            char current = map.charAt(i);
            //  Edge case checker seeing if any invalid characters entered
            if (current != '#' && current != '!' && current != 'p' && current != 'x' && current != '\n') {
                System.out.println("Invalid entry: Inputed String must only contain #, !, p, x, \\n \n Invalid input: " + current); 
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
    public boolean getVisited(int x, int y) {
        return this.mapExplored[x][y];
    }

    /**
     * Sets (x, y) coord of mapExplored to true when visited
     * @param x // x coord
     * @param y // y coord
     * @return // true if can be visited
     */
    public boolean setVisited(int x, int y) {
        if(getPath(x, y) == true){
            this.mapExplored[x][y] = true;
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
                if(this.mapExplored[row][col] == true) { // We are using 'v' to indicate a visited space on new string
                    currentMap.append('v');
                } else {
                    currentMap.append(map[row][col]); //if not visited just return current space
                }
            }
            currentMap.append('\n'); // after each row add '\n' to indent down to next col
        }
        return String.valueOf(currentMap);
    }

    /**
     * Main method currently used for testing map data
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        System.out.println("Working directory: " + System.getProperty("user.dir"));
        StringBuilder maze = new StringBuilder();

        // Read datafile.txt from inside the JAR
        InputStream input = finderMap.class.getResourceAsStream("/datafile.txt");

        if (input == null) {
            System.out.println("ERROR: datafile.txt not found inside JAR.");
            return;
        }

        try (Scanner myReader = new Scanner(input)) {
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);

                maze.append(data).append("\n");
            }
        }
        
        //String maze = "x!xxxxx\nxpxppp#\nxpppxxx\nxxxxxxx";
         
        finderMap testMap = new finderMap(maze.toString());

        //  print original map
        System.out.println("Original Map:");
        System.out.println(testMap.getString());

        //  test setVisited (pathway)
        System.out.println("Visit pathway: " + testMap.setVisited(1, 1));

        //  test setVisited (wall)
        System.out.println("Visit wall: " + testMap.setVisited(0, 0));

        //  test getVisted 
        System.out.println("Was visited: " + testMap.getVisited(1, 1));

        System.out.println("Updated Map:");
        System.out.println(testMap.getString());

        
    }
}
