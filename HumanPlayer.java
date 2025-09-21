import java.util.Scanner;

public class HumanPlayer implements Player {
    private String name;
    private int position;
    private Scanner scanner;

    public HumanPlayer(String name) {
        this.name = name;
        this.position = 0;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public DIceSet.DiceRollResult takeTurn() {
        System.out.print(name + ", press Enter to roll the dice...");
        scanner.nextLine();
        DIceSet.DiceRollResult result = DIceSet.getInstance().roll();
        System.out.println(name + " rolled: " + result);
        return result;
    }
}
