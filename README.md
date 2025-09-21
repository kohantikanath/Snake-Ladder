# 🐍🪜 Advanced Snake & Ladder Game

A feature-rich, customizable Snake & Ladder game implemented in Java with advanced gameplay mechanics and intelligent board generation.

## 🎮 Game Features

### ⭐ Core Features

- **Dynamic n×n Board**: Play on any board size (minimum 3×3)
- **Multiple Dice Support**: Use 1-3 dice simultaneously
- **Unlimited Players**: Support for any number of players (minimum 2)
- **Human & Bot Players**: Mix of interactive human players and automated bots
- **Smart Entity Placement**: Intelligent snake and ladder positioning with conflict prevention

### 🎯 Advanced Game Rules

- **Exact Win Condition**: Must land exactly on the final cell to win
- **Extra Turns on 6**: Rolling a 6 on any dice grants another turn
- **Player Collision**: Landing on another player sends them back to start
- **Row-Spanning Entities**: Snakes and ladders must span across different rows
- **No Entity Conflicts**: No overlapping snakes/ladders or circular paths

## 🏗️ Architecture

### Class Structure

```
📁 Snake&Ladder/
├── Entity.java          # Interface for snakes and ladders
├── Snake.java           # Snake implementation (head → tail)
├── Ladder.java          # Ladder implementation (bottom → top)
├── Player.java          # Player interface
├── HumanPlayer.java     # Human player with keyboard input
├── BotPlayer.java       # Automated bot player
├── PlayerFactory.java   # Factory for creating players
├── DIceSet.java         # Dice management with multiple dice support
├── Board.java           # Game board with intelligent entity placement
├── GameEngine.java      # Game loop and rule enforcement
└── Main.java            # Entry point and game configuration
```

### Design Patterns Used

- **Singleton Pattern**: `DIceSet` for centralized dice management
- **Factory Pattern**: `PlayerFactory` for creating different player types
- **Strategy Pattern**: Different player behavior implementations
- **Observer Pattern**: Board state tracking for player positions

## 🚀 How to Run

### Prerequisites

- Java 8 or higher
- Command line access

### Compilation & Execution

```bash
# Compile all Java files
javac *.java

# Run the game
java Main
```

## 🎲 Game Configuration

### 1. Board Setup

- **Minimum Size**: 3×3 (9 cells)
- **Recommended**: 10×10 (100 cells) for classic experience
- **Large Boards**: 15×15+ for extended gameplay

### 2. Dice Configuration

- **Single Dice**: Traditional gameplay (1-6)
- **Multiple Dice**: Enhanced strategy with 2-3 dice
- **Dice Logic**: Sum of all dice determines movement

### 3. Player Setup

- **Human Players**: Interactive with keyboard input
- **Bot Players**: Automated with strategic delays
- **Mixed Teams**: Any combination of human and bot players

## 🎮 Gameplay Mechanics

### Board Layout

```
🎯 Example 5×5 Board:
21  22  23  24  25
20  19  18  17  16
11  12  13  14  15
10   9   8   7   6
 1   2   3   4   5
```

### Entity Placement Rules

1. **No Same-Row Entities**: Snakes and ladders must span different rows
2. **No Overlapping**: Each cell can have maximum one entity
3. **No Circular Paths**: Prevents infinite loops
4. **Scaled Quantity**: Entity count scales with board size

### Win Conditions

```java
// Traditional Rule (removed)
if (position >= finalCell) { win(); }

// New Exact Rule
if (position == finalCell) { win(); }
else if (position > finalCell) { stayAtCurrentPosition(); }
```

### Extra Turn Logic

```java
if (diceResult.hasSix()) {
    grantExtraTurn();
    continue; // Same player rolls again
}
```

### Collision System

```java
if (landOnOccupiedCell()) {
    sendOtherPlayerToStart();
    moveCurrentPlayerToCell();
}
```

## 📋 Game Flow

### 1. Initialization Phase

```
📝 Configuration Input
├── Board dimension (n×n)
├── Number of dice (1-3)
├── Number of players (≥2)
└── Player types (Human/Bot)

🎲 Board Generation
├── Calculate entity count
├── Generate valid snakes
├── Generate valid ladders
└── Validate no conflicts
```

