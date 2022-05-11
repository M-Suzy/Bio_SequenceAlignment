package algos;

import enums.Arrow;
import enums.SequenceTypes;
import models.Cell;

public abstract class DPMatrixCalculator {

    protected String longerSeq;
    protected String shorterSeq;
    protected Cell[][] scoreTable;
    protected boolean tableIsFilledIn;
    protected boolean isInitialized;

    protected SequenceTypes sequenceType;
    protected int width, height;

    public DPMatrixCalculator(String firstSeq, String secondSeq, SequenceTypes sequenceType) {
        this.sequenceType = sequenceType;
        if (firstSeq.length() > secondSeq.length()) {
            this.longerSeq = firstSeq;
            this.shorterSeq = secondSeq;
            width = firstSeq.length()+1;
            height = secondSeq.length()+1;
        } else {
            this.longerSeq = secondSeq;
            this.shorterSeq = firstSeq;
            height = firstSeq.length()+1;
            width = secondSeq.length()+1;
        }
        scoreTable = new Cell[height][width];
    }

    public Cell[][] getCellTable(){
        ensureTableIsFilled();
        return scoreTable;
    }

    public int[][] getScoreTable() {
        ensureTableIsFilled();
        int[][] matrix = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                matrix[i][j] = scoreTable[i][j].getScore();
            }
        }
        return matrix;
    }

    protected void initScores() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                scoreTable[i][j].setScore(getInitialScore(i, j));
                if(i==0 && j!=0) {
                    scoreTable[i][j].addArrows(Arrow.LEFT);
                } else if(j==0 && i!=0) scoreTable[i][j].addArrows(Arrow.UP);
            }
        }
    }

    protected void initPointers() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                scoreTable[i][j].setPrevCell(getInitialCell(i, j));
            }
        }
    }

    protected void init() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                scoreTable[i][j] = new Cell(i, j);
            }
        }
        initScores();
        initPointers();

        isInitialized = true;
    }

    protected abstract Cell getInitialCell(int row, int col);

    protected abstract int getInitialScore(int row, int col);

    protected abstract void fillInCellSequence(Cell currentCell, Cell cellAbove,
                                       Cell cellToLeft, Cell cellDiagonal);

    protected abstract void fillInCellProtein(Cell currentCell, Cell cellAbove,
                                              Cell cellToLeft, Cell cellDiagonal);
    protected void fillIn() {
        for (int row = 1; row < height; row++) {
            for (int col = 1; col < width; col++) {
                Cell currentCell = scoreTable[row][col];
                Cell cellAbove = scoreTable[row - 1][col];
                Cell cellToLeft = scoreTable[row][col - 1];
                Cell cellDiagonal = scoreTable[row - 1][col - 1];
                if(sequenceType==SequenceTypes.PROTEIN){
                    fillInCellProtein(currentCell, cellAbove, cellToLeft, cellDiagonal);
                }
                else {
                    fillInCellSequence(currentCell, cellAbove, cellToLeft, cellDiagonal);
                }
            }
        }
        tableIsFilledIn = true;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    abstract protected Object getTraceback();

    protected void ensureTableIsFilled() {
        if (!isInitialized) {
            init();
        }
        if (!tableIsFilledIn) {
            fillIn();
        }
    }

}
