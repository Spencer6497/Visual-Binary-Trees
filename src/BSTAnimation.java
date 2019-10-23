/*
    Spencer Peace
    CSC 364-001
    Dr. Jeff Ward

    This program uses BST.java (the definition of the binary search tree) to display a visual representation of the BST
    along with associated functions like insert, delete, search, and tree traversal.

 */


import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class BSTAnimation extends Application {
    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        BST<Integer> tree = new BST<>(); // Create a tree

        BorderPane pane = new BorderPane();
        BTView view = new BTView(tree); // Create a View
        pane.setCenter(view);

        TextField tfKey = new TextField();
        tfKey.setPrefColumnCount(3);
        tfKey.setAlignment(Pos.BASELINE_RIGHT);
        Button btInsert = new Button("Insert");
        Button btDelete = new Button("Delete");

        // Begin project code
        // Add search, inorder, preorder, postorder, breadth-first, and height buttons
        Button btSearch = new Button("Search");
        Button btInorder = new Button("Inorder");
        Button btPreorder = new Button("Preorder");
        Button btPostorder = new Button("Postorder");
        Button btBreadth = new Button("Breadth-first");
        Button btHeight = new Button("Height");

        HBox hBox = new HBox(5);
        hBox.getChildren().addAll(new Label("Enter a key: "),
                tfKey, btInsert, btDelete, btSearch, btInorder, btPreorder, btPostorder, btBreadth, btHeight);
        hBox.setAlignment(Pos.CENTER);
        pane.setBottom(hBox);

        // Set behavior for insert button
        btInsert.setOnAction(e -> {
            int key = Integer.parseInt(tfKey.getText());
            if (tree.search(key)) { // key is in the tree already
                view.displayTree();   // Clears the old status message
                view.setStatus(key + " is already in the tree");
            }
            else {
                tree.insert(key); // Insert a new key
                view.displayTree();
                view.setStatus(key + " is inserted in the tree");
            }
        });

        // Set behavior for delete button
        btDelete.setOnAction(e -> {
            int key = Integer.parseInt(tfKey.getText());
            if (!tree.search(key)) { // key is not in the tree
                view.displayTree();    // Clears the old status message
                view.setStatus(key + " is not in the tree");
            }
            else {
                tree.delete(key); // Delete a key
                for (BST.TreeNode<Integer> i : view.path) { // Check if the deleted key appears in highlight path, if so, clear it, resetting the highlight
                    if(i.element == key) {
                        view.path.clear();
                        break;
                    }
                }
                view.displayTree();
                view.setStatus(key + " is deleted from the tree");
            }
        });

        // Set behavior for search button
        btSearch.setOnAction(e -> {
            int key = Integer.parseInt(tfKey.getText());
            view.path = tree.path(key); // set path variable in BTView equal to the path returned by BST path method
            if (!tree.search(key)) { // if key is not in the tree
                view.displayTree();
                view.setStatus(key + " is not in the tree");
            } else {
                view.displayTree();
                view.setStatus("Found " + key + " in the tree");
            }
        });

        // Create a scene and place the pane in the stage
        Scene scene = new Scene(pane, 750, 250); // Widened scene from 450 width to 750 width to fit buttons
        primaryStage.setTitle("BSTAnimation"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
    }

    /**
     * The main method is only needed for the IDE with limited
     * JavaFX support. Not needed for running from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }
}

