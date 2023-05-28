package Input;

import DrawPuzzle.Puzzle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * this class implements the MouseListener interface to handle mouse events
 * and provides methods for responding to mouse-related actions like clicks, presses, releases, etc.
 */
public class Mouse implements MouseListener {

    private int X;
    private int Y;
    private final Puzzle pz;

    public Mouse(Puzzle pz) {
        this.pz = pz;
    }

    @Override
    public void mouseClicked(MouseEvent me) {
    }

    /**
     * this method is called when a mouse button is pressed on the component,
     * it updates the X and Y coordinates of the mouse and checks if a puzzle piece is clicked.
     * this part of the whole project took me the longest
     */
    @Override
    public void mousePressed(MouseEvent me) {
        X = me.getX();
        Y = me.getY();
        for (int i = 0; i < pz.getDifficulty(); i++)
            if (pz.positionPieces[i][0] < X && pz.positionPieces[i][1] > X && pz.positionPieces[i][2] < Y && pz.positionPieces[i][3] > Y && pz.noPiece != -1) {
                /**
                 * checks if the current puzzle piece is clicked by comparing the mouse coordinates with the piece's position
                 * if the puzzle piece is clicked, it swaps its position with the clicked piece, updates the necessary variables, and initiates the move
                 */
                if (pz.positionPieces[pz.noPiece][0] < X && pz.positionPieces[pz.noPiece][1] > X && (pz.positionPieces[pz.noPiece][2] - pz.height - 5) < Y && (pz.positionPieces[pz.noPiece][3] - pz.height) > Y) {
                    //swap positions of the current puzzle piece and the clicked piece
                    int piece = pz.orderPieces.get(pz.noPiece);
                    pz.orderPieces.set(pz.noPiece, pz.orderPieces.get(i));
                    pz.orderPieces.set(i, piece);
                    pz.pieceMove = pz.noPiece; //update variables for the move
                    pz.noPiece = i;
                    pz.Ym = pz.positionPieces[i][2];
                    pz.direction = 2;
                    pz.move = true;
                }
                /**
                 * same thing, except it checks the puzzle piece below the current piece
                 * if the lower puzzle is clicked, it swaps its position with the current puzzle
                 */
                else if (pz.positionPieces[pz.noPiece][0] < X && pz.positionPieces[pz.noPiece][1] > X && (pz.positionPieces[pz.noPiece][2] + pz.height + 5) < Y && (pz.positionPieces[pz.noPiece][3] + pz.height) > Y) {
                    // Swap positions of the lower puzzle piece and the current piece
                    int piece = pz.orderPieces.get(pz.noPiece);
                    pz.orderPieces.set(pz.noPiece, pz.orderPieces.get(i));
                    pz.orderPieces.set(i, piece);
                    pz.pieceMove = pz.noPiece;
                    pz.noPiece = i;
                    pz.Ym = pz.positionPieces[i][2];
                    pz.direction = 0;
                    pz.move = true;
                }
                /**
                 * again, same thing, except it checks the puzzle piece left of the current piece
                 * if the left puzzle is clicked, it swaps its position with the current puzzle
                 */
                else if ((pz.positionPieces[pz.noPiece][0] - pz.width - 5) < X && (pz.positionPieces[pz.noPiece][1] - pz.width) > X && pz.positionPieces[pz.noPiece][2] < Y && pz.positionPieces[pz.noPiece][3] > Y) {
                    int piece = pz.orderPieces.get(pz.noPiece);
                    pz.orderPieces.set(pz.noPiece, pz.orderPieces.get(i));
                    pz.orderPieces.set(i, piece);
                    pz.pieceMove = pz.noPiece;
                    pz.noPiece = i;
                    pz.Xm = pz.positionPieces[i][0];
                    pz.direction = 1;
                    pz.move = true;
                }
                /**
                 * and once again, same thing, except it checks the puzzle piece right of the current piece
                 * if the right puzzle is clicked, it swaps its position with the current puzzle
                 */
                else if ((pz.positionPieces[pz.noPiece][0] + pz.width + 5) < X && (pz.positionPieces[pz.noPiece][1] + pz.width) > X && pz.positionPieces[pz.noPiece][2] < Y && pz.positionPieces[pz.noPiece][3] > Y) {
                    int piece = pz.orderPieces.get(pz.noPiece);
                    pz.orderPieces.set(pz.noPiece, pz.orderPieces.get(i));
                    pz.orderPieces.set(i, piece);
                    pz.pieceMove = pz.noPiece;
                    pz.noPiece = i;
                    pz.Xm = pz.positionPieces[i][0];
                    pz.direction = 3;
                    pz.move = true;
                }
                break;
            }
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }
}