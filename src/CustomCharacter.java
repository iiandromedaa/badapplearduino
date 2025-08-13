import java.util.Arrays;

public class CustomCharacter {

    public static CustomCharacter BLACK = new CustomCharacter(
        new byte[] {0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00});

    public static CustomCharacter WHITE = new CustomCharacter(
        new byte[] {0x1f,0x1f,0x1f,0x1f,0x1f,0x1f,0x1f,0x1f});
        
    private final byte[] pixels;
    
    public CustomCharacter(byte[] data) {
        pixels = data;
    }

    public byte[] getPixels() {
        return pixels;
    }

    public String toCode(String name) {
        StringBuilder sb = new StringBuilder();
        sb.append("const byte ");
        sb.append(name);
        sb.append("[8] PROGMEM = {");
        sb.append(toString());
        sb.append("};");
        return sb.toString();
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

    public boolean is(CustomCharacter character) {
        return Arrays.equals(character.getPixels(), pixels);
    }

}
