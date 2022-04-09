package algos;

import enums.Arrow;
import models.Matrix;
import models.PAM250;

import java.util.Map;

public class SequenceAlignment implements ScoreCalculator{
    private static int GAP_PENALTY_SCORE = -2;
    private static Matrix matrix;
    private static final int[][] PAM250Matrix = PAM250.PAM250Matrix;
    private static Map<Character, Integer> lettersAndPos;

    SequenceAlignment(){
        initLettersAndPos(PAM250.letters);
    }

    private void initLettersAndPos(char[] letters){
        for(int i = 0; i< letters.length; i++){
            lettersAndPos.put(letters[i], i);
        }
    }

    public static void setGapPenaltyScore(int gapPenaltyScore) {
        GAP_PENALTY_SCORE = gapPenaltyScore;
    }
    
    @Override
    public int getScore(int row, int col) {
        return 0;
    }

    @Override
    public Arrow getArrow(int upScore, int leftScore, int diagonalScore) {
        return null;
    }
}
