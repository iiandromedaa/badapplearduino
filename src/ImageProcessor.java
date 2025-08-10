import java.awt.Image;
import java.awt.image.BufferedImage;

public class ImageProcessor {
    
    public BufferedImage resizeImage(BufferedImage original, int newWidth, int newHeight) {
        Image result = original.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT);
        BufferedImage output = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        output.getGraphics().drawImage(result, 0, 0, null);
        return output;
    }

    // ngl i just kinda found the math for this method and plugged it in
    // which yes is not great practice but it did happen to work perfectly
    public BufferedImage thresholdImage(BufferedImage original, int threshold) {
        BufferedImage output = new BufferedImage(
            original.getWidth(), original.getHeight(), BufferedImage.TYPE_BYTE_BINARY);

        for (int y = 0; y < original.getHeight(); y++) {
            for (int x = 0; x < original.getWidth(); x++) {
                int rgb = original.getRGB(x, y);
                int gray = (int) (0.299 * ((rgb >> 16) & 0xFF) + 0.587 * 
                    ((rgb >> 8) & 0xFF) + 0.114 * (rgb & 0xFF)); // Luminosity method for grayscale

                if (gray < threshold)
                    output.setRGB(x, y, 0xFF000000); // Black (ARGB)
                else
                    output.setRGB(x, y, 0xFFFFFFFF); // White (ARGB)
            }
        }
        return output;
    }

    /**
     * java is row major DO NOT FORGET !!!!!!!
     * @param image
     * @return 2d array of custom characters
     * <p> 00 01 02 03
     * <p> 10 11 12 13
     */
    public CustomCharacter[][] toChars(BufferedImage image) {
        CustomCharacter[][] chars = new CustomCharacter[2][4];
        int iy = 0;
        int ix = 0;
        // tuned the numbers specifically for 23x17 image
        for (int y = 0; y < image.getHeight(); y += 9) {
            ix = 0;
            for (int x = 0; x < image.getWidth(); x += 6) {
                chars[iy][ix] = imageToCharacter(image.getSubimage(x, y, 5, 8));
                ix++;
            }
            iy++;
        }
        return chars;
    }

    /**
     * java is row major DO NOT FORGET !!!!!!!
     * @param image
     * @return frame object aka container for 2d array of custom characters
     * <p> 00 01 02 03
     * <p> 10 11 12 13
     */
    public Frame toChars(BufferedImage image, int frameCount) {
        return new Frame(toChars(image), frameCount);
    }

    /**
     * image has to be 5x8 black and white 1 bit colour
     * @param image
     * @return 
     */
    public CustomCharacter imageToCharacter(BufferedImage image) {
        if (image.getWidth() != 5 || image.getHeight() != 8)
            return null;
        byte[] data = new byte[8];
        for (int y = 0; y < 8; y++) {
            byte row = 0b00000;
            for (int x = 0; x < 5; x++) {
                int rgb = image.getRGB(x, y);
                if (rgb == 0xFF000000)
                    row = (byte) (row << 1); // bitshift left
                else if (rgb == 0xFFFFFFFF)
                    row = (byte) ((row << 1) | 0b1); // bitshift left and set rightmost to 1
            }
            data[y] = row;
        }
        return new CustomCharacter(data);
    }

}
