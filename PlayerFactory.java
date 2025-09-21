public class PlayerFactory {

    public enum PlayerType {
        HUMAN, BOT
    }

    public static Player createPlayer(PlayerType type, String name) {
        switch (type) {
            case HUMAN:
                return new HumanPlayer(name);
            case BOT:
                return new BotPlayer(name);
            default:
                throw new IllegalArgumentException("Unknown player type: " + type);
        }
    }

    public static Player createHumanPlayer(String name) {
        return new HumanPlayer(name);
    }

    public static Player createBotPlayer(String name) {
        return new BotPlayer(name);
    }
}
