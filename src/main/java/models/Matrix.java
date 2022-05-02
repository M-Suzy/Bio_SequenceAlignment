package models;

import enums.Arrow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Matrix {
    private int length;
    private int width;
    private Square[][] matrix;

    Matrix(){}
    public Matrix(int length, int width){
        this.width = width;
        this.length = length;
        matrix = new Square[width][length];
    }


    public Integer getValue(int row, int col){
        return matrix[row][col].getVal();
    }

    public List<Arrow> getArrow(int row, int col){
        return matrix[row][col].getArrows();
    }

    public String getArrowUnicodes(int row, int col){
        List<Arrow> arrows = getArrow(row, col);
        if(arrows.size() == 1) return arrows.get(0).getUnicode();
        if(arrows.size() == 3) return Arrow.LEFT.getUnicode()+Arrow.DIAGONAL.getUnicode()+Arrow.UP;
        //TODO
        return "";
    }
    public void setCell(int row, int col, int value){
        matrix[row][col].setVal(value);
    }

    public void setCell(int row, int col, int value, Arrow arrow){
        matrix[row][col].setVal(value);
        matrix[row][col].addArrow(arrow);
    }

    public void addArrows(int row, int col, Arrow arrow){
        if(matrix[row][col].getNumOfArrows() > 2) return;
        matrix[row][col].addArrow(arrow);
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Square[][] getMatrix() {
        return matrix;
    }


    public void setMatrix(Square[][] matrix) {
        this.matrix = matrix;
    }

    private static class Square{
        private Integer val;
        private List<Arrow> arrows = new ArrayList<>(3);
        Square(int val){
            this.val = val;
        }
        Square(int val, Arrow type){
            this.val = val;
            arrows.add(type);
        }

        Square(){}
        Integer getVal() {
            return val;
        }

        public void setVal(Integer val) {
            this.val = val;
        }

        public List<Arrow> getArrows() {
            return arrows;
        }

        int getNumOfArrows(){
           return arrows.size();
        }

        void addArrow(Arrow type){
            arrows.add(type);
        }
    }

    @Override
    public String toString() {
        for(int i = 0; i<width; i++){
            for (int j = 0; j<length; j++){
                System.out.print(matrix[i][j]==null? "" : getValue(i, j)+" ");
            }
            System.out.print("\n");
        }
        return "Matrix{" +
                "length=" + length +
                ", width=" + width +
                ", matrix=" + Arrays.toString(matrix) +
                '}';
    }
}
