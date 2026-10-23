import java.util.Arrays;

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


    //  Constructor (takes a inputed string and maps it to a 2d array)
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

        //  looping through row/col and setting a entire row (those seperated by \n) to that row position in our new map
        for(int row = 0; row < rows.length; row++){
            for(int col = 0; col < rows[0].length(); col++) {
                char curr = rows[row].charAt(col);
                this.map[row][col] = curr;
            } 
        }
    }

    //  used to check if pos x, y is a wall or not
    public boolean getPath(int x, int y) {
        if(this.map[x][y] == 'x') {
            return false;
        } else {
            return true;
        }
    }

    //  Getter (if square has been walked on)
    public boolean getVisited(int x, int y) {
        return this.mapExplored[x][y];
    }

    //  Setter (setting mapExplored to walked on if there isnt a wall on position)
    public boolean setVisited(int x, int y) {
        if(getPath(x, y) == true){
            this.mapExplored[x][y] = true;
            return true;
        } else {
            return false;
        }
        
    }

    //  gets current state of the map including where we have visited or have not
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

    public static void main(String[] args) throws Exception {
        String maze = "x!xxxxx\nxpxppp#\nxpppxxx\nxxxxxxx";

        finderMap testMap = new finderMap(maze);

        //  print original map
        System.out.println("Original Map:");
        System.out.println(testMap.getString());

        //  test setVisited (pathway)
        System.out.println("Visit pathway: " + testMap.setVisited(1, 1));

        //  test testVisited (wall)
        System.out.println("Visit wall: " + testMap.setVisited(0, 0));

        //  test getVisted 
        System.out.println("Was visited: " + testMap.getVisited(1, 1));

        System.out.println("Updated Map:");
        System.out.println(testMap.getString());
    }
}
