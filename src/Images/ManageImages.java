package Images;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * this class is used for managing images
 */

public class ManageImages extends Images {
    private int imgNum;
    
    public ManageImages(BufferedImage img) throws IOException {
        super(img);
    } //constructs a ManageImages object with the specified image

    public ManageImages(int num) throws IOException {
        super(null);
        imgNum = num;
    } //constructs a ManageImages object with the specified image number
    
    public boolean createImage() throws IOException {
        File file = new File(route + "image" + getNumImages() + ".jpg");
        if(img.getType() != 5)
            return false;
        return ImageIO.write(img, "jpg", file);
    } //creates a new image file based on the managed image, returns true if created successfully, false otherwise
    
    public boolean deleteImage() {
        File file = new File(route + "image" + imgNum + ".jpg");
        return file.delete();
    } //deletes the image file

    @Override
    public BufferedImage[] splitImage() {
        return null;
    }
}