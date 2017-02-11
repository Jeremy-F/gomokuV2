package fr.esiee;
import fr.esiee.player.IA;
import fr.esiee.player.Person;
import fr.esiee.player.Player;
import javafx.application.Application;
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

        GridPane boardGridPane = this.board.getGridPane();

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
        this.board = new Board(4, 4, this);
        Player player1 = new Person("AlexandreCausseBGDeLaNight", Color.WHITE);
        Player player2 = new IA("IA", Color.BLACK);
        board.initializePlayer(player1, player2);

        /*
        Player player1 = new Person("AlexandreCausseBGDeLaNight", Color.WHITE, this.board);
        Player newPlayer = player1.clone();
        System.out.println(newPlayer);
        newPlayer.setName("Salut");
        System.out.println(newPlayer);
        */
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