### 2. Game Loop

```
🔄 Turn Sequence
├── Display current player
├── Show current position
├── Roll dice
├── Calculate new position
├── Check win condition
├── Handle collisions
├── Apply snakes/ladders
├── Check for extra turn
└── Switch to next player (if no extra turn)
```

### 3. End Game

```
🏆 Victory Conditions
├── Player reaches exact final position
├── Display winner announcement
├── Show final leaderboard
└── Display final board state
```

## 🔧 Technical Implementation

### Intelligent Entity Generation

```java
// Dynamic entity count based on board size
int numEntities = Math.max(2, size / 20);

// Row validation for realistic placement
private boolean areInSameRow(int pos1, int pos2) {
    int row1 = (pos1 - 1) / boardDimension;
    int row2 = (pos2 - 1) / boardDimension;
    return row1 == row2;
}
```

### Multi-Dice System

```java
public class DiceRollResult {
    private List<Integer> individualRolls;  // [3, 6, 2]
    private int totalSum;                   // 11
    private boolean hasSix;                 // true
}
```

### Position Tracking

```java
// Snake-like board numbering
for (int row = boardDimension - 1; row >= 0; row--) {
    for (int col = 0; col < boardDimension; col++) {
        int position = row * boardDimension + col + 1;
        if (row % 2 == 1) {
            position = row * boardDimension + (boardDimension - 1 - col) + 1;
        }
    }
}
```

## 🎯 Example Game Session

```
=== WELCOME TO ADVANCED SNAKE AND LADDER GAME ===

Enter board dimension (n for n×n board, minimum 3): 5
Enter number of dice (1-3): 2
Enter number of players (minimum 2): 3

Player Setup:
1. Alice (Human)
2. Bob (Bot)
3. Charlie (Human)

=== GAME CONFIGURATION ===
Board: 5×5 (25 cells)
Dice: 2 dice
Players: 3
Snakes: 2
Ladders: 2

=== GAME RULES ===
• Roll exactly to land on the final cell to win
• Rolling a 6 gives you an extra turn
• Landing on another player sends them back to start
• Snakes slide you down, ladders climb you up
• No entities conflict on the same cell

--- Alice's turn ---
Current position: 0
Alice, press Enter to roll the dice...
Alice rolled: [4, 2] (Total: 6)
Alice moved from 0 to 6

🎲 Alice rolled a 6! Gets another turn! 🎲

--- Alice's turn ---
Current position: 6
Alice, press Enter to roll the dice...
Alice rolled: [3, 1] (Total: 4)
Alice moved from 6 to 10

Current positions:
Alice: 10
Bob: 0
Charlie: 0
```

## 🔍 Debugging Features

### Entity Information Display

```
Snakes:
  23 (row 5) -> 7 (row 2)
  19 (row 4) -> 3 (row 1)

Ladders:
  4 (row 1) -> 16 (row 4)
  11 (row 3) -> 22 (row 5)
```

### Position Validation

- Real-time position tracking
- Collision detection logging
- Move validation messages
- Win condition verification

## 🎨 Customization Options

### Board Themes

- Modify `printBoard()` method for custom visuals
- Add symbols for snakes (🐍) and ladders (🪜)
- Implement colored output for better UX

### Rule Variations

- Modify win conditions in `GameEngine.java`
- Adjust entity generation in `Board.java`
- Customize dice behavior in `DIceSet.java`

### Player Strategies

- Extend `BotPlayer` for different AI strategies
- Add probability-based decision making
- Implement learning algorithms

## 🐛 Common Issues & Solutions

### Compilation Errors

```bash
# Ensure all files are in same directory
javac *.java

# Check Java version
java -version
```

### Entity Generation Issues

- If few entities appear, increase board size
- Entities need different rows - this is intentional
- Random generation may require multiple attempts

### Input Handling

- Human players: Press Enter to roll dice
- Invalid inputs are handled with re-prompts
- Game validates all configuration inputs

### Feature Ideas

- [ ] Save/Load game state
- [ ] Network multiplayer support
- [ ] GUI implementation
- [ ] Sound effects and animations
- [ ] Tournament mode
- [ ] Custom board designs
- [ ] Achievement system
