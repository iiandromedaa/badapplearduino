public class Frame {
    
    private final CustomCharacter[][] frameData;
    private final int count;

    Frame(CustomCharacter[][] frameData, int count) {
        this.frameData = frameData;
        this.count = count;
    }

    public CustomCharacter[][] getFrameData() {
        return frameData;
    }

    public int getCount() {
        return count;
    }

    public CustomCharacter[] unwrap() {
        CustomCharacter[] output = new CustomCharacter[8];
        int i = 0;
        for (int y = 0; y < frameData.length; y++) {
            for (int x = 0; x < frameData[0].length; x++) {
                output[i] = frameData[y][x];
                i++;
            }
        }
        return output;
    }

    public String toCode() {
        StringBuilder sb = new StringBuilder();
        CustomCharacter[][] chars = getFrameData();
        for (int y = 0; y < chars.length; y++) {
            for (int x = 0; x < chars[0].length; x++) {
                sb.append(chars[y][x].toCode("f" + getCount() + "_" + y + x));
                sb.append("\n");
            }
        }
        return sb.toString();
    }

}
