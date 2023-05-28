package Windows;

import DrawPuzzle.Puzzle;
import Images.Pieces;
import Images.ManageImages;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MainManage extends JFrame
{

    private Puzzle pz;
    private MainPuzzle mz;

    public MainManage(Puzzle pz, MainPuzzle mz)
    {
        this.setSize(200, 200);
        this.setResizable(false);
        this.setTitle("Manage Images");
        components();
        this.pz = pz;
        this.mz = mz;
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    private void components()
    {
        JPanel jp = new JPanel();
        jp.setLayout(null);
        JFileChooser jfc = new JFileChooser();
        JLabel title = new JLabel("Manage Image");
        title.setBounds(0, 0, 200, 30);
        title.setHorizontalAlignment(JLabel.CENTER);
        jp.add(title);
        JButton upload = new JButton("Upload images");
        upload.setBounds(35, 30, 130, 50);
        upload.addActionListener((ActionEvent e) ->
        {
            int returnVal = jfc.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION)
            {
                File file = jfc.getSelectedFile();
                try
                {
                    ManageImages uploadimages = new ManageImages(ImageIO.read(file));
                    if (uploadimages.createImage())
                    {
                        pz.setDifficulty(pz.getDifficultyMenor());
                        JOptionPane.showMessageDialog(null, "Image created successfully");
                    } else
                        JOptionPane.showMessageDialog(null, "Image could not be created");
                } catch (Exception ex)
                {
                    JOptionPane.showMessageDialog(null, "Image could not be created");
                }
            }
        });
        jp.add(upload);
        JButton delete = new JButton("Delete images");
        delete.setBounds(35, 100, 130, 50);
        delete.addActionListener((ActionEvent e) ->
        {
            Object[] obj = new Object[Pieces.getNumImages()];
            for (int i = 0; i < Pieces.getNumImages(); i++)
                obj[i] = i;
            try
            {
                int numImages = Pieces.getNumImages() - 1;
                ManageImages deleteimages = new ManageImages((int) JOptionPane.showInputDialog(null, "Delete image", "Delete image", JOptionPane.INFORMATION_MESSAGE, null, obj, 0));
                mz.level = 0;
                pz.resetImage();
                if (deleteimages.deleteImage())
                {
                    int cont = 0;
                    int contImg = 0;
                    while (contImg < numImages)
                    {
                        File file = new File("Images/Pieces/image" + cont + ".jpg");
                        if (file.exists())
                        {
                            file.renameTo(new File("Images/Pieces/image" + contImg + ".jpg"));
                            contImg++;
                        }
                        cont++;
                    }
                    pz.setDifficulty(pz.getDifficultyMenor());
                    JOptionPane.showMessageDialog(null, "Image was successfully deleted");
                } else
                    JOptionPane.showMessageDialog(null, "The image could not be deleted");
            } catch (Exception ex)
            {
                System.err.println(ex.getMessage());
            }
        });
        jp.add(delete);
        this.getContentPane().add(jp);
        this.addWindowListener(new java.awt.event.WindowAdapter()
        {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent)
            {
                mz.setEnabled(true);
            }
        });
    }
}
