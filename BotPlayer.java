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
    public DIceSet.DiceRollResult takeTurn() {
        System.out.println(name + " (Bot) is rolling the dice...");

        // Add a small delay to make it feel more natural
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        DIceSet.DiceRollResult result = DIceSet.getInstance().roll();
        System.out.println(name + " (Bot) rolled: " + result);
        return result;
    }
}
