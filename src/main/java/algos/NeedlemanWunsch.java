package algos;

import enums.Arrow;
import enums.SequenceTypes;
import models.Cell;

public class NeedlemanWunsch extends SequenceAlignment {

    public NeedlemanWunsch(String firstSeq, String secondSeq) {
        super(firstSeq, secondSeq);
    }

    public NeedlemanWunsch(String firstSeq, String secondSeq, int match, int mismatch, int gap) {
        super(firstSeq, secondSeq, match, mismatch, gap, SequenceTypes.GENOMIC_SEQUENCE);
    }

    public NeedlemanWunsch(String firstSeq, String secondSeq, int match, int mismatch, int gap, SequenceTypes type) {
        super(firstSeq, secondSeq, match, mismatch, gap, type);
    }

    protected void fillInCell(Cell currentCell, Cell cellAbove, Cell cellToLeft, Cell diagonalCell) {
        int upScore = cellAbove.getScore() + gap;
        int leftScore = cellToLeft.getScore() + gap;
        int matchOrMismatchScore = diagonalCell.getScore();

        if (longerSeq.charAt(currentCell.getCol() - 1) == shorterSeq.charAt(currentCell.getRow() - 1))
        {
            matchOrMismatchScore += match;
        } else {
            matchOrMismatchScore += mismatch;
        }
        if (upScore >= leftScore) {
            if (matchOrMismatchScore >= upScore) {
                currentCell.setScore(matchOrMismatchScore);
                if(matchOrMismatchScore==upScore){
                    currentCell.addArrows(Arrow.DIAGONAL, Arrow.UP);
                } else currentCell.addArrows(Arrow.DIAGONAL);
                currentCell.setPrevCell(diagonalCell);
            } else {
                currentCell.setScore(upScore);
                if(upScore==leftScore) {
                    currentCell.addArrows(Arrow.LEFT, Arrow.UP);
                } else currentCell.addArrows(Arrow.UP);
                currentCell.setPrevCell(cellAbove);
            }
        } else {
            if (matchOrMismatchScore >= leftScore) {
                currentCell.setScore(matchOrMismatchScore);
                if(matchOrMismatchScore==leftScore){
                    currentCell.addArrows(Arrow.LEFT, Arrow.DIAGONAL);
                } else currentCell.addArrows(Arrow.DIAGONAL);
                currentCell.setPrevCell(diagonalCell);
            } else {
                currentCell.setScore(leftScore);
                currentCell.addArrows(Arrow.LEFT);
                currentCell.setPrevCell(cellToLeft);
            }
        }

    }


    protected void fillInCellProtein(Cell currentCell, Cell cellAbove, Cell cellToLeft, Cell diagonalCell) {
        int upScore = cellAbove.getScore() + gap;
        int leftScore = cellToLeft.getScore() + gap;
        int idRow = lettersAndPos.get(shorterSeq.charAt(currentCell.getRow() - 1));
        int idCol = lettersAndPos.get(longerSeq.charAt(currentCell.getCol() - 1));
        int matchOrMismatchScore = diagonalCell.getScore()+PAM250Matrix[idRow][idCol];

        if (upScore >= leftScore) {
            if (matchOrMismatchScore >= upScore) {
                currentCell.setScore(matchOrMismatchScore);
                if(matchOrMismatchScore==upScore){
                    currentCell.addArrows(Arrow.DIAGONAL, Arrow.UP);
                } else currentCell.addArrows(Arrow.DIAGONAL);
                currentCell.setPrevCell(diagonalCell);
            } else {
                currentCell.setScore(upScore);
                if(upScore==leftScore) {
                    currentCell.addArrows(Arrow.LEFT, Arrow.UP);
                } else currentCell.addArrows(Arrow.UP);
                currentCell.setPrevCell(cellAbove);
            }
        } else {
            if (matchOrMismatchScore >= leftScore) {
                currentCell.setScore(matchOrMismatchScore);
                if(matchOrMismatchScore==leftScore){
                    currentCell.addArrows(Arrow.LEFT, Arrow.DIAGONAL);
                } else currentCell.addArrows(Arrow.DIAGONAL);
                currentCell.setPrevCell(diagonalCell);
            } else {
                currentCell.setScore(leftScore);
                currentCell.addArrows(Arrow.LEFT);
                currentCell.setPrevCell(cellToLeft);
            }
        }

    }

    @Override
    protected boolean traceBackIsNotDone(Cell currentCell) {
        return currentCell.getPrevCell() != null;
    }

    @Override
    protected Cell getTracebackStartingCell() {
        return scoreTable[height - 1][width - 1];
    }


    protected Cell getInitialPointer(int row, int col) {
        if (row == 0 && col != 0) {
            return scoreTable[row][col - 1];
        } else if (col == 0 && row != 0) {
            return scoreTable[row - 1][col];
        } else {
            return null;
        }
    }

    protected int getInitialScore(int row, int col) {
        if (row == 0 && col != 0) {
            return col * gap;
        } else if (col == 0 && row != 0) {
            return row * gap;
        } else {
            return 0;
        }
    }

}