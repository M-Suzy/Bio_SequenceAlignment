package gui;

import models.Matrix;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuComponent extends JPanel {
    private JLabel seqChoice;
    private JRadioButton proteinBtn;
    private JRadioButton genomicSeqBtn;
    private JLabel firstSeqLbl;
    private JTextField firstSeq;
    private JLabel secondSeqLbl;
    private JTextField secondSeq;
    private JButton submitBtn;
    private Font fBtn = new Font(Font.SANS_SERIF,  Font.BOLD, 22);
    private Font f1 = new Font(Font.SANS_SERIF,  Font.BOLD, 16);
    private Font f2 = new Font(Font.SANS_SERIF,  Font.BOLD, 14);

    MenuComponent(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(700, 300));
        initComponents();
    }

    private void initComponents(){
        initSeqChoice();
        initSequenceInput();
        submitBtn= new JButton("Submit");
        submitBtn.setBackground(new Color(5, 120, 2));
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setFont(fBtn);
        add(submitBtn);
        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createMatrixFrame();
            }
        });
    }

    private void initSeqChoice(){
        JPanel seqChoicePanel = new JPanel(new GridLayout(0, 3, 1, 3));
        seqChoicePanel.setBackground(Color.WHITE);
        seqChoicePanel.setPreferredSize(new Dimension(500, 15));
        seqChoice = new JLabel("Choose alignment type:");
        seqChoice.setFont(f1);
        ButtonGroup choiceBtnGroup = new ButtonGroup();
        proteinBtn = new JRadioButton("Protein");
        proteinBtn.setBackground(Color.WHITE);
        proteinBtn.setFont(f2);
        genomicSeqBtn = new JRadioButton("Genomic sequence");
        genomicSeqBtn.setBackground(Color.WHITE);
        genomicSeqBtn.setFont(f2);
        choiceBtnGroup.add(proteinBtn);
        choiceBtnGroup.add(genomicSeqBtn);
        seqChoice.setLabelFor(proteinBtn);
        seqChoice.setLabelFor(genomicSeqBtn);
        seqChoicePanel.add(seqChoice);
        seqChoicePanel.add(proteinBtn);
        seqChoicePanel.add(genomicSeqBtn);
        add(seqChoicePanel);
    }

    private void initSequenceInput(){
        JPanel sequencePanel = new JPanel(new GridLayout(3, 1, 0, 3 ));
        sequencePanel.setPreferredSize(new Dimension(500, 50));
        sequencePanel.setBackground(Color.WHITE);
        firstSeqLbl = new JLabel("First sequence:");
        firstSeqLbl.setFont(f1);
        secondSeqLbl = new JLabel("Second sequence:");
        secondSeqLbl.setFont(f1);
        firstSeq = new JTextField();
        secondSeq = new JTextField();
        sequencePanel.add(firstSeqLbl);
        sequencePanel.add(firstSeq);

        sequencePanel.add(secondSeqLbl);
        sequencePanel.add(secondSeq);
        add(sequencePanel);
    }

    public static void createMatrixFrame()
    {
        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                JFrame frame = new JFrame("DP matrix");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                try
                {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }

               // Matrix matrix =new Matrix("aabab", "jsh");

               // MatrixTable matrixTable= new MatrixTable(matrix);


               // frame.getContentPane().add(BorderLayout.CENTER, matrixTable);
                frame.pack();
                frame.setLocationByPlatform(true);
                frame.setVisible(true);
                frame.setResizable(false);
            }
        });
    }

}
