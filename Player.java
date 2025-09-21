public interface Player {
    String getName();

    int getPosition();

    void setPosition(int position);

    DIceSet.DiceRollResult takeTurn();
}
