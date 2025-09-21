import java.util.*;

public class Board {
    private int size;
    private List<Snake> snakes;
    private List<Ladder> ladders;
    private Map<Integer, Player> playerPositions;

    public Board(int size) {
        this.size = size;
        this.snakes = new ArrayList<>();
        this.ladders = new ArrayList<>();
        this.playerPositions = new HashMap<>();
        initializeBoard();
    }

    private void initializeBoard() {
        // Add some default snakes (head -> tail)
        snakes.add(new Snake(99, 78));
        snakes.add(new Snake(95, 75));
        snakes.add(new Snake(92, 88));
        snakes.add(new Snake(89, 68));
        snakes.add(new Snake(74, 53));
        snakes.add(new Snake(64, 60));
        snakes.add(new Snake(62, 19));
        snakes.add(new Snake(49, 11));
        snakes.add(new Snake(46, 25));
        snakes.add(new Snake(16, 6));

        // Add some default ladders (bottom -> top)
        ladders.add(new Ladder(2, 38));
        ladders.add(new Ladder(7, 14));
        ladders.add(new Ladder(8, 31));
        ladders.add(new Ladder(15, 26));
        ladders.add(new Ladder(28, 84));
        ladders.add(new Ladder(21, 42));
        ladders.add(new Ladder(36, 44));
        ladders.add(new Ladder(51, 67));
        ladders.add(new Ladder(71, 91));
        ladders.add(new Ladder(78, 98));
    }

    public int movePlayer(Player player, int diceRoll) {
        int currentPosition = player.getPosition();
        int newPosition = currentPosition + diceRoll;

        // Check if player has won
        if (newPosition >= size) {
            newPosition = size;
        } else {
            // Check for snakes
            for (Snake snake : snakes) {
                if (snake.getStart() == newPosition) {
                    newPosition = snake.getEnd();
                    System.out.println(player.getName() + " hit a snake! Sliding down from " + snake.getStart() + " to "
                            + snake.getEnd());
                    break;
                }
            }

            // Check for ladders
            for (Ladder ladder : ladders) {
                if (ladder.getStart() == newPosition) {
                    newPosition = ladder.getEnd();
                    System.out.println(player.getName() + " climbed a ladder! Moving up from " + ladder.getStart()
                            + " to " + ladder.getEnd());
                    break;
                }
            }
        }

        player.setPosition(newPosition);
        playerPositions.put(player.hashCode(), player);
        return newPosition;
    }

    public void printBoard() {
        System.out.println("\n==== SNAKE AND LADDER BOARD ====");
        System.out.println("Board size: " + size);

        // Print in 10x10 grid format
        for (int row = 9; row >= 0; row--) {
            for (int col = 0; col < 10; col++) {
                int position = row * 10 + col + 1;
                if (row % 2 == 1) {
                    position = row * 10 + (9 - col) + 1;
                }

                String cell = String.format("%3d", position);

                // Check if any player is on this position
                for (Player player : playerPositions.values()) {
                    if (player.getPosition() == position) {
                        cell = " " + player.getName().charAt(0) + " ";
                        break;
                    }
                }

                System.out.print(cell + " ");
            }
            System.out.println();
        }

        System.out.println("\nSnakes:");
        for (Snake snake : snakes) {
            System.out.println("  " + snake.getStart() + " -> " + snake.getEnd());
        }

        System.out.println("\nLadders:");
        for (Ladder ladder : ladders) {
            System.out.println("  " + ladder.getStart() + " -> " + ladder.getEnd());
        }
        System.out.println("================================\n");
    }

    public boolean hasWinner(Player player) {
        return player.getPosition() >= size;
    }

    public int getSize() {
        return size;
    }

    public List<Snake> getSnakes() {
        return snakes;
    }

    public List<Ladder> getLadders() {
        return ladders;
    }
}
