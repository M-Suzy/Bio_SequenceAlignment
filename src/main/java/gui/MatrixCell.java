package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class MatrixCell extends DefaultTableCellRenderer {

    MatrixCell() {
        this.setHorizontalAlignment(JLabel.CENTER);
        this.setBackground(Color.lightGray);
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int col) {

        return super.getTableCellRendererComponent(table, value,
                isSelected, hasFocus, row, col);
    }


}
