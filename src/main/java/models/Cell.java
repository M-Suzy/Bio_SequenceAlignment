package models;

import enums.Arrow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Cell {
    private Cell prevCell;
    private int score;
    private int row;
    private int col;

    private List<Arrow> arrows;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        arrows = new ArrayList<>(3);
    }

    public void addArrows(Arrow... arrow){
        arrows.addAll(Arrays.asList(arrow));
    }

    public String getArrowsAndScore(){
        String res = "";
        for(Arrow arrow : arrows){
            res+=arrow.getUnicode();
        }
        return res+=score;
    }

    public List<Arrow> getArrows() {
        return arrows;
    }

    /**
     * @param score
     * the score to set
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * @param prevCell
     * the prevCell to set
     */
    public void setPrevCell(Cell prevCell) {
        this.prevCell = prevCell;
    }

    /**
     * @return the row
     */
    public int getRow() {
        return row;
    }

    /**
     * @return the col
     */
    public int getCol() {
        return col;
    }

    /**
     * @return the prevCell
     */
    public Cell getPrevCell() {
        return prevCell;
    }


    @Override
    public String toString() {
        return "Cell(" + row + ", " + col + "): score=" + score + ", prevCell="
                + prevCell + "]";
    }
}