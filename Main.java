import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== WELCOME TO SNAKE AND LADDER GAME ===");
        System.out.println();

        // Setup board
        Board board = new Board(100);

        // Setup players
        List<Player> players = new ArrayList<>();

        System.out.print("Enter number of players (2-4): ");
        int numPlayers = scanner.nextInt();
        scanner.nextLine(); // consume newline

        while (numPlayers < 2 || numPlayers > 4) {
            System.out.print("Please enter a valid number of players (2-4): ");
            numPlayers = scanner.nextInt();
            scanner.nextLine();
        }

        for (int i = 1; i <= numPlayers; i++) {
            System.out.print("Enter name for Player " + i + ": ");
            String playerName = scanner.nextLine();

            System.out.print("Is " + playerName + " a (H)uman or (B)ot player? ");
            String playerType = scanner.nextLine().trim().toUpperCase();

            while (!playerType.equals("H") && !playerType.equals("B")) {
                System.out.print("Please enter 'H' for Human or 'B' for Bot: ");
                playerType = scanner.nextLine().trim().toUpperCase();
            }

            Player player;
            if (playerType.equals("H")) {
                player = PlayerFactory.createHumanPlayer(playerName);
            } else {
                player = PlayerFactory.createBotPlayer(playerName);
            }

            players.add(player);
            System.out.println(playerName + " added as " +
                    (playerType.equals("H") ? "Human" : "Bot") + " player.");
        }

        System.out.println();
        System.out.println("Game setup complete!");
        System.out.println("Board size: " + board.getSize() + " squares");
        System.out.println("Number of snakes: " + board.getSnakes().size());
        System.out.println("Number of ladders: " + board.getLadders().size());
        System.out.println();

        // Start the game
        GameEngine gameEngine = new GameEngine(board, players);
        gameEngine.startGame();

        scanner.close();
    }
}