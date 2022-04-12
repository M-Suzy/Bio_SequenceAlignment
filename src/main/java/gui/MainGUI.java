package gui;

import models.Matrix;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.event.*;

public class MainGUI extends JDialog {
    private JPanel contentPane;
    private JRadioButton proteinRadioButton;
    private JRadioButton genomicSequenceRadioButton;
    private JTextField firstSequence;
    private JTextField secondSequence;
    private JButton generateButton;
    private JPanel choiceWindow;
    private JPanel tableWindow;
    private JLabel seqSelectLbl;
    private JButton buttonOK;
    private JButton buttonCancel;
    private Matrix matrix;

    public MainGUI() {
        setContentPane(contentPane);
        setModal(true);
       // contentPane.add(choiceWindow);
       // contentPane.add(tableWindow);
       // choiceWindow.add(firstSequence);
       // choiceWindow.add(secondSequence);
       // choiceWindow.add(proteinRadioButton);
       // //choiceWindow.add(genomicSequenceRadioButton);

        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String seq1 = firstSequence.getText();
                String seq2 = secondSequence.getText();
                matrix = new Matrix(seq1, seq2);

                JTable matrixTable = new JTable(new AbstractTableModel() {
                    @Override
                    public int getRowCount() {
                        return matrix.getLength();
                    }

                    @Override
                    public int getColumnCount() {
                        return matrix.getWidth();
                    }

                    @Override
                    public Object getValueAt(int rowIndex, int columnIndex) {
                        return matrix.getMatrix()[rowIndex][columnIndex];
                    }
                });
                tableWindow.add(matrixTable);
            }
        });

    }


    public static void main(String[] args) {
        MainGUI dialog = new MainGUI();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
