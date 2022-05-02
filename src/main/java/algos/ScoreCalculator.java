package algos;

import enums.Arrow;
import enums.SequenceTypes;
import models.Matrix;
import models.PAM250;

import java.util.*;

public class ScoreCalculator{
    private static int GAP_PENALTY_SCORE = -2;
    private static int MATCH_SCORE = 1;
    private static int MISMATCH_SCORE = -1;
    private static Matrix matrix;
    private final SequenceTypes sequenceType;
    private static final int[][] PAM250Matrix = PAM250.PAM250Matrix;
    private static  Map<Character, Integer> lettersAndPos;

    private String firstSequence;
    private String secondSequence;

    ScoreCalculator(String firstSequence, String secondSequence, SequenceTypes sequenceType){
        this.sequenceType = sequenceType;
        if(sequenceType == SequenceTypes.PROTEIN){
            initLettersAndPos(PAM250.letters);
        }
        this.firstSequence = firstSequence;
        this.secondSequence = secondSequence;
        matrix = new Matrix(firstSequence.length()+1, secondSequence.length()+1);
    }

    private void initLettersAndPos(char[] letters){
        for(int i = 0; i< letters.length; i++){
            lettersAndPos.put(letters[i], i);
        }
    }

    public static void setMismatchScore(int mismatchScore) {
        MISMATCH_SCORE = mismatchScore;
    }


    public static void setMatchScore(int matchScore) {
        MATCH_SCORE = matchScore;
    }

    public static void setGapPenaltyScore(int gapPenaltyScore) {
        GAP_PENALTY_SCORE = gapPenaltyScore;
    }


    private void checkIndex(int row, int col){
        if(row<0 || row>= matrix.getWidth() || col < 0 || col >= matrix.getLength()){
            throw new IndexOutOfBoundsException("Position is out of bounds!");
        }
    }


    public String step(int row, int col) {
        checkIndex(row, col);
        if(row == 0 && col == 0) {
            matrix.setCell(0, 0, 0);
            return ""+0;
        }
        if(row == 0){
            matrix.setCell(row, col, col*GAP_PENALTY_SCORE, Arrow.LEFT);
            return ""+Arrow.LEFT.getUnicode()+col*GAP_PENALTY_SCORE;
        }
        if(col == 0){
            matrix.setCell(row, col, row*GAP_PENALTY_SCORE, Arrow.UP);
            return ""+ Arrow.UP.getUnicode()+col*GAP_PENALTY_SCORE;
        }
        int valueLeft =  GAP_PENALTY_SCORE + matrix.getValue(row, col - 1);
        int valueUp = GAP_PENALTY_SCORE + matrix.getValue(row - 1, col);
        int valueDiagonal;
        if (sequenceType == SequenceTypes.PROTEIN ){
            int idRow = lettersAndPos.get(firstSequence.charAt(row));
            int idCol = lettersAndPos.get(secondSequence.charAt(col));
            valueDiagonal = PAM250Matrix[idRow][idCol] + matrix.getValue(row - 1, col - 1);
        }
        else valueDiagonal = Objects.equals(firstSequence.charAt(row-1), secondSequence.charAt(col-1)) ?
                MATCH_SCORE + matrix.getValue(row - 1, col - 1) :
                MISMATCH_SCORE + matrix.getValue(row - 1, col - 1);

        return decideMaxScoreAndDir(row, col, valueDiagonal, valueLeft, valueUp);
    }

    private String decideMaxScoreAndDir(int row, int col, int valueDiagonal, int valueLeft, int valueUp){
        int max = Math.max(Math.max(valueLeft,valueUp), valueDiagonal);
        matrix.setCell(row, col, max);
        if(max == valueUp)
           matrix.addArrows(row, col, Arrow.UP);
        if (max == valueDiagonal)
            matrix.addArrows(row, col, Arrow.DIAGONAL);
        if (max == valueLeft)
            matrix.addArrows(row, col, Arrow.LEFT);
        return "" + max;
    }



}
