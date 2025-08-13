import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Main {

    private static final String PATH = "./badapplesrc/image_sequence/";
    private static final String FILE_NAME = "bad_apple_%03d.png";
    private static final int TOTAL_FRAMES = 6562;

    public static void main(String[] args) throws Exception {
        File imageSequenceFolder = new File(PATH);
        if (!(imageSequenceFolder.exists() && imageSequenceFolder.isDirectory())) {
            System.err.println("please Get the image sequence and try again !!");
            return;
        }
        ImageProcessor ip = new ImageProcessor();
        List<Frame> coolList = new ArrayList<>();
        int skip = 4;
        for (int i = 1; i <= 500; i += skip) {
            coolList.add(imageToFrame(i, ip));
        }
        new CodeGenerator().writeIno(coolList, 33.33 * skip);
    }

    /**
     * just for organizations sake
     * @return
     * @throws IOException 
     */
    private static Frame imageToFrame(int frameNumber, ImageProcessor ip) throws IOException {
        BufferedImage original = ImageIO.read(new File(PATH + String.format(FILE_NAME, frameNumber)));
        return ip.toChars(ip.thresholdImage(ip.resizeImage(original, 23, 17), 128), frameNumber);
    }

}