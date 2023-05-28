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

/**
 * this class extends the JFrame class to create a main window for managing images
 * it provides functionality for managing images like uploading and deleting images
 */
public class MainManage extends JFrame {

    private Puzzle pz;
    private MainPuzzle mz;

    /**
     * constructor
     */
    public MainManage(Puzzle pz, MainPuzzle mz) {
        this.setSize(200, 200);
        this.setResizable(false);
        this.setTitle("Manage Images");
        components();
        this.pz = pz;
        this.mz = mz;
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    /**
     * creates the components for ManageImages panel
     */
    private void components() {
        JPanel jp = new JPanel();
        jp.setLayout(null);
        JFileChooser jfc = new JFileChooser(); //create a file chooser dialog
        JLabel title = new JLabel("Manage Image");
        title.setBounds(0, 0, 200, 30);
        title.setHorizontalAlignment(JLabel.CENTER);
        jp.add(title); //adds the JLabel to the JPanel

        JButton upload = new JButton("Upload images");
        upload.setBounds(35, 30, 130, 50);
        upload.addActionListener((ActionEvent e) -> {
            int returnVal = jfc.showOpenDialog(this); //opens the file chooser dialog
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = jfc.getSelectedFile(); //gets the selected file (image) from the file chooser
                /**
                 * attempts to create a new image using the selected file,
                 * reads the image file using ImageIO and creates a ManageImages instance.
                 * if the image creation is successful, updates the puzzle difficulty and displays a success message.
                 * if the image creation fails, displays an error message.
                 */
                try {
                    ManageImages uploadimages = new ManageImages(ImageIO.read(file));
                    if (uploadimages.createImage()) {
                        pz.setDifficulty(pz.getDifficultyMenor());
                        JOptionPane.showMessageDialog(null, "Image created successfully");
                    } else
                        JOptionPane.showMessageDialog(null, "Image could not be created");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Image could not be created");
                }
            }
        });
        jp.add(upload);
        /**
         * creates a "Delete images" button and adds it to the JPanel.
         * when the "Delete images" button is clicked, it displays a dialog box with multiple choices.
         * the choices are generated based on the number of images in the puzzle.
         * if a choice is selected, the corresponding image is deleted.
         */
        JButton delete = new JButton("Delete images");
        delete.setBounds(35, 100, 130, 50);
        delete.addActionListener((ActionEvent e) -> {
            Object[] obj = new Object[Pieces.getNumImages()];
            for (int i = 0; i < Pieces.getNumImages(); i++)
                obj[i] = i;
            try {
                int numImages = Pieces.getNumImages() - 1;
                ManageImages deleteimages = new ManageImages((int) JOptionPane.showInputDialog(null, "Delete image", "Delete image", JOptionPane.INFORMATION_MESSAGE, null, obj, 0));
                //creates an instance of ManageImages that will delete the selected image
                mz.level = 0;
                pz.resetImage();
                if (deleteimages.deleteImage()) { //checks if the image was successfully deleted
                    int cont = 0;
                    int contImg = 0;
                    while (contImg < numImages) { //iterate over the remaining images
                        File file = new File("Images/Pieces/image" + cont + ".jpg");
                        if (file.exists()) {
                            file.renameTo(new File("Images/Pieces/image" + contImg + ".jpg")); //renames the file to fill the gap left by the deleted image
                            contImg++;
                        }
                        cont++;
                    }
                    pz.setDifficulty(pz.getDifficultyMenor()); //sets the difficulty to the updated number of images
                    JOptionPane.showMessageDialog(null, "Image was successfully deleted");
                } else
                    JOptionPane.showMessageDialog(null, "The image could not be deleted");
            } catch (Exception ex) {
                System.err.println(ex.getMessage()); //print any exception that might occur
            }
        });
        jp.add(delete); //adds the delete button to the JPanel
        this.getContentPane().add(jp); //adds the jpanel to the content pane

        /**
         * adds a WindowListener to the JFrame to handle window closing.
         * when the window is closing, it enables the MainPuzzle instance.
         */
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent)
            {
                mz.setEnabled(true);
            }
        });
    }
}
