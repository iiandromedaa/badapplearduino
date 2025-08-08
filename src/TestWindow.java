import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class TestWindow {

    JLabel label = new JLabel();
    
    public TestWindow(BufferedImage bufferedImage) {
        JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(bufferedImage.getWidth()*10, bufferedImage.getHeight()*10);
        label.setIcon(new ImageIcon(bufferedImage.getScaledInstance(
            frame.getWidth(), frame.getHeight(), Image.SCALE_SMOOTH)));
        frame.add(label);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void changeImage(BufferedImage bufferedImage) {
        label.setIcon(new ImageIcon(bufferedImage));
    }

}
