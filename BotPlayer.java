public class BotPlayer implements Player {
    private String name;
    private int position;

    public BotPlayer(String name) {
        this.name = name;
        this.position = 0;
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
    public int takeTurn() {
        // Bot automatically rolls the dice
        int diceRoll = DIceSet.getInstance().roll();
        System.out.println(name + " (Bot) rolled: " + diceRoll);

        // Add a small delay to make it feel more natural
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return diceRoll;
    }
}
