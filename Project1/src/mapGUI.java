import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;

/**
 * This mapGUI class builds and displays a visual JavaFX window representing the
 * solved maze
 * <ul>
 * <li>Solved lowest-cost route = green boxes</li>
 * <li>Unused routes = white boxes</li>
 * <li>Walls ({@code 'x'}) = red boxes</li>
 * <li>Start ({@code '#'}) = orange box</li>
 * <li>End ({@code '!'}) = purple box</li>
 * </ul>
 * Numbers inside tiles represent their movement cost.
 *
 * @author Alex Michelman
 * @author Tess Ramsey
 * @version 5.0
 */
public class mapGUI extends Application {
    private static finderMap map;
    private static GridPane grid;
    private static ArrayList<ArrayList<Label>> cells; // 2d list of cells
    // creating a position for our mouse '🐭'
    private static int mouseRow = -1; // row the mouse is on (-1 means not placed yet)
    private static int mouseCol = -1; // column the mouse is on

    /**
     * Stores our map into a new test map instance
     * 
     * @param map finderMap instance from finderMap.main
     */
    public static void setMap(finderMap m) {// Non completed map to first display
        map = m;
    }

    /**
     * Style the labels
     * 
     * @param color hex code for bg color
     * @return
     */
    private static String style(String color) {
        return "-fx-border-color: black;"
                + "-fx-background-color: " + color + ";"
                + "-fx-font-size: 18px;";
    }

    /**
     * Re-labels grid cells to the value of in each map tile.
     * 
     * @param row
     * @param col
     */
    private static void restoreText(int row, int col) {
        char tile = map.getPosGui(row, col);

        if (tile == '!') {
            cells.get(row).get(col).setText("🧀");
        } else {
            cells.get(row).get(col).setText(String.valueOf(tile));
        }
    }

    /**
     * Create a timeline using visitOrder
     */
    private static void animateTraversal() {
        ArrayList<int[]> order = map.getVisitOrder();
        Timeline timeline = new Timeline();

        int stopAt = map.getBestRouteEnd();

        for (int i = 0; i < stopAt; i++) {
            int[] pos = order.get(i);

            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(250 * (i + 1)), e -> {
                int r = pos[0]; // row of this step
                int c = pos[1]; // column of this step
                int type = pos[2]; // 1 = stepped onto the cell, 0 = backed off it

                // color the cell (leave the start and end colors alone)
                char tile = map.getPosGui(r, c);
                if (tile != '#' && tile != '!') {
                    if (type == 1) {
                        cells.get(r).get(c).setStyle(style("#FFF59D")); // yellow
                    } else {
                        cells.get(r).get(c).setStyle(style("#E0E0E0")); // gray
                    }
                }

                // take the mouse off the cell it was on
                if (mouseRow != -1) {
                    restoreText(mouseRow, mouseCol);
                }

                // put the mouse on the new cell
                mouseRow = r;
                mouseCol = c;
                cells.get(r).get(c).setText("🐭");
            }));
        }

        timeline.setOnFinished(e -> {
            update(map, true); // draws the green route and resets all text

            // put the mouse on the last cell it visited, which is the cheese
            int[] last = order.get(stopAt - 1);
            cells.get(last[0]).get(last[1]).setText("🐭");
        });
        timeline.play();
    }

    /**
     * Update gui during search process
     */
    public static void update(finderMap m, boolean showPath) {
        map = m;

        ArrayList<ArrayList<String>> routes = map.getCompletedRoutes();// completed routes [[15, (1,0) (1,1) (1,2)
                                                                       // ...
        String shortestPath = "";
        if (!routes.isEmpty()) {
            shortestPath = routes.get(0).get(1);
        }

        for (int row = 0; row < map.getBoxHeight(); row++) {
            for (int col = 0; col < map.getBoxWidth(); col++) { // looping through map to set in grid

                char current = map.getPosGui(row, col); // gets position in our map

                Label cell = cells.get(row).get(col);
                cell.setPrefSize(45, 45);
                cell.setAlignment(Pos.CENTER);

                // Chane color for different types of cells
                if (current == 'x') {
                    cell.setStyle(style("#FF6E6E"));
                } else if (current == '!') {
                    cell.setText("🧀");
                    cell.setStyle(style("#DB6EFF"));
                } else if (current == '#') {
                    cell.setStyle(style("#FFB26E"));
                } else if (showPath && shortestPath.contains("(" + row + "," + col + ")")) {
                    cell.setStyle(style("#72FF70"));
                } else {
                    cell.setStyle(style("white"));
                }
            }
        }
    }

    /**
     * Builds and displays the visual maze grid, highlighting the lowest-cost route.
     * 
     * @param primaryStage the primary window container for this JavaFX application
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Maze Finder");

        // setup grid
        grid = new GridPane(); // creates a new gridPane object

        grid.setAlignment(Pos.CENTER); // alligns the grid to center of window
        grid.setHgap(10); // Horizontal spaceing between grid
        grid.setVgap(10); // vertical spaceing between grid
        grid.setPadding(new Insets(20)); // Adds spaces betwen each object

        cells = new ArrayList<ArrayList<Label>>();

        // populate cells
        for (int row = 0; row < map.getBoxHeight(); row++) {
            ArrayList<Label> row_labels = new ArrayList<>();

            for (int col = 0; col < map.getBoxWidth(); col++) {
                char current = map.getPosGui(row, col); // gets position in our map
                row_labels.add(new Label(String.valueOf(current)));
            }

            cells.add(row_labels);
        }

        // populate gui gridpane
        for (int r = 0; r < cells.size(); r++) {
            for (int c = 0; c < cells.get(r).size(); c++) {
                grid.add(cells.get(r).get(c), c, r);
            }
        }

        // create and show window
        Scene scene = new Scene(grid, 800, 600); // creates our window with pixel size 800x600
        primaryStage.setScene(scene); // sets it as the primary scene (first window opened)
        primaryStage.show(); // display or run the window

        update(map, false); // draw walls, start, end, no path yet
        animateTraversal();

    }

    /**
     * Launches the JavaFX application
     * 
     * @param args command-line arg
     */
    public static void main(String[] args) {
        launch(args);
    }
}
