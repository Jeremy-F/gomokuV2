package fr.esiee;
import fr.esiee.player.Player;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

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
public class Board {




	private ArrayList<ArrayList<SimpleObjectProperty<Box>>> boxes;
	private ArrayList<Player> players;

	private Game game;
	private Player currentPlayer;
	private GridPane gridPane;
	private int winningNumber;

    /**
     * Constructor allowing to create a board without {@link Game}
     * @param size The size of the board size * size
     */
    private Board(int size, int winningNumber) {
        this.winningNumber = winningNumber;
        this.boxes = new ArrayList<>();
        this.players = new ArrayList<>();

        this.gridPane = new GridPane();
        this.gridPane.setGridLinesVisible(true);

        this.initializeBoxes(size);
        this.updateGridPane();
    }

    /**
     * Update the gridpane
     * That's the GUI representation of the Board
     * @return The current object
     */
    private Board updateGridPane() {

        for (int line = 0; line < this.getBoxes().size(); line++) {
            for (int column = 0; column < this.getBoxes().size(); column++) {


                final Node boxNode = this.getBox(line, column).toNode();

                this.gridPane.add(boxNode,column,line);

                int finalLine = line;
                int finalColumn = column;

                boxNode.setOnMouseClicked(event -> this.play(finalLine, finalColumn));
            }
        }
        return this;
    }

    /**
     * Constructor allowing to create a board with {@link Game}
     * @param size The size of the board size * size
     * @param game The Game
     */
    public Board(int size, int winningNumber, Game game) {
        this(size, winningNumber);
        this.game = game;
    }

    /**
     * Created all the boxes, and initializes them.
     * @param size The size of the board size * size
     * @return Board Current object
     */
    private Board initializeBoxes(int size){
        for(int i = 0; i < size; i++){
            ArrayList<SimpleObjectProperty<Box>> boxesLine = new ArrayList<>();
            for(int j = 0; j < size; j++){
                SimpleObjectProperty<Box>  propertyBox= new SimpleObjectProperty<>(new Box());
                propertyBox.get().ownerProperty().addListener((observable, oldValue, newValue) -> this.updateGridPane());
                boxesLine.add(propertyBox);
            }
            this.boxes.add(boxesLine);
        }
        return this;
    }

    /**
     * Initialize the player on the board, limited for only two player.
     * @param firstPlayer The first play
     * @param secondPlayer The second play
     * @return Board Current object
     */
    public Board initializePlayer(Player firstPlayer, Player secondPlayer){
        Player[] players = {firstPlayer, secondPlayer};
        return this.initializePlayer( players );
    }
    /**
     * Initialize the player on the board, without limit.
     * @param players A list of all players. The board can contain x player
     * @return Board Current object
     */
    private Board initializePlayer(Player... players){
        for(Player player : players){
            this.players.add(player);
        }
        this.setCurrentPlayer(this.players.get(0));
        this.currentPlayer.play();
        return this;
    }

    /**
     * Select one box
     * @param line of the box (i)
     * @param column of the box (j)
     * @return A SimpleObjectProperty with the {@link Box} inside
     */
    public SimpleObjectProperty<Box> getSimpleBox(int line, int column){
        return this.getBoxes().get(line).get(column);
    }

