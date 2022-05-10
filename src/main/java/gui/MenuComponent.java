package gui;

import algos.NeedlemanWunsch;
import enums.SequenceTypes;
import gui.utils.SpringUtilities;
import models.Cell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuComponent extends JPanel {
    private JLabel seqChoice;
    private static JRadioButton proteinBtn;
    private static JRadioButton genomicSeqBtn;
    private JLabel firstSeqLbl;
    private static JTextField firstSeq;
    private JLabel secondSeqLbl;
    private static JTextField secondSeq;
    private JTextField match;
    private JTextField mismatch;
    private JTextField gap;
    private JButton submitBtn;
    private Font fBtn = new Font(Font.SANS_SERIF, Font.BOLD, 25);
    private Font f1 = new Font(Font.SANS_SERIF, Font.BOLD, 18);
    private Font f2 = new Font(Font.SANS_SERIF, Font.BOLD, 16);

    private static String firstSequence;
    private static String secondSequence;

    private static SequenceTypes sequenceType;

    private static Cell[][] matrix;

    private static MatrixTable matrixTable;

    private static int matchScore = 1;
    private static int mismatchScore = -1;
    private static int gapScore = -2;

    private static String[] alignment;

    private static int score;


    MenuComponent() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(800, 400));
        initComponents();
    }

    private void initComponents() {
        initSeqChoice();
        initSequenceInput();
        submitBtn = new JButton("Submit");
        submitBtn.setBackground(new Color(5, 120, 2));
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setFont(fBtn);
        add(submitBtn);

        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                firstSequence = firstSeq.getText();
                secondSequence = secondSeq.getText();
                sequenceType = proteinBtn.isSelected()? SequenceTypes.PROTEIN: SequenceTypes.GENOMIC_SEQUENCE;
                if(sequenceType==SequenceTypes.GENOMIC_SEQUENCE) {
                    matchScore = Integer.parseInt(match.getText());
                    mismatchScore = Integer.parseInt(mismatch.getText());
                }
                gapScore = Integer.parseInt(gap.getText());

                NeedlemanWunsch aligner = new NeedlemanWunsch(firstSequence, secondSequence,
                        matchScore, mismatchScore, gapScore, sequenceType);
                matrix = aligner.getCellTable();
                alignment = aligner.getAlignment();
                score = (sequenceType==SequenceTypes.GENOMIC_SEQUENCE) ?
                        aligner.getAlignmentScoreSequence():aligner.getAlignmentScoreProtein();
                createMatrixFrame();
            }
        });
    }

    private void initSeqChoice() {
        JPanel seqChoicePanel = new JPanel(new FlowLayout());

        seqChoicePanel.setBackground(Color.WHITE);
        seqChoice = new JLabel("Choose alignment type:");
        seqChoice.setFont(f1);
        ButtonGroup choiceBtnGroup = new ButtonGroup();
        proteinBtn = new JRadioButton("Protein");
        proteinBtn.setBackground(Color.WHITE);
        proteinBtn.setFont(f2);
        proteinBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                match.setVisible(false);
                mismatch.setVisible(false);
            }
        });
        genomicSeqBtn = new JRadioButton("Genomic sequence");
        genomicSeqBtn.setBackground(Color.WHITE);
        genomicSeqBtn.setFont(f2);
        genomicSeqBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                match.setVisible(true);
                mismatch.setVisible(true);
            }
        });
        choiceBtnGroup.add(proteinBtn);
        choiceBtnGroup.add(genomicSeqBtn);
        choiceBtnGroup.setSelected(genomicSeqBtn.getModel(), true);
        seqChoice.setLabelFor(proteinBtn);
        seqChoice.setLabelFor(genomicSeqBtn);
        seqChoicePanel.add(seqChoice);
        seqChoicePanel.add(proteinBtn);
        seqChoicePanel.add(genomicSeqBtn);
        add(seqChoicePanel);


        JPanel scorePanel = new JPanel(new FlowLayout());
        match = new JTextField("1");
        match.setPreferredSize(new Dimension(50, 50));
        mismatch = new JTextField("-1");
        mismatch.setPreferredSize(new Dimension(50, 50));
        gap = new JTextField("-2");
        gap.setPreferredSize(new Dimension(50, 50));
        JLabel matchLbl = new JLabel("Match:", JLabel.CENTER);
        matchLbl.setLabelFor(match);
        JLabel mismatchLbl = new JLabel("Mismatch:", JLabel.CENTER);
        mismatchLbl.setLabelFor(mismatch);
        JLabel gapLbl = new JLabel("Gap:", JLabel.CENTER);
        gapLbl.setLabelFor(gap);

        scorePanel.add(matchLbl);
        scorePanel.add(match);
        scorePanel.add(mismatchLbl);
        scorePanel.add(mismatch);
        scorePanel.add(gapLbl);
        scorePanel.add(gap);
        scorePanel.setBackground(Color.WHITE);
        add(scorePanel);

    }

    private void initSequenceInput() {
        JPanel sequencePanel = new JPanel();
        sequencePanel.setLayout(new SpringLayout());
        sequencePanel.setBackground(Color.WHITE);
        firstSeqLbl = new JLabel("First sequence:", JLabel.TRAILING);
        firstSeqLbl.setFont(f1);
        firstSeqLbl.setAlignmentX(15);
        secondSeqLbl = new JLabel("Second sequence:", JLabel.TRAILING);
        secondSeqLbl.setFont(f1);
        firstSeq = new JTextField();
        firstSeq.setFont(f1);
        secondSeq = new JTextField();
        secondSeq.setFont(f1);
        sequencePanel.add(firstSeqLbl);
        sequencePanel.add(firstSeq);

        sequencePanel.add(secondSeqLbl);
        sequencePanel.add(secondSeq);
        SpringUtilities.makeCompactGrid(sequencePanel, 2, 2, 2, 2, 22, 40);
        add(sequencePanel);
    }

    public static void createMatrixFrame() {
        SwingUtilities.invokeLater(() -> {
            matrixTable = new MatrixTable(firstSequence, secondSequence, matrix, alignment, score);

        });
    }

}
