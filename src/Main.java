import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Main {

    private static final String path = "./badapplesrc/image_sequence/";
    private static final String fileName = "bad_apple_%d.png";

    public static void main(String[] args) throws Exception {
        File imageSequenceFolder = new File(path);
        if (!(imageSequenceFolder.exists() && imageSequenceFolder.isDirectory())) {
            System.err.println("please Get the image sequence and try again !!");
            return;
        }
        ImageProcessor ip = new ImageProcessor();
        BufferedImage original = ImageIO.read(new File(path + String.format(fileName, 100)));
        new TestWindow(ip.thresholdImage(ip.resizeImage(original, 23, 17), 128));
    }

}