    /**
     * Select one box
     * @param line of the box (i)
     * @param column of the box (j)
     * @return The {@link Board} object corresponding
     */
    public Box getBox(int line, int column){
        return this.getSimpleBox(line,column).get();
    }
    /**
     * Changes the current user
     * @param currentPlayer The new currentPlayer
     * @return Board Current object
     */
    public Board setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
        return this;
    }

    /**
     * // TODO La javadoc de generic scan
     * @param line
     * @param column
     * @param dLine
     * @param dColumn
     * @return
     */
    public Alignment genericScan(int line, int column, int dLine, int dColumn){
        Alignment alignment = new Alignment(this.winningNumber);
        int size = this.getBoxes().size();
        for (int currentLine = line, currentColumn = column;
                currentLine >= 0 && currentLine < size && currentColumn < size && currentColumn >= 0;
                currentLine += dLine, currentColumn +=dColumn){
            alignment.add(getBox(currentLine, currentColumn));
        }
        return alignment;
    }
    /**
     * Returns the line according to the given number as a parameter
     * @param lineNumber The number of the line needed
     * @return An ArrayList with all the {@link Box} on the line
     */
    public Alignment getBoxLine(int lineNumber){
        return genericScan(lineNumber, 0, 0, 1);
    }

    /**
     * Return the column according to the given number as a parameter
     * @param columnNumber The number of the column needed
     * @return An ArrayList with all the {@link Box} on the column
     */
    public Alignment getBoxColumn(int columnNumber){
        return genericScan(0, columnNumber, 1, 0);
    }

    /**
     * Return the diagonal North-West to South-Est according to the given starting {@link Box}
     * @param line The line number of the first box
     * @param column The line column of the first box
     * @return The Diagonal {@link ArrayList<Box>}
     */
    public Alignment getDiagonalNwToSe(int line, int column){
        return genericScan(line,column, 1, 1);
    }
    /**
     * Return the diagonal South-West to North-Est according to the given starting {@link Box}
     * @param line The line number of the first box
     * @param column The line column of the first box
     * @return The Diagonal {@link ArrayList<Box>}
     */
    public Alignment getDiagonalSwToNe(int line, int column){
        return genericScan(line,column, -1, 1);
    }

    /**
     * Give all the possible alignement
     * @return A list containing all the alignment
     */
    public ArrayList<Alignment> getAllAlignment(){
        // Creating the Output Table
        ArrayList<Alignment> allAlignment = new ArrayList<>();
        int size = this.getBoxes().size();

        //Add line and columns
        for(int i = 0; i < size; i++) {
            // Add Column
            allAlignment.add(this.getBoxColumn(i));
            // Add Line
            allAlignment.add(this.getBoxLine(i));
        }
        //Add all diagonal
        for(int line = 0 ; line < size; line++){
            for(int column = 0; column < size; column++){
                //We need only the top, left and bottom lines.
                if ( (line == 0 || column == 0) || line == size - 1) {
                    allAlignment.add(this.getDiagonalNwToSe(line,column));
                    allAlignment.add(this.getDiagonalSwToNe(line,column));
                }
            }
        }
        // We remove all the Alignment below the minimum size
        allAlignment.removeIf(alignment -> alignment.size() < this.winningNumber);
        // We return the output table
        return allAlignment;
    }
    /**
     * Getter, return all boxes {@link Board#boxes}
     * @return ArrayList of ArrayList of SimpleObjectProperty {@link Box} :)
     */
    public ArrayList<ArrayList<SimpleObjectProperty<Box>>> getBoxes() {
        return boxes;
    }

    /**
     * Getter, return all the {@link }
     * @return All a list of all the {@link Player}
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * The current player
     * @return The current {@link Board#currentPlayer}
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    public int getIndexPlayer(Player player){
        for(int i = 0; i < this.players.size(); i++){
            if(this.players.get(i).equals(player)){
                return i;
            }
        }
        return -1;
    }

    /**
     * Return the next {@link Player}
     * without changing the {@link Board#currentPlayer}
     * @return Player The next player
     */
    public Player getNextPlayer(){
        int currentIndexPlayer = this.getIndexPlayer(this.getCurrentPlayer());
        return this.getPlayers().get( (currentIndexPlayer > this.getPlayers().size() - 2)?0:(currentIndexPlayer+1) );
    }
    /**
     * Play
     * @param line The Line
     * @param column The column
     * @return True if you can play on this box, else false
     */
    public boolean play(int line, int column) {
        Box box = this.getBox(line, column);
        if(box.haveOwner()){
            return false;
        }
        box.setOwner(this.getCurrentPlayer());
        this.currentPlayer = this.getNextPlayer();

        // On donne l amain au prochain joueur
        this.currentPlayer.play();

        //Board.affiche(getDiagonalSE(1,2));
        return true;
    }
    // TODO getEmptyBoxes à revoir et surtout à repenser
    public ArrayList<ArrayList<Box>> getEmptyBoxes(){
        ArrayList<ArrayList<Box>> emptyBoxes = new ArrayList<>();
        for(int line = 0; line < this.getBoxes().size(); line++){
            ArrayList<Box> lineO = new ArrayList<>();
            emptyBoxes.add(lineO);
            for(int column = 0; column < this.getBoxes().size(); column++){
                Box box = this.getBox(line, column);
                if(box.haveOwner()){
                    lineO.add(column, null);
                }else{
                    lineO.add(column, box);
                }
            }
        }
        return emptyBoxes;
    }
    /**
     * The gridPane who represent the Board
     * @return a GridPane representing the board
     */
    public GridPane getGridPane() {
        return gridPane;
    }
}