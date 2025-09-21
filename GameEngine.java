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
    }

    public void startGame() {
        System.out.println("=== SNAKE AND LADDER GAME STARTED ===");
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

        System.out.println("--- " + currentPlayer.getName() + "'s turn ---");
        System.out.println("Current position: " + currentPlayer.getPosition());

        // Player takes turn (rolls dice)
        int diceRoll = currentPlayer.takeTurn();

        // Move player on board
        int newPosition = board.movePlayer(currentPlayer, diceRoll);

        System.out.println(currentPlayer.getName() + " moved from " +
                (currentPlayer.getPosition() - diceRoll) + " to " + newPosition);

        // Check for win condition
        if (board.hasWinner(currentPlayer)) {
            winner = currentPlayer;
            gameEnded = true;
            return;
        }

        // Move to next player
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();

        // Print current game state
        System.out.println("\nCurrent positions:");
        for (Player player : players) {
            System.out.println(player.getName() + ": " + player.getPosition());
        }
        System.out.println();

        // Add a small pause between turns for readability
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
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
