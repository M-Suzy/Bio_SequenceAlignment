package gui;

import models.Cell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicInteger;

public class MatrixTable extends JPanel {
    private JFrame frame = new JFrame("Needleman and Wunsch’s DP Algorithm");
    private JTextField textField[][];
    private GridPanel gridPanel;
    private String longerSequence;
    private String shorterSequence;

    private final Cell[][] scoreTable;
    private final String[] alignment;
    private int height;
    private int width;
    private Font fBtn = new Font(Font.SANS_SERIF, Font.BOLD, 22);

    MatrixTable(String firstSequence, String secondSequence, Cell[][] scoreTable, String[] alignment) {
        this.alignment = alignment;
        if(firstSequence.length() > secondSequence.length()){
            this.longerSequence = firstSequence;
            this.shorterSequence = secondSequence;
        }
        else{
            this.longerSequence = secondSequence;
            this.shorterSequence = firstSequence;
        }

        this.height = shorterSequence.length() + 2;
        this.width = longerSequence.length() + 2;
        this.scoreTable = scoreTable;
        setBackground(new Color(4, 178, 217));

        textField = new JTextField[height][width];
        gridPanel = new GridPanel(new GridLayout(height, width, 1, 1));
        initTable();
        createGUI();
    }


    private void initTable() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if ((y == 0 && x == 1) || (y == 1 && x == 0)) {
                    textField[y][x] = new JTextField("-");
                } else if (y == 0 && x > 1) {
                    textField[y][x] = new JTextField("" + longerSequence.charAt(x - 2));
                } else if (x == 0 && y > 1) {
                    textField[y][x] = new JTextField("" + shorterSequence.charAt(y - 2));
                } else {
                    textField[y][x] = new JTextField();
                }
                textField[y][x].setForeground(Color.BLACK);
                textField[y][x].setEditable(false);
                textField[y][x].setPreferredSize(new Dimension(70, 60));
                textField[y][x].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 11));
                gridPanel.add(textField[y][x]);
            }
        }
    }

    public void createGUI() {

        setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        //add grid panel
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.anchor = GridBagConstraints.NORTH;
        add(gridPanel, gridBagConstraints);

        gridBagConstraints.anchor = GridBagConstraints.SOUTH;
        gridBagConstraints.weighty = 0.1;

        //add "next step" button to main panel
        JButton nextBtn = new JButton("Next Step");
        nextBtn.setFont(fBtn);
        nextBtn.setBackground(new Color(186, 85, 211));
        nextBtn.setForeground(new Color(224, 231, 34));
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.anchor = GridBagConstraints.SOUTH;
        gridBagConstraints.ipadx = 0;

        add(nextBtn, gridBagConstraints);

        AtomicInteger row = new AtomicInteger(1);
        AtomicInteger col = new AtomicInteger(1);

        nextBtn.addActionListener(e -> {
            textField[row.get()][col.get()].setText(scoreTable[row.get() - 1][col.get() - 1].getArrowsAndScore());
            textField[row.get()][0].setBackground(new Color(25, 150, 25));
            textField[0][col.get()].setBackground(new Color(25, 150, 25));
            if (col.get() == width - 1 && row.get() < height-1) {
                row.set(row.get() + 1);
                col.set(1);
            } else if (col.get() < width - 1) {
                col.set(col.get() + 1);
            }
            textField[row.get()][0].setBackground(new Color(242, 29, 168));
            textField[0][col.get()].setBackground(new Color(242, 29, 168));
        });

        JButton clearButton = new JButton("Clear Table");
        clearButton.setBackground(Color.BLACK);
        clearButton.setForeground(Color.WHITE);
        clearButton.setFont(fBtn);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.ipadx = 20;
        add(clearButton, gridBagConstraints);
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearGrid();
                row.set(1);
                col.set(1);
            }

            private void clearGrid() {
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        if ((y == 0 && x == 1) || (y == 1 && x == 0)) {
                            textField[y][x].setText("-");
                        } else if (y == 0 && x > 1) {
                            textField[y][x].setText("" + longerSequence.charAt(x - 2));
                        } else if (x == 0 && y > 1) {
                            textField[y][x].setText("" + shorterSequence.charAt(y - 2));
                        } else {
                            textField[y][x].setText("");
                        }
                        textField[y][x].setForeground(Color.BLACK);
                        textField[y][x].setBackground(Color.WHITE);

                    }
                }
            }
        });

        JButton alignmentsBtn = new JButton("Align");
        alignmentsBtn.setBackground(Color.blue);
        alignmentsBtn.setForeground(Color.ORANGE);
        alignmentsBtn.setFont(fBtn);
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.ipadx = 40;
        add(alignmentsBtn, gridBagConstraints);

        alignmentsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame resultFrame = new JFrame("Alignment");
                JPanel resultPanel = new JPanel();
                BoxLayout boxLayout = new BoxLayout(resultPanel, BoxLayout.Y_AXIS);
                resultPanel.setLayout(boxLayout);
                resultPanel.setBackground(Color.WHITE);
                resultFrame.setForeground(Color.BLACK);
                JLabel alignTitle = new JLabel("Alignment", JLabel.CENTER);
                JLabel dots = new JLabel("------------------------", JLabel.CENTER);
                dots.setFont(fBtn);
                alignTitle.setFont(fBtn);
                JLabel align1 = new JLabel(alignment[0], JLabel.CENTER);
                align1.setFont(fBtn);
                JLabel align2 = new JLabel(alignment[1], JLabel.CENTER);
                align2.setFont(fBtn);
                resultPanel.add(alignTitle);
                resultPanel.add(dots);
                resultPanel.add(align1);
                resultPanel.add(align2);
                resultFrame.add(resultPanel);
                resultFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                resultFrame.setMinimumSize(new Dimension(400, 200));
                resultFrame.setLocationRelativeTo(null);
                resultFrame.setVisible(true);
            }
        });
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(width * 80, height * 77));
        frame.getContentPane().add(this);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public class GridPanel extends JPanel {
        private static final long serialVersionUID = -6157041650150998205L;
        GridPanel(GridLayout layout) {
            super(layout);
        }
    }

}
