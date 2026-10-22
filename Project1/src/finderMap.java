public class finderMap {

    /**
     . = unexplored space
     C = your current location
     # = wall/ block that you need to go around
     S = start position
     E = end position

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
    private char[][] maze = {
        {'S', '.', '#', '.', '.'},
        {'#', '.', '#', '.', '#'},
        {'.', '.', '.', '.', '#'},
        {'.', '#', '#', '.', '.'},
        {'.', '.', '.', '#', 'E'}
    };

    public finderMap(String mazeString) {

    }

    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
    }
}
