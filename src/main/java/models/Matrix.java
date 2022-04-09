package models;

import enums.Arrow;

import java.util.ArrayList;
import java.util.List;

public class Matrix {
    private int length;
    private int width;
    private Square[][] matrix;
    Matrix(){}
    Matrix(String firstSequence, String secondSequence){
        this.width = firstSequence.length()+2;
        this.length = secondSequence.length()+2;
        matrix = new Square[width][length];
        setSequenceInMatrix(firstSequence.toCharArray(),
                secondSequence.toCharArray());
    }

    private void setSequenceInMatrix(char[] firstSequence, char[] secondSequence){
        for(int i = 2; i<length; i++){
            matrix[0][i] = new Square(secondSequence[i-2]);
        }
        for (int i = 2; i<width; i++){
            matrix[i][0] = new Square(firstSequence[i-2]);
        }
    }

    public Square getIndex(int row, int col){
        checkIndex(row, col);
        return matrix[row][col];
    }

    public void setSquare(int row, int col, Square square){
        checkIndex(row, col);
        matrix[row][col] = square;
    }
    private boolean checkIndex(int row, int col){
        if(row<0 || row>=length || col < 0 || col >= width){
            throw new IndexOutOfBoundsException("Position is out of bounds!");
        }
        return true;
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
        int val;
        List<Arrow> arrows = new ArrayList<>(3);
        Square(int val){
            this.val = val;
        }
        Square(int val, Arrow type){
            this.val = val;
            arrows.add(type);
        }
        void addArrow(Arrow type){
            arrows.add(type);
        }
    }
}
