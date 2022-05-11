package algos;

import enums.SequenceTypes;
import models.Cell;
import models.PAM250;

import java.util.HashMap;
import java.util.Map;

public abstract class SequenceAlignment extends DPMatrixCalculator{
    protected int match;
    protected int mismatch;
    protected int gap;
    protected String[] alignments;
    protected final SequenceTypes sequenceType;

    protected static final int[][] PAM250Matrix = PAM250.PAM250Matrix;
    protected static Map<Character, Integer> lettersAndPos;

    public SequenceAlignment(String firstSeq, String secondSeq, int match,
                             int mismatch, int gap, SequenceTypes sequenceType) {
        super(firstSeq, secondSeq, sequenceType);

        this.match = match;
        this.mismatch = mismatch;
        this.gap = gap;
        this.sequenceType = sequenceType;
    }

    public SequenceAlignment(String firstSequence, String secondSequence, int gapScore, SequenceTypes sequenceType) {
        super(firstSequence, secondSequence, sequenceType);
        this.gap = gapScore;
        this.sequenceType = sequenceType;
        initLettersAndPos();
    }

    private void initLettersAndPos(){
        lettersAndPos = new HashMap<>(PAM250.letters.length);
        for(int i = 0; i< PAM250.letters.length; i++){
            lettersAndPos.put(PAM250.letters[i], i);
        }
    }

    protected Object getTraceback() {
        StringBuilder alignFirst = new StringBuilder();
        StringBuilder alignSecond = new StringBuilder();
        StringBuilder alignmentLines = new StringBuilder();
        Cell currentCell = getTracebackStartingCell();
        while (currentCell.getPrevCell() != null) {
            if (currentCell.getRow() - currentCell.getPrevCell().getRow() == 1) {
                alignSecond.insert(0, shorterSeq.charAt(currentCell.getRow() - 1));
            } else {
                alignSecond.insert(0, '—');
            }
            if (currentCell.getCol() - currentCell.getPrevCell().getCol() == 1) {
                alignFirst.insert(0, longerSeq.charAt(currentCell.getCol() - 1));
            } else {
                alignSecond.insert(0, '—');
            }
            if( shorterSeq.charAt(currentCell.getRow() - 1) == longerSeq.charAt(currentCell.getCol() - 1)) {
                alignmentLines.insert(0, "|");
            }
            else alignmentLines.insert(0, " ");
            currentCell = currentCell.getPrevCell();
        }

        String[] alignments = new String[] { alignFirst.toString(), alignmentLines.toString(), alignSecond.toString()};

        return alignments;
    }

    public int getAlignmentScoreSequence() {
        if (alignments == null) {
            getAlignment();
        }
        int score = 0;
        for (int i = 0; i < alignments[0].length(); i++) {
            char c1 = alignments[0].charAt(i);
            char c2 = alignments[2].charAt(i);
            if (c1 == '—' || c2 == '—') {
                score += gap;
            } else if (c1 == c2) {
                score += match;
            } else {
                score += mismatch;
            }
        }
        return score;
    }

    public int getAlignmentScoreProtein() {
        if (alignments == null) {
            getAlignment();
        }
        int score = 0;
        for (int i = 0; i < alignments[0].length(); i++) {
            char c1 = alignments[0].charAt(i);
            char c2 = alignments[2].charAt(i);
            if (c1 == '—' || c2 == '—') {
                score += gap;
            } else {
                int idRow = lettersAndPos.get(c1);
                int idCol = lettersAndPos.get(c2);
                score += PAM250Matrix[idRow][idCol];
            }
        }
        return score;
    }

    public String[] getAlignment() {
        ensureTableIsFilledIn();
        alignments = (String[]) getTraceback();
        return alignments;
    }

    public static Map<Character, Integer> getLettersAndPos() {
        return lettersAndPos;
    }

    public static int[][] getPAM250Matrix() {
        return PAM250Matrix;
    }

    protected abstract Cell getTracebackStartingCell();
}
