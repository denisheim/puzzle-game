package Windows;

import DrawPuzzle.Puzzle;
import Images.Pieces;
import Input.Mouse;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.image.BufferStrategy;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * this class represents the main puzzle window of the application and provides functionality for running the game
 */
public class MainPuzzle extends JFrame implements Runnable {

    public static final int WIDTH = 550, HEIGHT = 620;
    private final Canvas canvas;
    private Thread thread;
    private boolean running = false;
    private boolean win = false;
    private boolean winT = true;

    private BufferStrategy bs;
    private Graphics g;

    private final int FPS = 30;
    private double targettime = 1000000000 / FPS;
    private double delta = 0;
    private int averageFPS = FPS;

    private int diffi = 3;
    private Puzzle pz = new Puzzle(diffi);
    private JPanel jp = new JPanel();
    public int level = 0;
    private int contWin = 0;

    /**
     * constructor
     */
    public MainPuzzle() {
        this.setSize(700, 650);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Puzzle");
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        canvas.setMaximumSize(new Dimension(WIDTH, HEIGHT));
        canvas.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        canvas.setFocusable(true);
        canvas.addMouseListener(new Mouse(pz));
        components();
        this.add(canvas);
    }

    public static void main(String[] args) {
        new MainPuzzle().start();
    }

    /**
     * initializes and configures the components of the puzzle frame
     * adds buttons and their event listeners for resetting the image, solving the puzzle,
     * changing the puzzle level and difficulty, and managing images.
     */
    private void components() {
        jp.setLayout(null);
        jp.setBounds(550, 0, 150, 620);
        jp.setBackground(Color.DARK_GRAY);
        JButton reset1 = new JButton("Randomize");
        reset1.setBounds(0, 50, 130, 50);
        reset1.addActionListener((ActionEvent e) -> {
            //resets the image, win status and winT status
            win = false;
            winT = true;
            pz.resetImage();
        });
        jp.add(reset1); //adds the reset button to the jpanel

        JButton solve = new JButton("Solve puzzle");
        solve.setBounds(0, 150, 130, 50);
        solve.addActionListener((ActionEvent e) -> {
            //solves the puzzle
            pz.solve();
        });
        jp.add(solve); //adds the solve button to the jpanel

        JButton changeLevel = new JButton("Change level");
        changeLevel.setBounds(0, 250, 130, 50);
        changeLevel.addActionListener((ActionEvent e) -> {
            //displays a dialog to select the puzzle level and resets the image
            Object[] obj = new Object[Pieces.getNumImages()];
            for (int i = 0; i < Pieces.getNumImages(); i++)
                obj[i] = i;
            try {
                level = (int) JOptionPane.showInputDialog(null, "Select level", "Select level", JOptionPane.INFORMATION_MESSAGE, null, obj, level);
                pz.resetImage();
                win = false;
                winT = true;
            } catch (Exception ex) {
            }
        });
        jp.add(changeLevel); //adds the changeLevel button to the jpanel

        JButton changeDiffi = new JButton("Change difficulty");
        changeDiffi.setBounds(0, 350, 130, 50);
        changeDiffi.addActionListener((ActionEvent e) -> {
            //displays a dialog to select the puzzle difficulty and updates the setting
            try {
                diffi = (int) JOptionPane.showInputDialog(null, "Select difficulty", "Select difficulty", JOptionPane.INFORMATION_MESSAGE, null, new Object[]
                {
                    3, 4, 5, 6, 7, 8
                }, diffi);
                pz.resetImage();
                win = false;
                winT = true;
            } catch (Exception ex) {
            }
            pz.setDifficulty(diffi);
            pz.resetImage();
        });
        jp.add(changeDiffi); //adds the changeDiffi button to the jpanel

        JButton manageImage = new JButton("Manage images");
        manageImage.setBounds(0, 450, 130, 50);
        manageImage.addActionListener((ActionEvent e) -> {
            //opens the image managment window and disables the current puzzle frame
            MainManage ci = new MainManage(pz,this);
            ci.setVisible(true);
            this.setEnabled(false);
        });
        jp.add(manageImage);
        this.getContentPane().add(jp); //adds the jpanel to the puzzle's frame pane
    }

    /**
     * updates the puzzle state and checks for a win condition
     * sets the puzzle level, checks if the puzzle is in a winning state,
     * and displays a congratulations message when the puzzle is successfully solved.
     */
    private void update() {
        pz.setLevel(level); //sets the level
        if (!win)
            win = pz.isWin(); //checks if the puzzle is in a winning state
        else if (!winT && contWin > 0) {
            JOptionPane.showMessageDialog(null, "congratulations");
            contWin = 0;
        }
        /**
         * handles post-win actions,
         * like updating the puzzle state variables
         */
        if (win && winT) {
            pz.noPiece = -1;
            contWin++;
            winT = false;
        }
    }

    /**
     * renders the puzzle game on the canvas,
     * draws the puzzle pieces and updates the display.
     */
    private void draw() {
        bs = canvas.getBufferStrategy();

        if (bs == null) {
            canvas.createBufferStrategy(2);
            return;
        }
        g = bs.getDrawGraphics();
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        pz.drawPieces(g);
        g.dispose();
        bs.show();
    }

    /**
     * runs the game loop for continuous updating and rendering
     * and implements the Runnable interface.
     */
    @Override
    public void run() {
        long now = 0;
        long lastTime = System.nanoTime();
        int frames = 0;
        long time = 0;

        while (running) {
            now = System.nanoTime();
            delta += (now - lastTime) / targettime; //calculate the time difference since the last iteration
            time += (now - lastTime); //mount the time
            lastTime = now; //update the lastTime to current time

            if (delta >= 1) {
                update();
                draw();
                delta--;
                frames++;
            }
            if (time >= 1000000000) {
                averageFPS = frames;
                frames = 0;
                time = 0;
            }
        }
        this.stop();
    }

    /**
     * starts the game loop by creating a thread and calls the run method
     */
    private void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    /**
     * stops the game loop by setting "running" to false,
     * however it waits for the thread to finish execution
     */
    private void stop() {
        try {
            thread.join();
            running = false;
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}