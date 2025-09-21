import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== WELCOME TO ADVANCED SNAKE AND LADDER GAME ===");
        System.out.println();

        // Setup board size
        System.out.print("Enter board dimension (n for n×n board, minimum 3): ");
        int boardDimension = scanner.nextInt();
        while (boardDimension < 3) {
            System.out.print("Board dimension must be at least 3. Enter again: ");
            boardDimension = scanner.nextInt();
        }

        // Setup number of dice
        System.out.print("Enter number of dice (1-3): ");
        int numDice = scanner.nextInt();
        while (numDice < 1 || numDice > 3) {
            System.out.print("Number of dice must be between 1 and 3. Enter again: ");
            numDice = scanner.nextInt();
        }
        DIceSet.getInstance().setNumberOfDice(numDice);

        // Setup board
        Board board = new Board(boardDimension);

        // Setup players
        List<Player> players = new ArrayList<>();
        scanner.nextLine(); // consume newline

        System.out.print("Enter number of players (minimum 2): ");
        int numPlayers = scanner.nextInt();
        scanner.nextLine(); // consume newline

        while (numPlayers < 2) {
            System.out.print("Please enter at least 2 players: ");
            numPlayers = scanner.nextInt();
            scanner.nextLine();
        }

        for (int i = 1; i <= numPlayers; i++) {
            System.out.print("Enter name for Player " + i + ": ");
            String playerName = scanner.nextLine().trim();

            while (playerName.isEmpty()) {
                System.out.print("Name cannot be empty. Enter again: ");
                playerName = scanner.nextLine().trim();
            }

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
        System.out.println("=== GAME CONFIGURATION ===");
        System.out.println("Board: " + boardDimension + "×" + boardDimension + " (" + board.getSize() + " cells)");
        System.out.println("Dice: " + numDice + " dice");
        System.out.println("Players: " + numPlayers);
        System.out.println("Snakes: " + board.getSnakes().size());
        System.out.println("Ladders: " + board.getLadders().size());
        System.out.println();

        System.out.println("=== GAME RULES ===");
        System.out.println("• Roll exactly to land on the final cell to win");
        System.out.println("• Rolling a 6 gives you an extra turn");
        System.out.println("• Landing on another player sends them back to start");
        System.out.println("• Snakes slide you down, ladders climb you up");
        System.out.println("• No entities conflict on the same cell");
        System.out.println();

        // Start the game
        GameEngine gameEngine = new GameEngine(board, players);
        gameEngine.startGame();

        scanner.close();
    }
}