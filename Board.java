import java.util.*;

public class Board {
    private int size;
    private int boardDimension; // For n*n board (square root of size)
    private List<Snake> snakes;
    private List<Ladder> ladders;
    private Map<Integer, Player> playerPositions;
    private Set<Integer> occupiedCells; // To track cells with entities

    public Board(int boardDimension) {
        this.boardDimension = boardDimension;
        this.size = boardDimension * boardDimension;
        this.snakes = new ArrayList<>();
        this.ladders = new ArrayList<>();
        this.playerPositions = new HashMap<>();
        this.occupiedCells = new HashSet<>();
        initializeBoard();
    }

    private void initializeBoard() {
        // Dynamically create snakes and ladders based on board size
        int numEntities = Math.max(2, size / 20); // Scale entities with board size
        Random random = new Random();

        // Add snakes
        for (int i = 0; i < numEntities; i++) {
            int head, tail;
            int attempts = 0;
            do {
                head = random.nextInt(size - size / 4) + size / 4; // Upper part of board
                tail = random.nextInt(head - 1) + 1; // Lower than head
                attempts++;
            } while ((occupiedCells.contains(head) || occupiedCells.contains(tail) ||
                    head == tail || head == size) && attempts < 100);

            if (attempts < 100 && isValidSnake(head, tail)) {
                snakes.add(new Snake(head, tail));
                occupiedCells.add(head);
                occupiedCells.add(tail);
            }
        }

        // Add ladders
        for (int i = 0; i < numEntities; i++) {
            int bottom, top;
            int attempts = 0;
            do {
                bottom = random.nextInt(size - size / 4) + 1; // Lower part of board
                top = random.nextInt(size - bottom) + bottom + 1; // Higher than bottom
                attempts++;
            } while ((occupiedCells.contains(bottom) || occupiedCells.contains(top) ||
                    bottom == top || top == size) && attempts < 100);

            if (attempts < 100 && isValidLadder(bottom, top)) {
                ladders.add(new Ladder(bottom, top));
                occupiedCells.add(bottom);
                occupiedCells.add(top);
            }
        }
    }

    private boolean isValidSnake(int head, int tail) {
        // Ensure no circular paths and head > tail
        // Ensure head and tail are not in the same row
        return head > tail && !wouldCreateCircle(head, tail, true) && !areInSameRow(head, tail);
    }

    private boolean isValidLadder(int bottom, int top) {
        // Ensure no circular paths and top > bottom
        // Ensure bottom and top are not in the same row
        return top > bottom && !wouldCreateCircle(bottom, top, false) && !areInSameRow(bottom, top);
    }

    private boolean areInSameRow(int pos1, int pos2) {
        // Convert 1-based positions to 0-based for calculation
        int row1 = getRowFromPosition(pos1);
        int row2 = getRowFromPosition(pos2);
        return row1 == row2;
    }

    private int getRowFromPosition(int position) {
        // Convert 1-based position to 0-based row
        return (position - 1) / boardDimension;
    }

    private int getColumnFromPosition(int position) {
        // Convert 1-based position to 0-based column
        int row = getRowFromPosition(position);
        int col = (position - 1) % boardDimension;

        // For odd rows (when counting from bottom), reverse the column order
        if (row % 2 == 1) {
            col = boardDimension - 1 - col;
        }

        return col;
    }

    private boolean wouldCreateCircle(int start, int end, boolean isSnake) {
        // Simple check to prevent obvious circular paths
        if (isSnake) {
            // Check if tail connects to any ladder that goes back to or above head
            for (Ladder ladder : ladders) {
                if (ladder.getStart() == end && ladder.getEnd() >= start) {
                    return true;
                }
            }
        } else {
            // Check if top connects to any snake that goes back to or below bottom
            for (Snake snake : snakes) {
                if (snake.getStart() == end && snake.getEnd() <= start) {
                    return true;
                }
            }
        }
        return false;
    }

    public int movePlayer(Player player, DIceSet.DiceRollResult diceResult) {
        int currentPosition = player.getPosition();
        int diceRoll = diceResult.getTotalSum();
        int newPosition = currentPosition + diceRoll;

        // Check exact win condition - must land exactly on winning cell
        if (newPosition > size) {
            System.out.println(player.getName() + " rolled too high! Need exactly " +
                    (size - currentPosition) + " to win. Staying at position " + currentPosition);
            return currentPosition; // Stay at current position
        }

        // Check if player has won exactly
        if (newPosition == size) {
            player.setPosition(newPosition);
            return newPosition;
        }

        // Check for player collision - send other player back to start
        Player collidedPlayer = getPlayerAtPosition(newPosition);
        if (collidedPlayer != null && !collidedPlayer.equals(player)) {
            collidedPlayer.setPosition(0);
            playerPositions.remove(collidedPlayer.hashCode());
            playerPositions.put(collidedPlayer.hashCode(), collidedPlayer);
            System.out.println(collidedPlayer.getName() + " was sent back to start by " + player.getName() + "!");
        }

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

        player.setPosition(newPosition);
        playerPositions.put(player.hashCode(), player);
        return newPosition;
    }

    private Player getPlayerAtPosition(int position) {
        for (Player player : playerPositions.values()) {
            if (player.getPosition() == position) {
                return player;
            }
        }
        return null;
    }

    public void printBoard() {
        System.out.println("\n==== SNAKE AND LADDER BOARD (" + boardDimension + "x" + boardDimension + ") ====");
        System.out.println("Board size: " + size + " cells");

        // Print in nxn grid format
        for (int row = boardDimension - 1; row >= 0; row--) {
            for (int col = 0; col < boardDimension; col++) {
                int position = row * boardDimension + col + 1;
                if (row % 2 == 1) {
                    position = row * boardDimension + (boardDimension - 1 - col) + 1;
                }

                String cell = String.format("%" + (String.valueOf(size).length() + 1) + "d", position);

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
            int headRow = getRowFromPosition(snake.getStart()) + 1; // Convert to 1-based
            int tailRow = getRowFromPosition(snake.getEnd()) + 1;
            System.out.println("  " + snake.getStart() + " (row " + headRow + ") -> " +
                    snake.getEnd() + " (row " + tailRow + ")");
        }

        System.out.println("\nLadders:");
        for (Ladder ladder : ladders) {
            int bottomRow = getRowFromPosition(ladder.getStart()) + 1; // Convert to 1-based
            int topRow = getRowFromPosition(ladder.getEnd()) + 1;
            System.out.println("  " + ladder.getStart() + " (row " + bottomRow + ") -> " +
                    ladder.getEnd() + " (row " + topRow + ")");
        }
        System.out.println("===========================" + "=".repeat(boardDimension) + "\n");
    }

    public boolean hasWinner(Player player) {
        return player.getPosition() == size; // Must be exactly equal
    }

    public int getSize() {
        return size;
    }

    public int getBoardDimension() {
        return boardDimension;
    }

    public List<Snake> getSnakes() {
        return snakes;
    }

    public List<Ladder> getLadders() {
        return ladders;
    }

    public void addPlayer(Player player) {
        playerPositions.put(player.hashCode(), player);
    }

    public void removePlayer(Player player) {
        playerPositions.remove(player.hashCode());
    }
}
