package gui;

import algos.SequenceAlignment;
import enums.SequenceTypes;
import models.Cell;
import models.PAM250;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class MatrixTable extends JPanel {
    private final JFrame frame;
    private final JTextField[][] textField;
    private final GridPanel gridPanel;
    private final String longerSequence;
    private final String shorterSequence;

    private final int height;
    private final int width;
    private final int score;
    private final int gapScore;

    private final Cell[][] scoreTable;
    private final String[] alignment;

    private final SequenceTypes sequenceType;
    private static final int[][] PAM250Matrix = PAM250.PAM250Matrix;
    private static Map<Character, Integer> lettersAndPos;

    private static final Font FONT_18 = new Font(Font.SANS_SERIF, Font.BOLD, 18);

    MatrixTable(String firstSequence, String secondSequence, SequenceTypes sequenceType,
                Cell[][] scoreTable, String[] alignment,
                int alignScore, int gapScore) {
        this.gapScore = gapScore;
        this.alignment = alignment;
        this.score = alignScore;
        this.sequenceType = sequenceType;
        frame = new JFrame("Needleman and Wunsch’s DP Algorithm");
        if (firstSequence.length() > secondSequence.length()) {
            this.longerSequence = firstSequence;
            this.shorterSequence = secondSequence;
        } else {
            this.longerSequence = secondSequence;
            this.shorterSequence = firstSequence;
        }
        lettersAndPos = SequenceAlignment.getLettersAndPos();
        this.height = shorterSequence.length() + 2;
        this.width = longerSequence.length() + 2;
        this.scoreTable = scoreTable;
        textField = new JTextField[height][width];
        gridPanel = new GridPanel(new GridLayout(height, width, 1, 1));

        setBackground(new Color(4, 178, 217));
        initTable();
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

    /**
     * Sets all matrix components and adds actionListeners accordingly
     */
    public void createGUI() {

        setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridPanel.setBackground(Color.WHITE);
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
        JButton nextBtn = new JButton("Next");
        nextBtn.setFont(FONT_18);
        nextBtn.setBackground(new Color(186, 85, 211));
        nextBtn.setForeground(new Color(224, 231, 34));
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.ipadx = 40;

        add(nextBtn, gridBagConstraints);

        JTextField pamScore = new JTextField();
        if(sequenceType==SequenceTypes.PROTEIN){
            pamScore.setText("PAM score = ");
            pamScore.setEditable(false);
            pamScore.setFont(FONT_18);
            pamScore.setForeground(Color.BLACK);
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 4;
            gridBagConstraints.gridwidth = 3;
            gridBagConstraints.ipadx = 80;
            add(pamScore, gridBagConstraints);
        }

        AtomicInteger row = new AtomicInteger(1);
        AtomicInteger col = new AtomicInteger(1);

        nextBtn.addActionListener(e -> {
            if (row.get() == height-1 && col.get() == width-1) {
                drawTraceBack();
            }
            textField[row.get()][col.get()].setText(scoreTable[row.get() - 1][col.get() - 1].getArrowsAndScore());
            textField[row.get()][0].setBackground(new Color(25, 150, 25));
            textField[0][col.get()].setBackground(new Color(25, 150, 25));
            if(sequenceType==SequenceTypes.PROTEIN && row.get()>1 && col.get()>1){
               pamScore.setText("PAM250 score = " + showPAMScore(row.get(), col.get()));
            }
            if (col.get() == width - 1 && row.get() < height - 1) {
                row.set(row.get() + 1);
                col.set(1);
            } else if (col.get() < width - 1) {
                col.set(col.get() + 1);
            }
            textField[row.get()][0].setBackground(new Color(242, 29, 168));
            textField[0][col.get()].setBackground(new Color(242, 29, 168));

        });

       createCLearTableUI(gridBagConstraints, row, col);

        JButton alignmentsBtn = new JButton("Get Alignment");
        alignmentsBtn.setBackground(Color.BLUE);
        alignmentsBtn.setForeground(Color.WHITE);
        alignmentsBtn.setFont(FONT_18);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = GridBagConstraints.SOUTH;
        gridBagConstraints.ipadx = 80;
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
                JTextField[][] alignGrid = new JTextField[3][alignment[0].length()];

                JLabel alignTitle = new JLabel("Alignment", JLabel.CENTER);
                JLabel dots = new JLabel("------------------------", JLabel.CENTER);
                dots.setFont(FONT_18);
                alignTitle.setFont(FONT_18);
                resultPanel.add(alignTitle);
                resultPanel.add(dots);
                GridPanel gridPanel1 = new GridPanel(new GridLayout(3, alignment[0].length()));
                for (int i = 0; i < 3; i++) {
                    alignGrid[i] = new JTextField[alignment[0].length()];
                    for (int j = 0; j < alignGrid[i].length; j++) {
                        alignGrid[i][j] = new JTextField("" + alignment[i].charAt(j));
                        alignGrid[i][j].setForeground(Color.BLACK);
                        alignGrid[i][j].setBackground(Color.WHITE);
                        alignGrid[i][j].setVisible(true);
                        alignGrid[i][j].setEditable(false);
                        alignGrid[i][j].setFont(FONT_18);
                        alignGrid[i][j].setBorder(null);
                        gridPanel1.add(alignGrid[i][j]);
                    }
                }
                JLabel scoreLbl = new JLabel("Alignment score = " + score);
                scoreLbl.setForeground(Color.red);
                scoreLbl.setFont(FONT_18);
                resultPanel.add(gridPanel1);
                resultPanel.add(scoreLbl);
                resultFrame.add(resultPanel);
                resultFrame.setMinimumSize(new Dimension(400, 200));
                resultFrame.setLocationRelativeTo(null);
                resultFrame.setVisible(true);
            }
        });

        createFormulaUI(gridBagConstraints);

        frame.setMinimumSize(new Dimension(width * 80, height * 80));
        frame.getContentPane().add(this);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    private void createCLearTableUI(GridBagConstraints gridBagConstraints,
                                    AtomicInteger row, AtomicInteger col){

        JButton clearButton = new JButton("Clear Table");
        clearButton.setBackground(Color.BLACK);
        clearButton.setForeground(Color.WHITE);
        clearButton.setFont(FONT_18);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.anchor = GridBagConstraints.SOUTH;
        gridBagConstraints.ipadx = 0;
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
    }

    private void createFormulaUI(GridBagConstraints gridBagConstraints){
        JButton seeFormula = new JButton("See Formula");
        seeFormula.setFont(FONT_18);
        seeFormula.setBackground(new Color(255, 127, 2));
        seeFormula.setForeground(Color.BLACK);
        seeFormula.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame formulaFrame = new JFrame("Used Formula");
                JPanel formulaPanel = new JPanel();
                BoxLayout boxLayout = new BoxLayout(formulaPanel, BoxLayout.Y_AXIS);
                formulaPanel.setLayout(boxLayout);
                formulaPanel.setBackground(Color.LIGHT_GRAY);
                formulaPanel.setForeground(Color.BLACK);
                JLabel[] formula = new JLabel[4];

                formula[0] = new JLabel("D(1, 1) = 0");
                formula[1] = new JLabel("D(1,j) = D(1,j-1) + gap score");
                formula[2] = new JLabel("D(i,1) = D(i-1,1) + gap score");
                formula[3] = new JLabel("D(i,j) = max {D(i-1,j-1) + match/mismatch, D(i,j-1) + gap score, D(i-1,j) + gap score}");
                for (JLabel label : formula) {
                    label.setFont(new Font("Helvetica Neue", Font.PLAIN, 18));
                    formulaPanel.add(label);
                }
                formulaFrame.add(formulaPanel);
                formulaFrame.setMinimumSize(new Dimension(400, 200));
                formulaFrame.setLocationRelativeTo(null);
                formulaFrame.setVisible(true);
            }
        });

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.ipadx = 20;

        add(seeFormula, gridBagConstraints);
    }

    private void drawTraceBack() {
        Cell currentCell = scoreTable[height-2][width-2];
        Color lightRed = new Color(247, 117, 117);
        textField[height-1][width-1].setBackground(lightRed);
        while(currentCell.getPrevCell()!=null){
            textField[currentCell.getRow()+1][currentCell.getCol()+1].setBackground(lightRed);
            currentCell=currentCell.getPrevCell();
        }
        textField[1][1].setBackground(Color.yellow);
    }

    private int showPAMScore(int row, int col){
        int idRow = lettersAndPos.get(textField[row][0].getText().charAt(0));
        int idCol = lettersAndPos.get(textField[0][col].getText().charAt(0));
        return PAM250Matrix[idRow][idCol];

    }

    public class GridPanel extends JPanel {
        private static final long serialVersionUID = -6157041650150998205L;

        GridPanel(GridLayout layout) {
            super(layout);
        }
    }

}
