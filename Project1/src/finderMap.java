public class finderMap {

    /**
     p = unexplored space
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
    private char[][] map = {
        {'#', 'p', 'x', 'p', 'p'},
        {'x', 'p', 'x', 'p', 'x'},
        {'p', 'p', 'p', 'p', 'x'},
        {'p', 'x', 'x', 'p', 'x'},
        {'p', 'p', 'p', 'x', '!'}
    };

    private boolean[][] mapExplored = {
        {true, false, false, false, false},
        {false, false, false, false, false},
        {false, false, false, false, false},
        {false, false, false, false, false},
        {false, false, false, false, false}
    };

        //  constructor
        //String "# p x p p \n x p x p x \n p p p p x \n p x x p p \n p p p x !"
    public finderMap(String map) {

    }

    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
    }
}
