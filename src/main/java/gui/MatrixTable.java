package gui;

import models.Matrix;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MatrixTable extends JPanel{
    JTable matrixTbl ;
    private Matrix matrix;
    MatrixTable(Matrix matrix){
        setLayout(new BorderLayout());

        DefaultTableModel model = new DefaultTableModel(matrix.getWidth(),  matrix.getLength()) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return String.class;
            }
        };

        JTable table = new JTable(model);
        for (int row = 0; row < model.getRowCount(); row++) {
            for (int col = 0; col < model.getColumnCount(); col++) {
                String s = matrix.getMatrix()[row][col]==null? "": "" +matrix.getMatrix()[row][col];
                model.setValueAt(s, row, col);
            }
        }
        table.setDefaultRenderer(String.class, new MatrixCell());

        table.setFocusable(false);
        table.setShowGrid(false);
        table.setRowMargin(0);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setRowSelectionAllowed(false);
        table.setVisible(true);
        table.setPreferredSize(new Dimension(800, 800));
    }


}
