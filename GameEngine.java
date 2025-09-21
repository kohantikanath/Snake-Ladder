import java.util.*;

public class GameEngine {
    private Board board;
    private List<Player> players;
    private int currentPlayerIndex;
    private boolean gameEnded;
    private Player winner;

    public GameEngine(Board board, List<Player> players) {
        this.board = board;
        this.players = new ArrayList<>(players);
        this.currentPlayerIndex = 0;
        this.gameEnded = false;
        this.winner = null;

        // Add all players to the board
        for (Player player : players) {
            board.addPlayer(player);
        }
    }

    public void startGame() {
        System.out.println("=== SNAKE AND LADDER GAME STARTED ===");
        System.out.println("Board: " + board.getBoardDimension() + "x" + board.getBoardDimension() +
                " (" + board.getSize() + " cells)");
        System.out.println("Dice: " + DIceSet.getInstance().getNumberOfDice() + " dice");
        System.out.println("Players: ");
        for (int i = 0; i < players.size(); i++) {
            System.out.println((i + 1) + ". " + players.get(i).getName());
        }
        System.out.println();

        board.printBoard();

        while (!gameEnded) {
            playTurn();
        }

        endGame();
    }

    private void playTurn() {
        Player currentPlayer = players.get(currentPlayerIndex);
        boolean getExtraTurn = false;

        do {
            System.out.println("--- " + currentPlayer.getName() + "'s turn ---");
            System.out.println("Current position: " + currentPlayer.getPosition());

            // Player takes turn (rolls dice)
            DIceSet.DiceRollResult diceResult = currentPlayer.takeTurn();
            int oldPosition = currentPlayer.getPosition();

            // Move player on board
            int newPosition = board.movePlayer(currentPlayer, diceResult);

            System.out.println(currentPlayer.getName() + " moved from " + oldPosition + " to " + newPosition);

            // Check for win condition
            if (board.hasWinner(currentPlayer)) {
                winner = currentPlayer;
                gameEnded = true;
                return;
            }

            // Check if player gets extra turn (rolled a 6)
            getExtraTurn = diceResult.hasSix();
            if (getExtraTurn && !gameEnded) {
                System.out.println("🎲 " + currentPlayer.getName() + " rolled a 6! Gets another turn! 🎲");
            }

            // Print current game state
            System.out.println("\nCurrent positions:");
            for (Player player : players) {
                System.out.println(player.getName() + ": " + player.getPosition());
            }
            System.out.println();

            // Add a small pause between turns for readability
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        } while (getExtraTurn && !gameEnded);

        // Move to next player only if current player doesn't get extra turn
        if (!gameEnded) {
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        }
    }

    private void endGame() {
        System.out.println("=== GAME OVER ===");
        if (winner != null) {
            System.out.println("🎉 CONGRATULATIONS! " + winner.getName() + " WINS! 🎉");
            System.out.println(winner.getName() + " reached position " + winner.getPosition());
        }

        System.out.println("\nFinal positions:");
        players.sort((p1, p2) -> Integer.compare(p2.getPosition(), p1.getPosition()));
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            System.out.println((i + 1) + ". " + player.getName() + " - Position: " + player.getPosition());
        }

        board.printBoard();
        System.out.println("Thanks for playing Snake and Ladder!");
    }

    public boolean isGameEnded() {
        return gameEnded;
    }

    public Player getWinner() {
        return winner;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public List<Player> getPlayers() {
        return new ArrayList<>(players);
    }
}
