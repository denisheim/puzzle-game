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

public class MainPuzzle extends JFrame implements Runnable {

    public static final int WIDTH = 550, HEIGHT = 620;
    private Canvas canvas;
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

    private void components() {
        jp.setLayout(null);
        jp.setBounds(550, 0, 150, 620);
        jp.setBackground(Color.LIGHT_GRAY);
        JButton reset1 = new JButton("Reset image");
        reset1.setBounds(0, 50, 130, 50);
        reset1.addActionListener((ActionEvent e) -> {
            win = false;
            winT = true;
            pz.resetImage();
        });
        jp.add(reset1);
        JButton solve = new JButton("Solve puzzle");
        solve.setBounds(0, 150, 130, 50);
        solve.addActionListener((ActionEvent e) ->
        {
            pz.solve();
        });
        jp.add(solve);
        JButton changeLevel = new JButton("Change level");
        changeLevel.setBounds(0, 250, 130, 50);
        changeLevel.addActionListener((ActionEvent e) -> {
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
        jp.add(changeLevel);
        JButton changeDiffi = new JButton("Change difficulty");
        changeDiffi.setBounds(0, 350, 130, 50);
        changeDiffi.addActionListener((ActionEvent e) -> {
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
        jp.add(changeDiffi);
        JButton manageImage = new JButton("Manage images");
        manageImage.setBounds(0, 450, 130, 50);
        manageImage.addActionListener((ActionEvent e) -> {
            MainManage ci = new MainManage(pz,this);
            ci.setVisible(true);
            this.setEnabled(false);
        });
        jp.add(manageImage);
        this.getContentPane().add(jp);
    }

    private void update() {
        pz.setLevel(level);
        if (!win)
            win = pz.isWin();
        else if (!winT && contWin > 0)
        {
            JOptionPane.showMessageDialog(null, "congratulations");
            contWin = 0;
        }
        if (win && winT)
        {
            pz.noPiece = -1;
            contWin++;
            winT = false;
        }
    }

    private void draw() {
        bs = canvas.getBufferStrategy();

        if (bs == null)
        {
            canvas.createBufferStrategy(2);
            return;
        }
        g = bs.getDrawGraphics();
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        pz.drawPieces(g);
        g.dispose();
        bs.show();
    }

    @Override
    public void run() {
        long now = 0;
        long lastTime = System.nanoTime();
        int frames = 0;
        long time = 0;

        while (running)
        {
            now = System.nanoTime();
            delta += (now - lastTime) / targettime;
            time += (now - lastTime);
            lastTime = now;

            if (delta >= 1)
            {
                update();
                draw();
                delta--;
                frames++;
            }
            if (time >= 1000000000)
            {
                averageFPS = frames;
                frames = 0;
                time = 0;
            }
        }
        this.stop();
    }

    private void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    private void stop()
    {
        try {
            thread.join();
            running = false;
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
