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

    public void addArrows(Arrow... arrow) {
        arrows.addAll(Arrays.asList(arrow));
    }

    public String getArrowsAndScore() {
        String res = "";
        for (Arrow arrow : arrows) {
            res += arrow.getUnicode();
        }
        return res + score;
    }

    public List<Arrow> getArrows() {
        return arrows;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setPrevCell(Cell prevCell) {
        this.prevCell = prevCell;
    }


    public int getRow() {
        return row;
    }


    public int getCol() {
        return col;
    }


    public Cell getPrevCell() {
        return prevCell;
    }


    @Override
    public String toString() {
        return "Cell(" + row + ", " + col + "): score=" + score + ", prevCell="
                + prevCell + "]";
    }
}