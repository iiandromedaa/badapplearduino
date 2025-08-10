import java.util.Arrays;

public class CustomCharacter {

    public static byte[] BLACK = {0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
    public static byte[] WHITE = {0x1f,0x1f,0x1f,0x1f,0x1f,0x1f,0x1f,0x1f};
    private final byte[] pixels;
    
    public CustomCharacter(byte[] data) {
        pixels = data;
    }

    public byte[] getPixels() {
        return pixels;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < pixels.length; i++) {
            sb.append("0x");
            sb.append(Integer.toHexString(pixels[i]));
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public boolean is(byte[] character) {
        return Arrays.equals(character, getPixels());
    }

}
