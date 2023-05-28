package Input;

import DrawPuzzle.Puzzle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Mouse implements MouseListener
{

    private int X;
    private int Y;
    private final Puzzle pz;

    public Mouse(Puzzle pz)
    {
        this.pz = pz;
    }

    @Override
    public void mouseClicked(MouseEvent me)
    {
    }

    @Override
    public void mousePressed(MouseEvent me)
    {
        X = me.getX();
        Y = me.getY();
        for (int i = 0; i < pz.getDifficulty(); i++)
            if (pz.positionPieces[i][0] < X && pz.positionPieces[i][1] > X && pz.positionPieces[i][2] < Y && pz.positionPieces[i][3] > Y && pz.noPiece != -1)
            {
                if (pz.positionPieces[pz.noPiece][0] < X && pz.positionPieces[pz.noPiece][1] > X && (pz.positionPieces[pz.noPiece][2] - pz.height - 5) < Y && (pz.positionPieces[pz.noPiece][3] - pz.height) > Y)
                {
                    int piece = pz.orderPieces.get(pz.noPiece);
                    pz.orderPieces.set(pz.noPiece, pz.orderPieces.get(i));
                    pz.orderPieces.set(i, piece);
                    pz.pieceMove = pz.noPiece;
                    pz.noPiece = i;
                    pz.Ym = pz.positionPieces[i][2];
                    pz.direction = 2;
                    pz.move = true;
                } else if (pz.positionPieces[pz.noPiece][0] < X && pz.positionPieces[pz.noPiece][1] > X && (pz.positionPieces[pz.noPiece][2] + pz.height + 5) < Y && (pz.positionPieces[pz.noPiece][3] + pz.height) > Y)
                {
                    int piece = pz.orderPieces.get(pz.noPiece);
                    pz.orderPieces.set(pz.noPiece, pz.orderPieces.get(i));
                    pz.orderPieces.set(i, piece);
                    pz.pieceMove = pz.noPiece;
                    pz.noPiece = i;
                    pz.Ym = pz.positionPieces[i][2];
                    pz.direction = 0;
                    pz.move = true;
                } else if ((pz.positionPieces[pz.noPiece][0] - pz.width - 5) < X && (pz.positionPieces[pz.noPiece][1] - pz.width) > X && pz.positionPieces[pz.noPiece][2] < Y && pz.positionPieces[pz.noPiece][3] > Y)
                {
                    int piece = pz.orderPieces.get(pz.noPiece);
                    pz.orderPieces.set(pz.noPiece, pz.orderPieces.get(i));
                    pz.orderPieces.set(i, piece);
                    pz.pieceMove = pz.noPiece;
                    pz.noPiece = i;
                    pz.Xm = pz.positionPieces[i][0];
                    pz.direction = 1;
                    pz.move = true;
                } else if ((pz.positionPieces[pz.noPiece][0] + pz.width + 5) < X && (pz.positionPieces[pz.noPiece][1] + pz.width) > X && pz.positionPieces[pz.noPiece][2] < Y && pz.positionPieces[pz.noPiece][3] > Y)
                {
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
    public void mouseReleased(MouseEvent me)
    {
    }

    @Override
    public void mouseEntered(MouseEvent me)
    {
    }

    @Override
    public void mouseExited(MouseEvent me)
    {
    }

}
