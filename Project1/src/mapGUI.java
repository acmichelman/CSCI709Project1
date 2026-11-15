import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * This mapGUI class builds and displays a visual JavaFX window representing the solved maze
 * <ul>
 *   <li>Solved lowest-cost route = green boxes</li>
 *   <li>Unused routes = white boxes</li>
 *   <li>Walls ({@code 'x'}) = red boxes</li>
 *   <li>Start ({@code '#'}) = orange box</li>
 *   <li>End ({@code '!'}) = purple box</li>
 * </ul>
 * Numbers inside tiles represent their movement cost.
 *
 * @author Alex Michelman
 * @author Tess Ramsey
 * @version 5.0
 */
public class mapGUI extends Application {
    private static finderMap testMap;

    /**
     * Stores our map into a new test map instance
     * @param map finderMap instance from finderMap.main
     */
    public static void setMap(finderMap map) {// Non completed map to first display
        testMap = map;
    }

    /**
     * Builds and displays the visual maze grid, highlighting the lowest-cost route.
     * @param primaryStage the primary window container for this JavaFX application
     */
    @Override
    public void start(Stage primaryStage) {
        ArrayList<ArrayList<String>> routes = testMap.getCompletedRoutes();// completed routes [[15, (1,0) (1,1) (1,2)
                                                                           // ...

        primaryStage.setTitle("Maze Finder");

        GridPane grid = new GridPane(); // creates a new gridPane object

        grid.setAlignment(Pos.CENTER); // alligns the grid to center of window
        grid.setHgap(10); // Horizontal spaceing between grid
        grid.setVgap(10); // vertical spaceing between grid
        grid.setPadding(new Insets(20)); // Adds spaces betwen each object

        String shortestPath = "";
        if (!routes.isEmpty()) {
            shortestPath = routes.get(0).get(1);
        }

        for (int row = 0; row < testMap.getBoxHeight(); row++) {
            for (int col = 0; col < testMap.getBoxWidth(); col++) { // looping through map to set in grid

                char current = testMap.getPosGui(row, col); // gets position in our map

                Label cell = new Label(String.valueOf(current)); // Label casts out char in the map to a label object
                                                                 // and sets to grid

                cell.setPrefSize(45, 45); // Convenience method for overriding the region's computed preferred width and
                                          // height.
                cell.setAlignment(Pos.CENTER); // sets back to ccenter

                // test appearence
                if (String.valueOf(current).equals("x")) {
                    cell.setStyle(
                            "-fx-border-color: black;" + // font color
                                    "-fx-background-color: #FF6E6E;" + // background color
                                    "-fx-font-size: 18px;" // size of text
                    );
                } else if (String.valueOf(current).equals("!")) {
                    cell.setStyle(
                            "-fx-border-color: black;" + // font color
                                    "-fx-background-color: #DB6EFF;" + // background color
                                    "-fx-font-size: 18px;" // size of text
                    );
                } else if (String.valueOf(current).equals("#")) {
                    cell.setStyle(
                            "-fx-border-color: black;" + // font color
                                    "-fx-background-color: #FFB26E;" + // background color
                                    "-fx-font-size: 18px;" // size of text
                    );
                } else if (shortestPath.contains("(" + row + "," + col + ")")) {// If this position belongs to the
                                                                                // shortest path
                    cell.setStyle(
                            "-fx-border-color: black;" + // font color
                                    "-fx-background-color: #72FF70;" + // background color
                                    "-fx-font-size: 18px;" // size of text
                    );
                } else {
                    cell.setStyle(
                            "-fx-border-color: black;" + // font color
                                    "-fx-background-color: white;" + // background color
                                    "-fx-font-size: 18px;" // size of text
                    );
                }
                grid.add(cell, col, row);
            }
        }

        Scene scene = new Scene(grid, 800, 600); // creates our window with pixel size 800x600
        primaryStage.setScene(scene); // sets it as the priomary scene (first window opened)
        primaryStage.show(); // display or run the window

    }

    /**
     * Launches the JavaFX application
     * @param args command-line arg
     */
    public static void main(String[] args) {
        launch(args);
    }
}
