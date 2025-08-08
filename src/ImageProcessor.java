import java.awt.Image;
import java.awt.image.BufferedImage;

public class ImageProcessor {
    
    public BufferedImage resizeImage(BufferedImage original, int newWidth, int newHeight) {
        Image result = original.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT);
        BufferedImage output = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        output.getGraphics().drawImage(result, 0, 0, null);
        return output;
    }

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

}
