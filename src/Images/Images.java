package Images;

import java.awt.Graphics2D;
import java.io.File;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * this class represents images used in the puzzle game
 * it also provides common functionality and attributes for image handling and manipulation.
 */
public abstract class Images {

    protected final BufferedImage img;
    protected final String route = "Images/Pieces/";

    public Images(int num) throws IOException {
        img = rezise(ImageIO.read(new File(route + "image" + num + ".jpg")));
    }

    public Images(BufferedImage img) {
        this.img = img;
    }

    public BufferedImage getImg() {
        return img;
    }

    public int getWidth() {
        return img.getWidth();
    }

    public int getHeight() {
        return img.getHeight();
    }

    /**
     * Resizes a given BufferedImage to the specified size.
     */
    private BufferedImage rezise(BufferedImage img) {
        int sizeX = 500;
        int sizeY = 500;
        BufferedImage dimg = new BufferedImage(sizeX, sizeY, img.getType());
        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(img, 0, 0, sizeX, sizeY, null);
        g2d.dispose();
        return dimg;
    }

    /**
     * checks if the directory for puzzle images exists. If not, it creates the directory and generates a default (black) image.
     * if the directory exists but the default image is missing, it generates the default image
     */
    public static void existDirec() {
        File file = new File("Images/Pieces/");

        if (!file.exists()) {
            //create the directory
            file.mkdirs();

            //generate default image
            BufferedImage img = new BufferedImage(500, 500, 5);
            file = new File("Images/Pieces/image0.jpg");
            try {
                ImageIO.write(img, "jpg", file);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            file = new File("Images/Pieces/image0.jpg");
            if (!file.exists()) {
                BufferedImage img = new BufferedImage(500, 500, 5);
                try {
                    ImageIO.write(img, "jpg", file);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }

    /**
     * retrieves the total number of images present in the set directory
     */
    public static int getNumImages() {
        File file = null;
        int cont = 0;
        while (true) {
            file = new File("Images/Pieces/" + "image" + cont + ".jpg");
            if (file.exists())
                cont++;
            else
                break;
        }
        return cont;
    }

    public abstract BufferedImage[] splitImage(); //splits the image based on the difficulty level
}
