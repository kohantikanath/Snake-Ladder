import java.util.Random;

public class DIceSet {
    private static DIceSet instance;
    private Random random;

    private DIceSet() {
        random = new Random();
    }

    public static DIceSet getInstance() {
        if (instance == null) {
            instance = new DIceSet();
        }
        return instance;
    }

    public int roll() {
        return random.nextInt(6) + 1;
    }
}
