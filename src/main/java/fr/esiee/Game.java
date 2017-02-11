package fr.esiee;
import fr.esiee.player.IA;
import fr.esiee.player.Person;
import fr.esiee.player.Player;
import fr.esiee.player.SmartIAV1;
import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 *****************************************************
 * ,----.     E3T - Esiee Paris      ,--.            *
 * '  .-./    ,---. ,--,--,--. ,---. |  |,-.,--.,--. *
 * |  | .---.| .-. ||        || .-. ||     /|  ||  | *
 * '  '--'  |' '-' '|  |  |  |' '-' '|  \  \'  ''  ' *
 * `------'  `---' `--`--`--' `---' `--'`--'`------' *
 *    Alexandre Causse            Jérémy Fornarino   *
 *****************************************************
 * @author Alexandre Causse & Jérémy Fornarino   [E3T]
 */
public class Game extends Application {


    private ObservableList<Player> players;
    private Board board;
    private GridPane gridPane;

    /**
     * Begins creating the application after initialization ({@link Game#init()})
     *
     * @param primaryStage The First Stage (The Window)
     * @throws Exception Error
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Gomoku C&F xX");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Game.class.getResource("view/RootLayout.fxml"));

        BorderPane borderPane = (BorderPane) loader.load();

        GridPane boardGridPane = this.gridPane;

        borderPane.setCenter(boardGridPane);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    /**
     * Initialization of the game
     * - Create a board
     * - Create two player
     * - Adding this two players to the board
     *
     * @throws Exception Error
     */
    @Override
    public void init() throws Exception {
        super.init();
        Player player1 = new Person("AlexandreCausseBGDeLaNight", Color.WHITE);
        //Player player2 = new IA("IA", Color.BLACK);
        Player player2 = new SmartIAV1("SmartIAV1", Color.BLACK);
        this.board = new Board(4, 4);
        board.initializePlayer(player1, player2);

        this.initializeGridPane();

    }

    private void initializeGridPane(){
        this.gridPane = new GridPane();
        this.gridPane.setGridLinesVisible(true);
        for (int line = 0; line < this.board.dimension(); line++) {
            for (int column = 0; column < this.board.dimension(); column++) {
                final SimpleObjectProperty<Box> propertyBox = this.board.getBoxProperty(line, column);
                propertyBox.get().ownerProperty().addListener((observable, oldValue, newValue) -> this.updateGridPane());
            }
        }
        updateGridPane();
    }

    /**
     *
     */
    private void updateGridPane() {

        for (int line = 0; line < this.board.dimension(); line++) {
            for (int column = 0; column < this.board.dimension(); column++) {

                final Node boxNode = this.board.getBox(line, column).toNode();
                this.gridPane.add(boxNode,column,line);

                final int finalLine = line;
                final int finalColumn = column;

                boxNode.setOnMouseClicked(event -> this.board.play(finalLine, finalColumn));
            }
        }
    }


    /**
     * Update the gridpane
     * That's the GUI representation of the Board
     * @return The current object
     *
    private Game updateGridPane() {

        for (int line = 0; line < this.board.dimension(); line++) {
            for (int column = 0; column < this.board.dimension(); column++) {


                final Node boxNode = this.board.getBox(line, column).toNode();

                this.gridPane.add(boxNode,column,line);

                int finalLine = line;
                int finalColumn = column;

                boxNode.setOnMouseClicked(event -> this.play(finalLine, finalColumn));
            }
        }
        return this;
    }*/
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);

    }
}