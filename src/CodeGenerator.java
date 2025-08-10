import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class CodeGenerator {

    // in the most literal sense
    private static List<String> whitelist = new ArrayList<>();
    private static List<String> blacklist = new ArrayList<>();
    
    public static String frameToCode(Frame frame) {
        StringBuilder sb = new StringBuilder();
        CustomCharacter[][] chars = frame.getFrameData();
        for (int y = 0; y < chars.length; y++) {
            for (int x = 0; x < chars[0].length; x++) {
                sb.append(characterToCode(chars[y][x], "f" + frame.getCount() + "_" + y + x));
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public static void writeIno(List<Frame> list, int delay) {
        StringBuilder sb = new StringBuilder();
        sb.append("#include <LiquidCrystal.h>\n");
        sb.append("LiquidCrystal lcd(7, 8, 9, 10, 11, 12);\n");
        sb.append("const byte WHITE[8] PROGMEM = {0x1f,0x1f,0x1f,0x1f,0x1f,0x1f,0x1f,0x1f,};\n");
        sb.append("const byte BLACK[8] PROGMEM = {0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,};\n");
        for (Frame frame : list) {
            sb.append(frameToCode(frame));
        }
        sb.append("\nvoid setup()\n{\nlcd.begin(16,2);\n}\n");
        sb.append("\nvoid loop()\n{\n");
        for (int i = 0; i < list.size(); i++) {
            String[] scope = getCharacterNamesFromFrame(list.get(i));
            for (int j = 0; j < 8; j++) {
                // sb.append("lcd.createChar(");
                // sb.append(j);
                // sb.append(", ");
                // sb.append(scope[j]);
                // sb.append(");\n");
                sb.append("createCustomCharacter(");
                sb.append(j);
                sb.append(", ");
                if (whitelist.contains(scope[j]))
                    sb.append("WHITE");
                else if (blacklist.contains(scope[j]))
                    sb.append("BLACK");
                else
                    sb.append(scope[j]);
                sb.append(");\n");
            }
            int anotherIndex = 0;
            for (int k = 0; k < 2; k++) {
                for (int l = 0; l < 4; l++) {
                    sb.append("lcd.setCursor(");
                    sb.append(l+6);
                    sb.append(", ");
                    sb.append(k);
                    sb.append(");\n");
                    sb.append("lcd.write((byte)");
                    sb.append(anotherIndex);
                    sb.append(");\n");
                    anotherIndex++;
                }
            }
            sb.append("delay(");
            sb.append(delay);
            sb.append(");\n");
        }
        sb.append("}\n");
        sb.append("""
                void createCustomCharacter(int j, byte bytes[]) {
                    uint8_t charData[8];
                
                    for (uint8_t i = 0; i < 8; i++) {
                        charData[i] = pgm_read_byte(&(bytes[i]));
                    }
                
                    lcd.createChar(j, charData);
                }
                """);
        try (PrintWriter out = new PrintWriter("generated.ino")) {
            out.print(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String[] getCharacterNamesFromFrame(Frame frame) {
        CustomCharacter[][] chars = frame.getFrameData();
        String[] output = new String[8];
        int i = 0;
        for (int y = 0; y < chars.length; y++) {
            for (int x = 0; x < chars[0].length; x++) {
                output[i] = ("f" + frame.getCount() + "_" + y + x);
                i++;
            }
        }
        return output;
    }

    private static String characterToCode(CustomCharacter character, String name) {
        if (character.is(CustomCharacter.WHITE)) {
            whitelist.add(name);
            return "";
        }
        else if (character.is(CustomCharacter.BLACK)) {
            blacklist.add(name);
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("const byte ");
        sb.append(name);
        sb.append("[8] PROGMEM = {");
        sb.append(character.toString());
        sb.append("};");
        return sb.toString();
    }

}
