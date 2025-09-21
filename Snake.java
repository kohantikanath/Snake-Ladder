public class Snake implements Entity {
    private int head;
    private int tail;

    public Snake(int head, int tail) {
        this.head = head;
        this.tail = tail;
    }

    @Override
    public int getStart() {
        return head;
    }

    @Override
    public int getEnd() {
        return tail;
    }

    public int getHead() {
        return head;
    }

    public int getTail() {
        return tail;
    }
}
