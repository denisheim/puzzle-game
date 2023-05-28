package Images;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ManageImages extends Images {
    private int imgNum;
    
    public ManageImages(BufferedImage img) throws IOException {
        super(img);
    }

    public ManageImages(int num) throws IOException {
        super(null);
        imgNum = num;
    }
    
    public boolean createImage() throws IOException {
        File file = new File(route + "image" + getNumImages() + ".jpg");
        if(img.getType() != 5)
            return false;
        return ImageIO.write(img, "jpg", file);
    }
    
    public boolean deleteImage() {
        File file = new File(route + "image" + imgNum + ".jpg");
        return file.delete();
    }

    @Override
    public BufferedImage[] splitImage() {
        return null;
    }
}