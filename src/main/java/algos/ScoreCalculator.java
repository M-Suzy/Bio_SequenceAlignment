package algos;

import enums.Arrow;

public interface ScoreCalculator {
    int getScore(int row, int col);
    Arrow getArrow(int upScore, int leftScore, int diagonalScore);
}
