package gui;

import algos.NeedlemanWunsch;
import enums.SequenceTypes;
import gui.utils.SpringUtilities;
import inputvalidators.InputValidator;
import models.Cell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class MenuComponent extends JPanel {
    private JTextField firstSeq;
    private JTextField secondSeq;
    private JTextField match;
    private JTextField mismatch;
    private JTextField gap;
    private JRadioButton proteinBtn;
    private JRadioButton genomicSeqBtn;

    private static String firstSequence;
    private static String secondSequence;

    private static SequenceTypes sequenceType;

    private static Cell[][] matrix;

    private static MatrixTable matrixTable;

    private static int matchScore;
    private static int mismatchScore;
    private static int gapScore;
    private static int alignmentScore ;

    private static String[] alignment;

    private static final Font fBtn = new Font(Font.SANS_SERIF, Font.BOLD, 25);
    private static final Font f1 = new Font(Font.SANS_SERIF, Font.BOLD, 18);
    private static final Font f2 = new Font(Font.SANS_SERIF, Font.BOLD, 16);


    MenuComponent() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(800, 400));
        initComponents();
    }

    private void initComponents() {
        initSeqChoice();
        initSequenceInput();
        JButton submitBtn = new JButton("Submit");
        submitBtn.setBackground(new Color(5, 120, 2));
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setFont(fBtn);
        add(submitBtn);

        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int errors = 0;
                firstSequence = firstSeq.getText();
                if(firstSequence!= null)
                    firstSequence = firstSequence.replaceAll("\\s+", ""); // removes all the spacers
                secondSequence = secondSeq.getText();
                if(secondSequence!=null)
                    secondSequence = secondSequence.replaceAll("\\s+", "");
                sequenceType = proteinBtn.isSelected()? SequenceTypes.PROTEIN: SequenceTypes.GENOMIC_SEQUENCE;
                if(sequenceType == SequenceTypes.GENOMIC_SEQUENCE){
                    if(!InputValidator.validateGenomicSeq(firstSequence)){
                        errors += 1;
                        firstSeq.setBackground(Color.pink);
                    }
                    if(!InputValidator.validateGenomicSeq(firstSequence)){
                        errors += 1;
                        secondSeq.setBackground(Color.pink);
                    }
                }
                NeedlemanWunsch aligner = null;
                gapScore = InputValidator.validateMMGScores(gap.getText());
                if(gapScore == -1) {
                    gap.setBackground(Color.pink);
                    errors+=1;
                }
                if(sequenceType==SequenceTypes.GENOMIC_SEQUENCE) {
                    matchScore = InputValidator.validateMMGScores(match.getText());
                    mismatchScore = InputValidator.validateMMGScores(mismatch.getText());
                    if(matchScore != -1 && mismatchScore!=-1) {
                        aligner = new NeedlemanWunsch(firstSequence, secondSequence,
                                matchScore, mismatchScore, gapScore, sequenceType);
                    } else errors+=1;
                }
                else {
                    aligner = new NeedlemanWunsch(firstSequence, secondSequence, gapScore, sequenceType);
                }
                if(aligner!=null) {
                    matrix = aligner.getCellTable();
                    alignment = aligner.getAlignment();
                    alignmentScore = (sequenceType == SequenceTypes.GENOMIC_SEQUENCE) ?
                            aligner.getAlignmentScoreSequence() : aligner.getAlignmentScoreProtein();
                    createMatrixFrame();
                }
            }
        });
    }

    private void initSeqChoice() {
        JPanel seqChoicePanel = new JPanel(new FlowLayout());

        seqChoicePanel.setBackground(Color.WHITE);
        JLabel seqChoice = new JLabel("Choose alignment type:");
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
        sequencePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sequencePanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        JLabel firstSeqLbl = new JLabel("First sequence:",  JLabel.LEFT);
        firstSeqLbl.setFont(f1);
        firstSeqLbl.setAlignmentX(15);
        JLabel secondSeqLbl = new JLabel("Second sequence:", JLabel.LEFT);
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
            matrixTable = new MatrixTable(firstSequence, secondSequence, sequenceType, matrix,
                    alignment, alignmentScore, gapScore);
            matrixTable.createGUI();

        });
    }

}
