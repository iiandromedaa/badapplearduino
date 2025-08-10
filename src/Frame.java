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

}
