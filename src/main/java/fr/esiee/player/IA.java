package fr.esiee.player;
import fr.esiee.Board;
import fr.esiee.Box;
import javafx.scene.paint.Color;
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
public class IA extends Player{
    public IA(String name, Color color) {
        super(name, color);
    }

    /*public int eval(){
        if(this.getBoard().isFinished()){
            if(this.haveWin()){
                return 1000;
            }else if(this.wonByOtherPlayer()){
                return -100;
            }else{
                return 0;
            }
        }
        return -3;
    }*/

    @Override
    public boolean play(Board board) {
        //It should not play if the game is up
        if (board.isFinished()) {
            //todo : remove
            System.out.println("game is over ! ");
            return false;
        }

        int randomLine   = (int)(Math.random() * board.dimension());
        int randomColumn = (int)(Math.random() * board.dimension());
        if(board.getBox(randomLine, randomColumn).hasOwner()){
            return play(board);
        }else {
            board.play(randomLine, randomColumn);
        }
        return true;
    }



    public int evaluateProposition(Board board, Box box, int depth){
        if(depth == 0 || board.isFinished())
        {
            return evaluate(board);
        }

/*        int max = -10000;
        int i,j,tmp;

        for(i=0;i<3;i++)
        {
            for(j=0;j<3;j++)
            {
                if(jeu[i][j] == 0)
                {
                    jeu[i][j] = 2;
                    tmp = Min(jeu,profondeur-1);

                    if(tmp > max)
                    {
                        max = tmp;
                    }
                    jeu[i][j] = 0;
                }
            }
        }

        return max;
 */

        return 0;
    }

}
