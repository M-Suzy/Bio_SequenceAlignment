package algos;

import enums.Arrow;
import models.Matrix;
import models.PAM250;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SequenceAlignment implements ScoreCalculator{
    private static boolean USING_PAM = false;
    private static int GAP_PENALTY_SCORE = -2;
    private static int MATCH_SCORE = 1;
    private static int MISMATCH_SCORE = -1;
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
    
    public static void setMismatchScore(int mismatchScore) {
    	MISMATCH_SCORE = mismatchScore;
    }
    
    
    public static void setMatchScore(int matchScore) {
    	MATCH_SCORE = matchScore;
    }
    
    public static void setGapPenaltyScore(int gapPenaltyScore) {
        GAP_PENALTY_SCORE = gapPenaltyScore;
    }
    
    @Override
    public int getScore(int row, int col) {
    	
        if(row == 1){
        	return (col-1)*GAP_PENALTY_SCORE;
        }
        else if(col == 1){
        	return (row-1)*GAP_PENALTY_SCORE;
        }
        else{
        	int valueLeft =  GAP_PENALTY_SCORE + matrix.getIndex(row, col - 1).getVal();
        	int valueUp = GAP_PENALTY_SCORE + matrix.getIndex(row - 1, col).getVal();
        	int valueDiagonal;
        	if(USING_PAM == false)
        	{
        		if( matrix.getIndex(0, col).getVal() ==  matrix.getIndex(row, 0).getVal()){
    			   valueDiagonal = MATCH_SCORE + matrix.getIndex(row - 1, col - 1).getVal();
    		   }
    		   else{
    			   valueDiagonal = MISMATCH_SCORE + matrix.getIndex(row - 1, col - 1).getVal();
    		   }
       		}
        	else{
    		   int index1 = Arrays.asList(PAM250.letters).indexOf(matrix.getIndex(0, col).getVal()); 
    		   int index2 = Arrays.asList(PAM250.letters).indexOf(matrix.getIndex(row, 0).getVal()); 
    		   valueDiagonal = PAM250Matrix[index1][index2] + matrix.getIndex(row - 1, col - 1).getVal();
    	   }
    	   return Math.max(valueDiagonal,Math.max(valueLeft,valueUp));
       }
    }

    @Override
    public List<Arrow> getArrow(int row, int col) {
    	List<Arrow> arr = new ArrayList<>(3);
    	if(matrix.getIndex(row, col).getVal() == matrix.getIndex(row - 1, col).getVal() + GAP_PENALTY_SCORE) {
    		arr.add(Arrow.UP);
        }
        if(matrix.getIndex(row, col).getVal() == matrix.getIndex(row, col - 1).getVal() + GAP_PENALTY_SCORE) {
        	arr.add(Arrow.LEFT);
        }
       
        if(USING_PAM == false){
        	if( matrix.getIndex(0, col).getVal() ==  matrix.getIndex(row, 0).getVal() && matrix.getIndex(row, col).getVal() == matrix.getIndex(row - 1, col - 1).getVal() + MATCH_SCORE ){
        		arr.add(Arrow.DIAGONAL);
        	}
        	if(matrix.getIndex(0, col).getVal() !=  matrix.getIndex(row, 0).getVal() && matrix.getIndex(row, col).getVal() == matrix.getIndex(row - 1, col - 1).getVal() + MISMATCH_SCORE ) {
        		arr.add(Arrow.DIAGONAL);
        	}
        }
        else{
        	int index1 = Arrays.asList(PAM250.letters).indexOf(matrix.getIndex(0, col).getVal()); 
  			int index2 = Arrays.asList(PAM250.letters).indexOf(matrix.getIndex(row, 0).getVal()); 
  			
        	if( matrix.getIndex(row, col).getVal() == matrix.getIndex(row - 1, col - 1).getVal() + PAM250Matrix[index1][index2]) {
        		arr.add(Arrow.DIAGONAL);
        	}
        }
        
        return arr;
    }
    
}
