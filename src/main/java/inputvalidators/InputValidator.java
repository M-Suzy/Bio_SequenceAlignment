package inputvalidators;

import java.util.logging.Logger;

public class InputValidator {
    private static Logger logger = Logger.getLogger(InputValidator.class.getName());

    private static final int MAX_SEQ_LENGTH = 40;

    public static Integer validateMMGScores(String score) {
        assert score!=null;
        Integer num;
        try {
            num = Integer.parseInt(score);
            return num;
        } catch (NumberFormatException nfe) {
            logger.info("Input should be numeric!");
        }
        return null;
    }

    public static boolean validateGenomicSeq(String sequence) {
        assert sequence!=null;
        if (sequence.length() > MAX_SEQ_LENGTH) {
            logger.info("The sequence is too long!");
            return false;
        }
        if (!sequence.matches("[acgtACGT]+")) {
            logger.info("The sequence is not a nucleotide sequence!");
            return false;
        }
        return true;

    }

    public static boolean validateProteinSeq(String sequence) {
        assert sequence!=null;
        if (sequence.length() > 40) {
            logger.info("The sequence is too long!");
            return false;
        }
        if (!sequence.matches("[ARNDCQEGHILKMFPSTWYVBJZXarndcqeghilkmfpstwyvbjzx]+")) {
            logger.info("The sequence is not a Protein sequence!");
            return false;
        }
        return true;

    }

}
