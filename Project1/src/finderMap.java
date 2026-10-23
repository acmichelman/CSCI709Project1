import java.util.Arrays;

public class finderMap {

    /**
     p = unexplored space
     v = explored space
      = your current location
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
    private char[][] map;
    private boolean [][] mapExplored;


        //  Constructor
        //  String "# p x p p \n x p x p x \n p p p p x \n p x x p p \n p p p x !"
        //  length 29
    public finderMap(String map) {
        //  First scrub the input so all lower and no spaces
        map = map.toLowerCase();
        map = map.replace(" ", "");

        for (int i = 0; i < map.length(); i++) {
            char current = map.charAt(i);

            //  Edge case checker seeing if any invalid characters entered
            if (current != '#' && current != '!' && current != 'p' && current != 'x' && current != '\n') {
                System.out.println("Invalid entry: Inputed String must only contain #, !, p, x, \\n \n Invalid input: " + current); 
            }
        }

        //  find array size and set it
        int size = map.length();
        double sqrRoot = Math.sqrt(size);
        size =  Integer.valueOf((int) sqrRoot);
        this.map = new char[size][size];

        int row = 0, col = 0;
        //  looks through map and sets values at correct points in new created map
        for(int i = 0; i < map.length(); i++) {
            if(map.charAt(i) != '\n') { //  When we see new line instead increment row
                this.map[row][col] = map.charAt(i);
                col++;
            } else {
                col = 0;
                row++;
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

    //  Setter (setting it to walked on)
    public boolean setVisited(int x, int y) {
        return this.mapExplored[x][y] = true;
    }

    //  gets current state of the map including where we have visited or have not
    public String getString() {
        StringBuilder currentMap = new StringBuilder();
        for(int row = 0; row < this.map.length; row++) {
            for(int col = 0; col < this.map[row].length; col++) {
                if(this.mapExplored[row][col] == true) {
                    currentMap.append('v');
                } else {
                    currentMap.append(map[row][col]);
                }
            }
            currentMap.append('\n');
        }
        return String.valueOf(currentMap);
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        String maze = "# p x p p \n x p x p x \n p p p p x \n p x x p p \n p p p x !";//    test str
        finderMap testMap = new finderMap(maze);

        System.out.println(Arrays.deepToString(testMap.map)); //test findermap
    }
}
