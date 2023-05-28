package DrawPuzzle;

import Images.Images;
import Images.Pieces;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Puzzle {

    private Images[] imgs;
    public BufferedImage[][] pieces;
    private int numImages;
    private int difficulty;
    private int difficultyMenor;
    private int level;
    public int noPiece;
    public ArrayList<Integer> orderPieces;
    public int[][] positionPieces;
    public int width;
    public int height;
    public boolean move = false;
    public int direction = -1;
    public int pieceMove = -1;
    public int Ym = 0;
    public int Xm = 0;

    public Puzzle(int difficulty) {
        Images.existDirec();
        numImages = Images.getNumImages();
        this.difficulty = difficulty * difficulty;
        difficultyMenor = difficulty;
        this.level = 0;
        imgs = new Images[numImages];
        positionPieces = new int[this.difficulty][4];
        pieces = new BufferedImage[numImages][this.difficulty];
        orderPieces = new ArrayList<>();
        noPiece = (int) (Math.random() * this.difficulty);
        setOrderPiece();
        setImages();
        width = pieces[level][0].getWidth();
        height = pieces[level][0].getHeight();
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int getDifficultyMenor() {
        return difficultyMenor;
    }
    
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty * difficulty;
        this.difficultyMenor = difficulty;
        numImages = Images.getNumImages();
        imgs = new Images[numImages];
        pieces = new BufferedImage[numImages][this.difficulty];
        noPiece = (int) (Math.random() * this.difficulty);
        positionPieces = new int[this.difficulty][4];
        resetImage();
        setImages();
        width = pieces[level][0].getWidth();
        height = pieces[level][0].getHeight();
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void resetImage() {
        noPiece = (int) (Math.random() * this.difficulty);
        orderPieces.clear();
        setOrderPiece();
    }

    public void solve() {
        for (int i = 0; i < orderPieces.size(); i++)
            orderPieces.set(i, i);
    }

    private void setOrderPiece() {
        while (orderPieces.size() < difficulty) {
            int posTemp = (int) (Math.random() * difficulty);
            if (!existNum(posTemp))
                orderPieces.add(posTemp);
        }
    }

    private boolean existNum(int num) {
        for (Integer orderPiece : orderPieces)
            if (orderPiece == num)
                return true;
        return false;
    }

    public void drawPieces(Graphics g) {
        int space = 5;
        int x = 20;
        int calPos;
        int y = 50;
        int contX = 0;
        int contY = 0;
        for (int i = 0; i < difficulty; i++) {
            if (contX > 0) {
                x += (width + space);
                contX++;
            } else
                contX++;
            if (contY > difficultyMenor - 1) {
                y += (height + space);
                x = 20;
                contX = 1;
                contY = 1;
            } else
                contY++;
            if (i != noPiece)
                if (move && i == pieceMove)
                    switch (direction) {
                        case 0:
                            calPos = positionPieces[i][2] - Ym;
                            if (calPos < -50) {
                                Ym -= 50;
                                g.drawImage(pieces[level][orderPieces.get(i)], x, Ym, null);
                            } else {
                                g.drawImage(pieces[level][orderPieces.get(i)], x, y, null);
                                move = false;
                                direction = -1;
                                pieceMove = -1;
                                Ym = 0;
                            }
                            break;
                        case 1:
                            calPos = positionPieces[i][0] - Xm;
                            if (calPos > 50) {
                                Xm += 50;
                                g.drawImage(pieces[level][orderPieces.get(i)], Xm, y, null);
                            } else {
                                g.drawImage(pieces[level][orderPieces.get(i)], x, y, null);
                                move = false;
                                direction = -1;
                                pieceMove = -1;
                                Xm = 0;
                            }
                            break;
                        case 2:
                            calPos = positionPieces[i][2] - Ym;
                            if (calPos > 50) {
                                Ym += 50;
                                g.drawImage(pieces[level][orderPieces.get(i)], x, Ym, null);
                            } else {
                                g.drawImage(pieces[level][orderPieces.get(i)], x, y, null);
                                move = false;
                                direction = -1;
                                pieceMove = -1;
                                Ym = 0;
                            }
                            break;
                        case 3:
                            calPos = positionPieces[i][0] - Xm;
                            if (calPos < -50) {
                                Xm -= 50;
                                g.drawImage(pieces[level][orderPieces.get(i)], Xm, y, null);
                            } else {
                                g.drawImage(pieces[level][orderPieces.get(i)], x, y, null);
                                move = false;
                                direction = -1;
                                pieceMove = -1;
                                Xm = 0;
                            }
                            break;
                    }
                else
                    g.drawImage(pieces[level][orderPieces.get(i)], x, y, null);
            positionPieces[i][0] = x;
            positionPieces[i][1] = x + width;
            positionPieces[i][2] = y;
            positionPieces[i][3] = y + height;
        }
    }

    public boolean isWin() {
        boolean win = true;
        for (int i = 0; i < difficulty; i++)
            if (i != orderPieces.get(i)) {
                win = false;
                break;
            }
        return win;
    }

    private void setImages() {
        for (int i = 0; i < Images.getNumImages(); i++)
            try {
                imgs[i] = new Pieces(i, difficultyMenor);
                BufferedImage[] imgsTemp = imgs[i].splitImage();
                for (int j = 0; j < difficulty; j++)
                    pieces[i][j] = imgsTemp[j];
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
    }
}
