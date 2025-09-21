public class Ladder implements Entity {
    private int bottom;
    private int top;

    public Ladder(int bottom, int top) {
        this.bottom = bottom;
        this.top = top;
    }

    @Override
    public int getStart() {
        return bottom;
    }

    @Override
    public int getEnd() {
        return top;
    }

    public int getBottom() {
        return bottom;
    }

    public int getTop() {
        return top;
    }
}
