import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class DIceSet {
    private static DIceSet instance;
    private Random random;
    private int numberOfDice;

    private DIceSet() {
        random = new Random();
        numberOfDice = 1; // Default to 1 dice
    }

    public static DIceSet getInstance() {
        if (instance == null) {
            instance = new DIceSet();
        }
        return instance;
    }

    public void setNumberOfDice(int numberOfDice) {
        if (numberOfDice < 1) {
            throw new IllegalArgumentException("Number of dice must be at least 1");
        }
        this.numberOfDice = numberOfDice;
    }

    public int getNumberOfDice() {
        return numberOfDice;
    }

    public DiceRollResult roll() {
        List<Integer> individualRolls = new ArrayList<>();
        int totalSum = 0;
        boolean hasSix = false;

        for (int i = 0; i < numberOfDice; i++) {
            int roll = random.nextInt(6) + 1;
            individualRolls.add(roll);
            totalSum += roll;
            if (roll == 6) {
                hasSix = true;
            }
        }

        return new DiceRollResult(individualRolls, totalSum, hasSix);
    }

    // Inner class to hold dice roll results
    public static class DiceRollResult {
        private List<Integer> individualRolls;
        private int totalSum;
        private boolean hasSix;

        public DiceRollResult(List<Integer> individualRolls, int totalSum, boolean hasSix) {
            this.individualRolls = new ArrayList<>(individualRolls);
            this.totalSum = totalSum;
            this.hasSix = hasSix;
        }

        public List<Integer> getIndividualRolls() {
            return new ArrayList<>(individualRolls);
        }

        public int getTotalSum() {
            return totalSum;
        }

        public boolean hasSix() {
            return hasSix;
        }

        @Override
        public String toString() {
            if (individualRolls.size() == 1) {
                return String.valueOf(totalSum);
            } else {
                return individualRolls.toString() + " (Total: " + totalSum + ")";
            }
        }
    }
}
